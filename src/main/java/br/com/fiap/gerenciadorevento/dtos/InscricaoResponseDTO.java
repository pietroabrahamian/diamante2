package br.com.fiap.gerenciadorevento.dtos;

import java.time.LocalDateTime;

public record InscricaoResponseDTO(
        Long id,
        EventoResumoDTO evento,
        ParticipanteResumoDTO participante,
        LocalDateTime dtaInscricao,
        String status,
        String observacao
) {}
