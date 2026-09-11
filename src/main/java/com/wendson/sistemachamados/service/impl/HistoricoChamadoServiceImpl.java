package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.HistoricoChamadoRequestDTO;
import com.wendson.sistemachamados.dto.HistoricoChamadoResponseDTO;
import com.wendson.sistemachamados.entity.Chamado;
import com.wendson.sistemachamados.entity.HistoricoChamado;
import com.wendson.sistemachamados.entity.Usuario;
import com.wendson.sistemachamados.exception.ResourceNotFoundException;
import com.wendson.sistemachamados.mapper.HistoricoChamadoMapper;
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
    private final HistoricoChamadoMapper historicoChamadoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HistoricoChamadoResponseDTO> listar(Long chamadoId) {
        List<HistoricoChamado> entidades = chamadoId == null ? repository.listar() : repository.buscarPorChamado(chamadoId);
        return entidades.stream().map(historicoChamadoMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HistoricoChamadoResponseDTO buscarPorId(Long id) {
        return historicoChamadoMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional
    public HistoricoChamadoResponseDTO criar(HistoricoChamadoRequestDTO request) {
        HistoricoChamado entidade = historicoChamadoMapper.toEntity(request);

        entidade.setUsuario(buscarUsuario(request.getUsuarioId()));
        entidade.setChamado(buscarChamado(request.getChamadoId()));
        entidade.setDataHora(LocalDateTime.now());

        return historicoChamadoMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public HistoricoChamadoResponseDTO atualizar(Long id, HistoricoChamadoRequestDTO request) {
        HistoricoChamado entidade = buscarEntidade(id);
        HistoricoChamado dados = historicoChamadoMapper.toEntity(request);

        Usuario usuario = buscarUsuario(request.getUsuarioId());
        Chamado chamado = buscarChamado(request.getChamadoId());

        entidade.setDescricao(dados.getDescricao());
        entidade.setTipoEvento(dados.getTipoEvento());
        entidade.setUsuario(usuario);
        entidade.setChamado(chamado);

        return historicoChamadoMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    private HistoricoChamado buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("HistoricoChamado não encontrado: " + id));
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + id));
    }

    private Chamado buscarChamado(Long id) {
        return chamadoRepository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado: " + id));
    }

}
