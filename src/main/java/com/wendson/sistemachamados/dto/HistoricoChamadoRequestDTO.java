package com.wendson.sistemachamados.dto;

import com.wendson.sistemachamados.entity.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class HistoricoChamadoRequestDTO {
    @NotBlank @Size(max = 10000)
    private String descricao;

    @NotNull
    private TipoEvento tipoEvento;

    @NotNull @Positive
    private Long usuarioId;

    @NotNull @Positive
    private Long chamadoId;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getChamadoId() {
        return chamadoId;
    }

    public void setChamadoId(Long chamadoId) {
        this.chamadoId = chamadoId;
    }
}
