package br.com.fiap.gerenciadorevento.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inscricoes",
       uniqueConstraints = @UniqueConstraint(columnNames = {"evento_id", "participante_id"}))
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evento_id", nullable = false)
    @NotNull(message = "O evento é obrigatório")
    @JsonIgnoreProperties("inscricoes")
    @ToString.Exclude
    private Evento evento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participante_id", nullable = false)
    @NotNull(message = "O participante é obrigatório")
    @JsonIgnoreProperties("inscricoes")
    @ToString.Exclude
    private Participante participante;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dtaInscricao = LocalDateTime.now();

    @NotBlank(message = "O status é obrigatório")
    @Pattern(regexp = "CONFIRMADA|PENDENTE|CANCELADA",
             message = "Status deve ser: CONFIRMADA, PENDENTE ou CANCELADA")
    private String status;

    @Size(max = 300, message = "A observação deve ter no máximo 300 caracteres")
    private String observacao;
}
