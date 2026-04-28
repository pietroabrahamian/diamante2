package br.com.fiap.gerenciadorevento.controllers;

import br.com.fiap.gerenciadorevento.dtos.EventoResumoDTO;
import br.com.fiap.gerenciadorevento.models.Evento;
import br.com.fiap.gerenciadorevento.services.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService service;

    @GetMapping
    public Page<Evento> listarEventos(
            @PageableDefault(size = 10, sort = "dtaEvento", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.getEventos(pageable);
    }

    @GetMapping("/{id}")
    public Evento buscarPorId(@PathVariable Long id) {
        return service.getEventoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento criarEvento(@Valid @RequestBody Evento evento) {
        return service.addEvento(evento);
    }

    @PutMapping("/{id}")
    public Evento atualizarEvento(@PathVariable Long id, @Valid @RequestBody Evento evento) {
        return service.updateEvento(id, evento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarEvento(@PathVariable Long id) {
        service.deleteEvento(id);
    }

    @GetMapping("/busca/nome")
    public Page<Evento> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorNome(nome, pageable);
    }

    @GetMapping("/busca/tipo")
    public Page<Evento> buscarPorTipo(
            @RequestParam String tipo,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorTipo(tipo, pageable);
    }

    @GetMapping("/busca/local")
    public Page<Evento> buscarPorLocal(
            @RequestParam String local,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorLocal(local, pageable);
    }

    @GetMapping("/busca/periodo")
    public Page<Evento> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorPeriodo(inicio, fim, pageable);
    }

    @GetMapping("/busca/capacidade")
    public Page<Evento> buscarPorCapacidade(
            @RequestParam int minimo,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorCapacidadeMinima(minimo, pageable);
    }

    @GetMapping("/resumo")
    public Page<EventoResumoDTO> listarResumo(
            @PageableDefault(size = 10, sort = "dtaEvento") Pageable pageable) {
        return service.listarResumo(pageable);
    }
}
