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


    public Chamado(){

    }

    public Chamado(String titulo, String descricao){
        this.titulo = titulo;
        this.descricao = descricao;

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



    @Override
    public String toString(){
        return "Chamado[id = " + id + ", titulo = " + titulo + ", descricao = " + descricao + "]";
    }


}
