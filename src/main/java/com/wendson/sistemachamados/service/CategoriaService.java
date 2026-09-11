package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService {
    Page<CategoriaResponseDTO> listar(Pageable pageable);
    CategoriaResponseDTO buscarPorId(Long id);
    CategoriaResponseDTO criar(CategoriaRequestDTO request);
    CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO request);
    void excluir(Long id);
}
