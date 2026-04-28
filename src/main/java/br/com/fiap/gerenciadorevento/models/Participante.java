package br.com.fiap.gerenciadorevento.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "participantes")
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do participante é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(unique = true, nullable = false)
    private String email;

    @Min(value = 1, message = "A idade mínima é 1")
    @Max(value = 120, message = "A idade máxima é 120")
    private int idade;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\(?\\d{2}\\)?[\\s-]?9?\\d{4}[-\\s]?\\d{4}",
             message = "Telefone inválido. Use o formato: (11) 91234-5678 ou 11912345678")
    @Column(nullable = false, length = 20)
    private String telefone;
}
