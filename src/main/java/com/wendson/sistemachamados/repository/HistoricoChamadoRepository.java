package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.HistoricoChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface HistoricoChamadoRepository extends JpaRepository<HistoricoChamado, Long> {
    @Query(name = "HistoricoChamado.listar", nativeQuery = true)
    List<HistoricoChamado> listar();

    @Query(name = "HistoricoChamado.buscarPorId", nativeQuery = true)
    Optional<HistoricoChamado> buscarPorId(@Param("id") Long id);

    @Query(name = "HistoricoChamado.buscarPorChamado", nativeQuery = true)
    List<HistoricoChamado> buscarPorChamado(@Param("chamadoId") Long chamadoId);
}
