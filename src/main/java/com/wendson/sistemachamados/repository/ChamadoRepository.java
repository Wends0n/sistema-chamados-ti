package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    @Query(value = "SELECT * FROM chamados ORDER BY id", nativeQuery = true)
    List<Chamado> listar();

    @Query(value = "SELECT * FROM chamados WHERE id = :id", nativeQuery = true)
    Optional<Chamado> buscarPorId(@Param("id") Long id);

    @Query(value = "SELECT * FROM chamados WHERE strpos(lower(titulo), lower(:titulo)) > 0 ORDER BY id", nativeQuery = true)
    List<Chamado> buscarPorTitulo(@Param("titulo") String titulo);
}
