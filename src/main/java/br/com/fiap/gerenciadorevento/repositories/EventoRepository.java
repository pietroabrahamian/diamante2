package br.com.fiap.gerenciadorevento.repositories;

import br.com.fiap.gerenciadorevento.models.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    Page<Evento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Evento> findByTipoIgnoreCase(String tipo, Pageable pageable);

    Page<Evento> findByLocalContainingIgnoreCase(String local, Pageable pageable);

    Page<Evento> findByDtaEventoBetween(LocalDate inicio, LocalDate fim, Pageable pageable);

    Page<Evento> findByCapacidadeMaxGreaterThanEqual(int capacidade, Pageable pageable);
}
