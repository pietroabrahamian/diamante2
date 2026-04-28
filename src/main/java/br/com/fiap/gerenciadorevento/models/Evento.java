package br.com.fiap.gerenciadorevento.models;

import br.com.fiap.gerenciadorevento.validation.DataFutura;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do evento é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "A data do evento é obrigatória")
    @DataFutura
    @Column(nullable = false)
    private LocalDate dtaEvento;

    @NotBlank(message = "O local do evento é obrigatório")
    @Size(max = 200, message = "O local deve ter no máximo 200 caracteres")
    @Column(nullable = false, length = 200)
    private String local;

    @Min(value = 1, message = "A capacidade mínima é 1")
    @Max(value = 100000, message = "A capacidade máxima é 100.000")
    @Column(nullable = false)
    private int capacidadeMax;

    @NotBlank(message = "O tipo do evento é obrigatório")
    @Pattern(regexp = "Workshop|Palestra|Seminário|Congresso|Show|Outro",
             message = "Tipo deve ser: Workshop, Palestra, Seminário, Congresso, Show ou Outro")
    @Column(nullable = false, length = 50)
    private String tipo;
}
