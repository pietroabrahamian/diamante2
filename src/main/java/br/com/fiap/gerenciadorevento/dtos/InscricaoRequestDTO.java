package br.com.fiap.gerenciadorevento.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record InscricaoRequestDTO(
        @NotNull(message = "O ID do evento é obrigatório") Long eventoId,
        @NotNull(message = "O ID do participante é obrigatório") Long participanteId,
        @NotBlank(message = "O status é obrigatório")
        @Pattern(regexp = "CONFIRMADA|PENDENTE|CANCELADA",
                 message = "Status deve ser: CONFIRMADA, PENDENTE ou CANCELADA")
        String status,
        @Size(max = 300) String observacao
) {}
