package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ChamadoService {
    Page<ChamadoResponseDTO> listar(String titulo, Pageable pageable);
    ChamadoResponseDTO buscarPorId(Long id);
    ChamadoResponseDTO criar(ChamadoRequestDTO request);
    ChamadoResponseDTO atualizar(Long id, ChamadoRequestDTO request);
    void excluir(Long id);
    void excluirTodos();
}
