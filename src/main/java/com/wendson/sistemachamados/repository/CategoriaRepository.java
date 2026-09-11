package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(
            value = "SELECT * FROM categoria ORDER BY id",
            countQuery = "SELECT COUNT(*) FROM categoria",
            nativeQuery = true
    )
    Page<Categoria> listar(Pageable pageable);

    @Query(
            value = "SELECT * FROM categoria WHERE id = :id",
            nativeQuery = true
    )
    Optional<Categoria> buscarPorId(@Param("id") Long id);
}
