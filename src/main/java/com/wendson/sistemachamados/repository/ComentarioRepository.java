package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    @Query(name = "Comentario.listar", nativeQuery = true)
    List<Comentario> listar();

    @Query(name = "Comentario.buscarPorId", nativeQuery = true)
    Optional<Comentario> buscarPorId(@Param("id") Long id);

    @Query(name = "Comentario.buscarPorChamado", nativeQuery = true)
    List<Comentario> buscarPorChamado(@Param("chamadoId") Long chamadoId);
}
