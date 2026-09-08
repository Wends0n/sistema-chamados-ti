package com.wendson.sistemachamados.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_chamado")
public class HistoricoChamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false, length = 30)
    private TipoEvento tipoEvento;

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

    public HistoricoChamado(){

    }

    public HistoricoChamado(Long id, String descricao, TipoEvento tipoEvento, LocalDateTime dataHora, Usuario usuario, Chamado chamado) {
        this.id = id;
        this.descricao = descricao;
        this.tipoEvento = tipoEvento;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
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
