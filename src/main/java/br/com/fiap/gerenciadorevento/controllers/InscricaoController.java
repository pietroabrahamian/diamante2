package br.com.fiap.gerenciadorevento.controllers;

import br.com.fiap.gerenciadorevento.dtos.InscricaoRequestDTO;
import br.com.fiap.gerenciadorevento.dtos.InscricaoResponseDTO;
import br.com.fiap.gerenciadorevento.services.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService service;

    @GetMapping
    public Page<InscricaoResponseDTO> listarTodas(
            @PageableDefault(size = 10) Pageable pageable) {
        return service.listarTodas(pageable);
    }

    @GetMapping("/{id}")
    public InscricaoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InscricaoResponseDTO criarInscricao(@Valid @RequestBody InscricaoRequestDTO dto) {
        return service.criarInscricao(dto);
    }

    @PutMapping("/{id}")
    public InscricaoResponseDTO atualizarInscricao(
            @PathVariable Long id,
            @Valid @RequestBody InscricaoRequestDTO dto) {
        return service.atualizarInscricao(id, dto);
    }

    @PatchMapping("/{id}/status")
    public InscricaoResponseDTO atualizarStatus(
            @PathVariable Long id,
            @RequestParam String novoStatus) {
        return service.atualizarStatus(id, novoStatus);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarInscricao(@PathVariable Long id) {
        service.cancelarInscricao(id);
    }

    @GetMapping("/busca/evento/{eventoId}")
    public Page<InscricaoResponseDTO> buscarPorEvento(
            @PathVariable Long eventoId,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorEvento(eventoId, pageable);
    }

    @GetMapping("/busca/participante/{participanteId}")
    public Page<InscricaoResponseDTO> buscarPorParticipante(
            @PathVariable Long participanteId,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorParticipante(participanteId, pageable);
    }

    @GetMapping("/busca/status")
    public Page<InscricaoResponseDTO> buscarPorStatus(
            @RequestParam String status,
            @PageableDefault(size = 10) Pageable pageable) {
        return service.buscarPorStatus(status, pageable);
    }
}
