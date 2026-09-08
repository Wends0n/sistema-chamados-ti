package com.wendson.sistemachamados.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "chamados")
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "text")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Prioridade prioridade;

    @Column(name = "data_abertura", nullable = false, updatable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "data_resolucao")
    private LocalDateTime dataResolucao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    private Usuario tecnico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @PrePersist
    void registrarData() {
        if (dataAbertura == null) dataAbertura = LocalDateTime.now();
    }

    public Chamado(){

    }

    public Chamado(Long id, String titulo, String descricao, Estado estado, Prioridade prioridade, LocalDateTime dataAbertura, LocalDateTime dataResolucao, Usuario solicitante, Usuario tecnico, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.estado = estado;
        this.prioridade = prioridade;
        this.dataAbertura = dataAbertura;
        this.dataResolucao = dataResolucao;
        this.solicitante = solicitante;
        this.tecnico = tecnico;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
