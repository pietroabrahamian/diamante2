package br.com.fiap.gerenciadorevento.dtos;

import java.time.LocalDate;

public record EventoResumoDTO(Long id, String nome, LocalDate dtaEvento, String local, String tipo) {}
