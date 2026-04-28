package br.com.fiap.gerenciadorevento.controllers;

import br.com.fiap.gerenciadorevento.dtos.ParticipanteResumoDTO;
import br.com.fiap.gerenciadorevento.models.Participante;
import br.com.fiap.gerenciadorevento.services.ParticipanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService service;

    @GetMapping
    public Page<Participante> listarParticipantes(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return service.getParticipantes(pageable);
    }

    @GetMapping("/{id}")
    public Participante buscarPorId(@PathVariable Long id) {
        return service.getParticipanteById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Participante criarParticipante(@Valid @RequestBody Participante participante) {
        return service.addParticipante(participante);
    }

    @PutMapping("/{id}")
    public Participante atualizarParticipante(@PathVariable Long id, @Valid @RequestBody Participante participante) {
        return service.updateParticipante(id, participante);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarParticipante(@PathVariable Long id) {
        service.deleteParticipante(id);
    }

    @GetMapping("/busca/email")
    public Participante buscarPorEmail(@RequestParam String email) {
        return service.buscarPorEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado"));
    }

    @GetMapping("/busca/nome")
    public Page<Participante> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorNome(nome, pageable);
    }

    @GetMapping("/busca/idade")
    public Page<Participante> buscarPorFaixaEtaria(
            @RequestParam int min,
            @RequestParam int max,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorFaixaEtaria(min, max, pageable);
    }

    @GetMapping("/resumo")
    public Page<ParticipanteResumoDTO> listarResumo(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return service.listarResumo(pageable);
    }
}
