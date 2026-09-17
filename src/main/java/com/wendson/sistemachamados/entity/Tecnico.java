package com.wendson.sistemachamados.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tecnico")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String especialidade;

    public Tecnico() {
    }

    public Tecnico(Long id, Usuario usuario, String especialidade) {
        setUsuario(usuario);
        this.especialidade = especialidade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) {
        if (this.usuario == usuario) return;
        Usuario anterior = this.usuario;
        this.usuario = usuario;
        if (anterior != null && anterior.getTecnico() == this) anterior.setTecnico(null);
        if (usuario != null && usuario.getTecnico() != this) usuario.setTecnico(this);
    }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
