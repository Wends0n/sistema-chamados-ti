package com.wendson.sistemachamados.dto;

import com.wendson.sistemachamados.entity.*;
import jakarta.validation.constraints.*;

public class ChamadoRequestDTO {
    @NotBlank @Size(max = 200)
    private String titulo;

    @NotBlank @Size(max = 10000)
    private String descricao;

    @NotNull
    private Estado estado;

    @NotNull
    private Prioridade prioridade;

    @NotNull @Positive
    private Long solicitanteId;

    @Positive
    private Long tecnicoId;

    @NotNull @Positive
    private Long categoriaId;

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

    public Long getSolicitanteId() {
        return solicitanteId;
    }

    public void setSolicitanteId(Long solicitanteId) {
        this.solicitanteId = solicitanteId;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
