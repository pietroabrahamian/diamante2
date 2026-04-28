package br.com.fiap.gerenciadorevento.services;

import br.com.fiap.gerenciadorevento.dtos.EventoResumoDTO;
import br.com.fiap.gerenciadorevento.models.Evento;
import br.com.fiap.gerenciadorevento.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    public Page<Evento> getEventos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Evento> getEventoById(Long id) {
        return repository.findById(id);
    }

    public Evento addEvento(Evento evento) {
        return repository.save(evento);
    }

    public Evento updateEvento(Long id, Evento newEvento) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado");
        }
        newEvento.setId(id);
        return repository.save(newEvento);
    }

    public void deleteEvento(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado");
        }
        repository.deleteById(id);
    }

    public Page<Evento> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Evento> buscarPorTipo(String tipo, Pageable pageable) {
        return repository.findByTipoIgnoreCase(tipo, pageable);
    }

    public Page<Evento> buscarPorLocal(String local, Pageable pageable) {
        return repository.findByLocalContainingIgnoreCase(local, pageable);
    }

    public Page<Evento> buscarPorPeriodo(LocalDate inicio, LocalDate fim, Pageable pageable) {
        return repository.findByDtaEventoBetween(inicio, fim, pageable);
    }

    public Page<Evento> buscarPorCapacidadeMinima(int capacidade, Pageable pageable) {
        return repository.findByCapacidadeMaxGreaterThanEqual(capacidade, pageable);
    }

    public Page<EventoResumoDTO> listarResumo(Pageable pageable) {
        return repository.findAll(pageable)
                .map(e -> new EventoResumoDTO(e.getId(), e.getNome(), e.getDtaEvento(), e.getLocal(), e.getTipo()));
    }
}
