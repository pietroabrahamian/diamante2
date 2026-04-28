package br.com.fiap.gerenciadorevento.services;

import br.com.fiap.gerenciadorevento.dtos.EventoResumoDTO;
import br.com.fiap.gerenciadorevento.dtos.InscricaoRequestDTO;
import br.com.fiap.gerenciadorevento.dtos.InscricaoResponseDTO;
import br.com.fiap.gerenciadorevento.dtos.ParticipanteResumoDTO;
import br.com.fiap.gerenciadorevento.models.Evento;
import br.com.fiap.gerenciadorevento.models.Inscricao;
import br.com.fiap.gerenciadorevento.models.Participante;
import br.com.fiap.gerenciadorevento.repositories.EventoRepository;
import br.com.fiap.gerenciadorevento.repositories.InscricaoRepository;
import br.com.fiap.gerenciadorevento.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    public Page<InscricaoResponseDTO> listarTodas(Pageable pageable) {
        return inscricaoRepository.findAll(pageable).map(this::toDTO);
    }

    public InscricaoResponseDTO buscarPorId(Long id) {
        return inscricaoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada"));
    }

    public InscricaoResponseDTO criarInscricao(InscricaoRequestDTO dto) {
        Evento evento = eventoRepository.findById(dto.eventoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));

        Participante participante = participanteRepository.findById(dto.participanteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado"));

        if (inscricaoRepository.existsByEventoIdAndParticipanteId(dto.eventoId(), dto.participanteId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Participante já inscrito neste evento");
        }

        long inscritos = inscricaoRepository.countByEventoIdAndStatus(dto.eventoId(), "CONFIRMADA");
        if (inscritos >= evento.getCapacidadeMax()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Evento atingiu a capacidade máxima de " + evento.getCapacidadeMax() + " participantes");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);
        inscricao.setStatus(dto.status());
        inscricao.setObservacao(dto.observacao());
        inscricao.setDtaInscricao(LocalDateTime.now());

        return toDTO(inscricaoRepository.save(inscricao));
    }

    public InscricaoResponseDTO atualizarInscricao(Long id, InscricaoRequestDTO dto) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada"));

        Evento evento = eventoRepository.findById(dto.eventoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
        Participante participante = participanteRepository.findById(dto.participanteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado"));

        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);
        inscricao.setStatus(dto.status());
        inscricao.setObservacao(dto.observacao());
        return toDTO(inscricaoRepository.save(inscricao));
    }

    public InscricaoResponseDTO atualizarStatus(Long id, String novoStatus) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada"));

        if (!novoStatus.matches("CONFIRMADA|PENDENTE|CANCELADA")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Status inválido. Use: CONFIRMADA, PENDENTE ou CANCELADA");
        }

        inscricao.setStatus(novoStatus);
        return toDTO(inscricaoRepository.save(inscricao));
    }

    public void cancelarInscricao(Long id) {
        if (!inscricaoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada");
        }
        inscricaoRepository.deleteById(id);
    }

    public Page<InscricaoResponseDTO> buscarPorEvento(Long eventoId, Pageable pageable) {
        if (!eventoRepository.existsById(eventoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado");
        }
        return inscricaoRepository.findByEventoId(eventoId, pageable).map(this::toDTO);
    }

    public Page<InscricaoResponseDTO> buscarPorParticipante(Long participanteId, Pageable pageable) {
        if (!participanteRepository.existsById(participanteId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado");
        }
        return inscricaoRepository.findByParticipanteId(participanteId, pageable).map(this::toDTO);
    }

    public Page<InscricaoResponseDTO> buscarPorStatus(String status, Pageable pageable) {
        return inscricaoRepository.findByStatus(status.toUpperCase(), pageable).map(this::toDTO);
    }

    private InscricaoResponseDTO toDTO(Inscricao i) {
        EventoResumoDTO eventoDTO = new EventoResumoDTO(
                i.getEvento().getId(),
                i.getEvento().getNome(),
                i.getEvento().getDtaEvento(),
                i.getEvento().getLocal(),
                i.getEvento().getTipo()
        );
        ParticipanteResumoDTO participanteDTO = new ParticipanteResumoDTO(
                i.getParticipante().getId(),
                i.getParticipante().getNome(),
                i.getParticipante().getEmail()
        );
        return new InscricaoResponseDTO(
                i.getId(),
                eventoDTO,
                participanteDTO,
                i.getDtaInscricao(),
                i.getStatus(),
                i.getObservacao()
        );
    }
}
