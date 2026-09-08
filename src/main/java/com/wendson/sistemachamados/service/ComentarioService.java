package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.*;
import java.util.List;

public interface ComentarioService {
    List<ComentarioResponseDTO> listar(Long chamadoId);
    ComentarioResponseDTO buscarPorId(Long id);
    ComentarioResponseDTO criar(ComentarioRequestDTO request);
    ComentarioResponseDTO atualizar(Long id, ComentarioRequestDTO request);
    void excluir(Long id);
}
