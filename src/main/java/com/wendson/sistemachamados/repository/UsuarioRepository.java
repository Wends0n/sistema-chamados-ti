package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = "SELECT * FROM usuario ORDER BY id", nativeQuery = true)
    List<Usuario> listar();

    @Query(value = "SELECT * FROM usuario WHERE id = :id", nativeQuery = true)
    Optional<Usuario> buscarPorId(@Param("id") Long id);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM usuario WHERE lower(email) = lower(:email) AND id <> :id)", nativeQuery = true)
    boolean emailEmUso(@Param("email") String email, @Param("id") Long id);
}
