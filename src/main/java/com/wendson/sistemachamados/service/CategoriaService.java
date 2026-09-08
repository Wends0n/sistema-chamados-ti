package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.*;
import java.util.List;

public interface CategoriaService {
    List<CategoriaResponseDTO> listar();
    CategoriaResponseDTO buscarPorId(Long id);
    CategoriaResponseDTO criar(CategoriaRequestDTO request);
    CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO request);
    void excluir(Long id);
}
