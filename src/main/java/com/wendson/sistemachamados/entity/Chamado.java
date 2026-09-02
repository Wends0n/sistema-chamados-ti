package com.wendson.sistemachamados.entity;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "solicitante")
    private String solicitante;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "prioridade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Column(name = "data_abertura",nullable = false,updatable = false)
    private LocalDateTime dataAbertura;

    @PrePersist
    public void prePersist(){
        this.dataAbertura = LocalDateTime.now();
    }

    public Chamado(){

    }

    public Chamado(String titulo, String descricao, String solicitante,Prioridade prioridade, Estado estado){
        this.titulo = titulo;
        this.descricao = descricao;
        this.solicitante = solicitante;
        this.prioridade = prioridade;
        this.estado = estado;
    }

    public long getId(){
        return id;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getSolicitante(){
        return solicitante;
    }

    public void setSolicitante(String solicitante){
        this.solicitante = solicitante;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public Prioridade getPrioridade(){
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade){
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataAbertura(){
        return dataAbertura;
    }

    @Override
    public String toString(){
        return "Chamado[id = " + id + ", titulo = " + titulo + ", descricao = " + descricao + "]";
    }


}
