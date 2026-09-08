package com.wendson.sistemachamados.dto;

import com.wendson.sistemachamados.entity.*;
import jakarta.validation.constraints.*;


public class CategoriaRequestDTO {
    @NotBlank @Size(max = 100)
    private String nome;

    @NotBlank @Size(max = 10000)
    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
