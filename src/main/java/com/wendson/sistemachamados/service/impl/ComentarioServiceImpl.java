package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.*;
import com.wendson.sistemachamados.entity.*;
import com.wendson.sistemachamados.repository.*;
import com.wendson.sistemachamados.service.ComentarioService;
import com.wendson.sistemachamados.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ComentarioServiceImpl implements ComentarioService {
    private final ComentarioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ChamadoRepository chamadoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDTO> listar(Long chamadoId) {
        List<Comentario> entidades = chamadoId == null ? repository.listar() : repository.buscarPorChamado(chamadoId);
        return entidades.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ComentarioResponseDTO buscarPorId(Long id) { return toResponse(buscarEntidade(id)); }

    @Override
    @Transactional
    public ComentarioResponseDTO criar(ComentarioRequestDTO request) {
        Comentario entidade = new Comentario();
        entidade.setDataHora(LocalDateTime.now());
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public ComentarioResponseDTO atualizar(Long id, ComentarioRequestDTO request) {
        Comentario entidade = buscarEntidade(id);
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    private Comentario buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Comentario não encontrado: " + id));
    }

    private void copiarDados(ComentarioRequestDTO request, Comentario entidade) {
        entidade.setMensagem(request.getMensagem().strip());
        entidade.setUsuario(usuarioRepository.buscarPorId(request.getUsuarioId()).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + request.getUsuarioId())));
        entidade.setChamado(chamadoRepository.buscarPorId(request.getChamadoId()).orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado: " + request.getChamadoId())));
    }

    private ComentarioResponseDTO toResponse(Comentario entidade) {
        ComentarioResponseDTO response = new ComentarioResponseDTO();
        response.setId(entidade.getId());
        response.setMensagem(entidade.getMensagem());
        response.setUsuarioId(entidade.getUsuario() == null ? null : entidade.getUsuario().getId());
        response.setChamadoId(entidade.getChamado() == null ? null : entidade.getChamado().getId());
        response.setDataHora(entidade.getDataHora());
        return response;
    }
}
