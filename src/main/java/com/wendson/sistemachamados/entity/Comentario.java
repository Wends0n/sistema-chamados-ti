package com.wendson.sistemachamados.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentario")
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "Comentario.listar",
        query = "SELECT * FROM comentario ORDER BY id",
        resultClass = Comentario.class
    ),
    @NamedNativeQuery(
        name = "Comentario.buscarPorId",
        query = "SELECT * FROM comentario WHERE id = :id",
        resultClass = Comentario.class
    ),
    @NamedNativeQuery(
        name = "Comentario.buscarPorChamado",
        query = "SELECT * FROM comentario WHERE chamado_id = :chamadoId ORDER BY data_hora, id",
        resultClass = Comentario.class
    )
})
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String mensagem;

    @Column(name = "data_hora", nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;

    @PrePersist
    void registrarData() {
        if (dataHora == null) dataHora = LocalDateTime.now();
    }

    public Comentario(){

    }

    public Comentario(Long id, String mensagem, LocalDateTime dataHora, Usuario usuario, Chamado chamado) {
        this.id = id;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
        this.usuario = usuario;
        this.chamado = chamado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }
}
