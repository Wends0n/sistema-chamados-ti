package com.wendson.sistemachamados.dto;

import com.wendson.sistemachamados.entity.Estado;
import com.wendson.sistemachamados.entity.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChamadoRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;
    @NotBlank(message = "O solicitante é obrigatório")
    private String solicitante;
    @NotNull(message = "O estado é obrigatório")
    private Estado estado;
    @NotNull(message = "A prioridade é obrigatória")
    private Prioridade prioridade;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getSolicitante() { return solicitante; }
    public void setSolicitante(String solicitante) { this.solicitante = solicitante; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

}