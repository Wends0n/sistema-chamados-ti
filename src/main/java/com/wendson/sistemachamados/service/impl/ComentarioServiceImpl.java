package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.*;
import com.wendson.sistemachamados.entity.*;
import com.wendson.sistemachamados.mapper.ComentarioMapper;
import com.wendson.sistemachamados.repository.*;
import com.wendson.sistemachamados.service.ComentarioService;
import com.wendson.sistemachamados.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ComentarioServiceImpl implements ComentarioService {
    private final ComentarioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final ComentarioMapper comentarioMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ComentarioResponseDTO> listar(Long chamadoId, Pageable pageable) {
        Page<Comentario> entidades = chamadoId == null ? repository.listar(pageable) : repository.buscarPorChamado(chamadoId, pageable);
        return entidades.map(comentarioMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ComentarioResponseDTO buscarPorId(Long id) {
        return comentarioMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional
    public ComentarioResponseDTO criar(ComentarioRequestDTO request) {
        Comentario entidade = comentarioMapper.toEntity(request);

        entidade.setUsuario(buscarUsuario(request.getUsuarioId()));
        entidade.setChamado(buscarChamado(request.getChamadoId()));
        entidade.setDataHora(LocalDateTime.now());

        return comentarioMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public ComentarioResponseDTO atualizar(Long id, ComentarioRequestDTO request) {
        Comentario entidade = buscarEntidade(id);
        Comentario dados = comentarioMapper.toEntity(request);

        Usuario usuario = buscarUsuario(request.getUsuarioId());
        Chamado chamado = buscarChamado(request.getChamadoId());

        entidade.setMensagem(dados.getMensagem());
        entidade.setUsuario(usuario);
        entidade.setChamado(chamado);

        return comentarioMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    private Comentario buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Comentario não encontrado: " + id));
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + id));
    }

    private Chamado buscarChamado(Long id) {
        return chamadoRepository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado: " + id));
    }

}
