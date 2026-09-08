package com.wendson.sistemachamados.repository;

import com.wendson.sistemachamados.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(name = "Usuario.listar", nativeQuery = true)
    List<Usuario> listar();

    @Query(name = "Usuario.buscarPorId", nativeQuery = true)
    Optional<Usuario> buscarPorId(@Param("id") Long id);

    @Query(name = "Usuario.emailEmUso", nativeQuery = true)
    boolean emailEmUso(@Param("email") String email, @Param("id") Long id);
}
