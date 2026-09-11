package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.UsuarioRequestDTO;
import com.wendson.sistemachamados.dto.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {
    Page<UsuarioResponseDTO> listar(Pageable pageable);
    UsuarioResponseDTO buscarPorId(Long id);
    UsuarioResponseDTO criar(UsuarioRequestDTO request);
    UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO request);
    void excluir(Long id);
}
