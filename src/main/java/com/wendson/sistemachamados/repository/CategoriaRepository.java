package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(value = "SELECT * FROM categoria ORDER BY id", nativeQuery = true)
    List<Categoria> listar();

    @Query(value = "SELECT * FROM categoria WHERE id = :id", nativeQuery = true)
    Optional<Categoria> buscarPorId(@Param("id") Long id);
}
