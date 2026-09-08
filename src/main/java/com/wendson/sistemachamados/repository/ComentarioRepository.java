package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    @Query(value = "SELECT * FROM comentario ORDER BY id", nativeQuery = true)
    List<Comentario> listar();

    @Query(value = "SELECT * FROM comentario WHERE id = :id", nativeQuery = true)
    Optional<Comentario> buscarPorId(@Param("id") Long id);

    @Query(value = "SELECT * FROM comentario WHERE chamado_id = :chamadoId ORDER BY data_hora, id", nativeQuery = true)
    List<Comentario> buscarPorChamado(@Param("chamadoId") Long chamadoId);
}
