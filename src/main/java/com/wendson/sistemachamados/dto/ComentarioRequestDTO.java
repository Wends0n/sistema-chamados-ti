package com.wendson.sistemachamados.dto;

import com.wendson.sistemachamados.entity.*;
import jakarta.validation.constraints.*;

public class ComentarioRequestDTO {
    @NotBlank @Size(max = 10000)
    private String mensagem;

    @NotNull @Positive
    private Long usuarioId;

    @NotNull @Positive
    private Long chamadoId;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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
