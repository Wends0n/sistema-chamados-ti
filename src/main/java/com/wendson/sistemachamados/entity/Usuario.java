package com.wendson.sistemachamados.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")

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

    @OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY)
    private List<Chamado> chamadosSolicitados = new ArrayList<>();

    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    private List<Chamado> chamadosAtribuidos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Comentario> comentarioUsuario = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<HistoricoChamado> historicoChamadoUsuario = new ArrayList<>();

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



    public List<Chamado> getChamadosSolicitados(){
        return chamadosSolicitados;
    }

    public List<Chamado> getChamadosAtribuidos(){
        return chamadosAtribuidos;
    }

    public List<Comentario> getComentarioUsuario(){
        return comentarioUsuario;
    }

    public List<HistoricoChamado> getHistoricoChamadoUsuario(){
        return historicoChamadoUsuario;
    }

}
