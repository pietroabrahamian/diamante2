package br.com.fiap.gerenciadorevento.services;

import br.com.fiap.gerenciadorevento.dtos.ParticipanteResumoDTO;
import br.com.fiap.gerenciadorevento.models.Participante;
import br.com.fiap.gerenciadorevento.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository repository;

    public Page<Participante> getParticipantes(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Participante> getParticipanteById(Long id) {
        return repository.findById(id);
    }

    public Participante addParticipante(Participante participante) {
        if (repository.existsByEmailIgnoreCase(participante.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
        return repository.save(participante);
    }

    public Participante updateParticipante(Long id, Participante newParticipante) {
        Participante existente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado"));

        if (!existente.getEmail().equalsIgnoreCase(newParticipante.getEmail())
                && repository.existsByEmailIgnoreCase(newParticipante.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já está em uso por outro participante");
        }

        newParticipante.setId(id);
        return repository.save(newParticipante);
    }

    public void deleteParticipante(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado");
        }
        repository.deleteById(id);
    }

    public Optional<Participante> buscarPorEmail(String email) {
        return repository.findByEmailIgnoreCase(email);
    }

    public Page<Participante> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Participante> buscarPorFaixaEtaria(int min, int max, Pageable pageable) {
        return repository.findByIdadeBetween(min, max, pageable);
    }

    public Page<ParticipanteResumoDTO> listarResumo(Pageable pageable) {
        return repository.findAll(pageable)
                .map(p -> new ParticipanteResumoDTO(p.getId(), p.getNome(), p.getEmail()));
    }
}
