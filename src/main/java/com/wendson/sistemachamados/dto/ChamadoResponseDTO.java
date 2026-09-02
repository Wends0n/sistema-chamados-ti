package com.wendson.sistemachamados.dto;

import com.wendson.sistemachamados.entity.Estado;
import com.wendson.sistemachamados.entity.Prioridade;
import java.time.LocalDateTime;

public class ChamadoResponseDTO {

    private long id;
    private String titulo;
    private String descricao;
    private String solicitante;
    private Estado estado;
    private Prioridade prioridade;
    private LocalDateTime dataAbertura;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getSolicitante() { return solicitante; }
    public void setSolicitante(String solicitante) { this.solicitante = solicitante; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade;}
}