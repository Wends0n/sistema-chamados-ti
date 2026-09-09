package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.HistoricoChamadoRequestDTO;
import com.wendson.sistemachamados.dto.HistoricoChamadoResponseDTO;
import com.wendson.sistemachamados.entity.HistoricoChamado;
import com.wendson.sistemachamados.exception.ResourceNotFoundException;
import com.wendson.sistemachamados.repository.ChamadoRepository;
import com.wendson.sistemachamados.repository.HistoricoChamadoRepository;
import com.wendson.sistemachamados.repository.UsuarioRepository;
import com.wendson.sistemachamados.service.HistoricoChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class HistoricoChamadoServiceImpl implements HistoricoChamadoService {
    private final HistoricoChamadoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ChamadoRepository chamadoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HistoricoChamadoResponseDTO> listar(Long chamadoId) {
        List<HistoricoChamado> entidades = chamadoId == null ? repository.listar() : repository.buscarPorChamado(chamadoId);
        return entidades.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HistoricoChamadoResponseDTO buscarPorId(Long id) { return toResponse(buscarEntidade(id)); }

    @Override
    @Transactional
    public HistoricoChamadoResponseDTO criar(HistoricoChamadoRequestDTO request) {
        HistoricoChamado entidade = new HistoricoChamado();
        entidade.setDataHora(LocalDateTime.now());
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public HistoricoChamadoResponseDTO atualizar(Long id, HistoricoChamadoRequestDTO request) {
        HistoricoChamado entidade = buscarEntidade(id);
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    private HistoricoChamado buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("HistoricoChamado não encontrado: " + id));
    }

    private void copiarDados(HistoricoChamadoRequestDTO request, HistoricoChamado entidade) {
        entidade.setDescricao(request.getDescricao().strip());
        entidade.setTipoEvento(request.getTipoEvento());
        entidade.setUsuario(usuarioRepository.buscarPorId(request.getUsuarioId()).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + request.getUsuarioId())));
        entidade.setChamado(chamadoRepository.buscarPorId(request.getChamadoId()).orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado: " + request.getChamadoId())));
    }

    private HistoricoChamadoResponseDTO toResponse(HistoricoChamado entidade) {
        HistoricoChamadoResponseDTO response = new HistoricoChamadoResponseDTO();
        response.setId(entidade.getId());
        response.setDescricao(entidade.getDescricao());
        response.setTipoEvento(entidade.getTipoEvento());
        response.setUsuarioId(entidade.getUsuario() == null ? null : entidade.getUsuario().getId());
        response.setChamadoId(entidade.getChamado() == null ? null : entidade.getChamado().getId());
        response.setDataHora(entidade.getDataHora());
        return response;
    }
}
