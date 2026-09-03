package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.ChamadoRequestDTO;
import com.wendson.sistemachamados.dto.ChamadoResponseDTO;

import java.util.List;

public interface ChamadoService {
    List<ChamadoResponseDTO> listar(String titulo);
    ChamadoResponseDTO buscarPorId(Long id);
    ChamadoResponseDTO criar(ChamadoRequestDTO request);
    ChamadoResponseDTO atualizar(Long id, ChamadoRequestDTO request);
    void excluir(Long id);
    void excluirTodos();
}
