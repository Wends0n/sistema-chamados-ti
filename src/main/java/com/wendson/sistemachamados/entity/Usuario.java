package com.wendson.sistemachamados.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "Usuario.listar",
        query = "SELECT * FROM usuario ORDER BY id",
        resultClass = Usuario.class
    ),
    @NamedNativeQuery(
        name = "Usuario.buscarPorId",
        query = "SELECT * FROM usuario WHERE id = :id",
        resultClass = Usuario.class
    ),
    @NamedNativeQuery(
        name = "Usuario.emailEmUso",
        query = "SELECT EXISTS (SELECT 1 FROM usuario WHERE lower(email) = lower(:email) AND id <> :id)"
    )
})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private TipoUsuario tipoUsuario;

    public Usuario(){

    }

    public Usuario(Long id, String nome, String email, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
