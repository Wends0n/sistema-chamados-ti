package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ComentarioService {
    Page<ComentarioResponseDTO> listar(Long chamadoId, Pageable pageable);
    ComentarioResponseDTO buscarPorId(Long id);
    ComentarioResponseDTO criar(ComentarioRequestDTO request);
    ComentarioResponseDTO atualizar(Long id, ComentarioRequestDTO request);
    void excluir(Long id);
}
