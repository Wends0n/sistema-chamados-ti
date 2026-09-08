package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.UsuarioRequestDTO;
import com.wendson.sistemachamados.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> listar();
    UsuarioResponseDTO buscarPorId(Long id);
    UsuarioResponseDTO criar(UsuarioRequestDTO request);
    UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO request);
    void excluir(Long id);
}
