package br.com.fiap.gerenciadorevento.repositories;

import br.com.fiap.gerenciadorevento.models.Inscricao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    Page<Inscricao> findByEventoId(Long eventoId, Pageable pageable);

    Page<Inscricao> findByParticipanteId(Long participanteId, Pageable pageable);

    Page<Inscricao> findByStatus(String status, Pageable pageable);

    boolean existsByEventoIdAndParticipanteId(Long eventoId, Long participanteId);

    long countByEventoIdAndStatus(Long eventoId, String status);
}
