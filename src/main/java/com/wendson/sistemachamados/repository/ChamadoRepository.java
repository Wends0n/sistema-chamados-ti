package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    @Query(name = "Chamado.listar", nativeQuery = true)
    List<Chamado> listar();

    @Query(name = "Chamado.buscarPorId", nativeQuery = true)
    Optional<Chamado> buscarPorId(@Param("id") Long id);

    @Query(name = "Chamado.buscarPorTitulo", nativeQuery = true)
    List<Chamado> buscarPorTitulo(@Param("titulo") String titulo);
}
