package com.wendson.sistemachamados.dto;

import com.wendson.sistemachamados.entity.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {
    @NotBlank @Size(max = 150)
    private String nome;

    @NotBlank @Email @Size(max = 254)
    private String email;

    @NotBlank @Size(min = 8, max = 128)
    private String senha;

    @Size(max = 150)
    private String especialidade;

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    @NotNull
    private TipoUsuario tipoUsuario;

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
