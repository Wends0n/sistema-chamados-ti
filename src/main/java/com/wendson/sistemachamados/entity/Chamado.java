package com.wendson.sistemachamados.entity;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table(name="chamado")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "estado")
    private String estado;

    @Column(name = "prioridade")
    private String prioridade;

    @Column(name = "dataAbertura")
    private LocalDate dataAbertura;

    public Chamado(){

    }

    public Chamado(String titulo, String descricao, String estado, String prioridade, LocalDate dataAbertura){
        this.titulo = titulo;
        this.descricao = descricao;
        this.estado = estado;
        this.prioridade = prioridade;
        this.dataAbertura = dataAbertura;
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

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public String getPrioridade(){
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getDataAbertura(){
        return dataAbertura;
    }

    @Override
    public String toString(){
        return "Chamado[id = " + id + ", titulo = " + titulo + ", descricao = " + descricao + ", estado = " + estado + ", prioridade = " + prioridade + ", data de abertura = " + dataAbertura + "]";
    }


}
