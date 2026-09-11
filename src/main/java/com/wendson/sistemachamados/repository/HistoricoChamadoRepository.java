package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.HistoricoChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface HistoricoChamadoRepository extends JpaRepository<HistoricoChamado, Long> {
    @Query(
            value = "SELECT * FROM historico_chamado ORDER BY id",
            countQuery = "SELECT COUNT(*) FROM historico_chamado",
            nativeQuery = true
    )
    Page<HistoricoChamado> listar(Pageable pageable);

    @Query(value = "SELECT * FROM historico_chamado WHERE id = :id",
            nativeQuery = true
    )
    Optional<HistoricoChamado> buscarPorId(@Param("id") Long id);

    @Query(
            value = "SELECT * FROM historico_chamado WHERE chamado_id = :chamadoId ORDER BY data_hora, id",
            countQuery = "SELECT COUNT(*) FROM historico_chamado WHERE chamado_id = :chamadoId",
            nativeQuery = true
    )
    Page<HistoricoChamado> buscarPorChamado(@Param("chamadoId") Long chamadoId, Pageable pageable);
}
