package br.com.fiap.gerenciadorevento.repositories;

import br.com.fiap.gerenciadorevento.models.Participante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    Optional<Participante> findByEmailIgnoreCase(String email);

    Page<Participante> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Participante> findByIdadeBetween(int min, int max, Pageable pageable);

    boolean existsByEmailIgnoreCase(String email);
}
