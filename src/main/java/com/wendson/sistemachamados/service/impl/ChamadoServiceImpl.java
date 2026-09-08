package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.*;
import com.wendson.sistemachamados.entity.*;
import com.wendson.sistemachamados.repository.*;
import com.wendson.sistemachamados.service.ChamadoService;
import com.wendson.sistemachamados.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChamadoServiceImpl implements ChamadoService {
    private final ChamadoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public List<ChamadoResponseDTO> listar(String titulo) {
        List<Chamado> entidades = (titulo == null || titulo.isBlank()) ? repository.listar() : repository.buscarPorTitulo(titulo);
        return entidades.stream().map(this::toResponse).toList();
    }

    @Override
    public ChamadoResponseDTO buscarPorId(Long id) { return toResponse(buscarEntidade(id)); }

    @Override
    @Transactional
    public ChamadoResponseDTO criar(ChamadoRequestDTO request) {
        Chamado entidade = new Chamado();
        entidade.setDataAbertura(LocalDateTime.now());
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public ChamadoResponseDTO atualizar(Long id, ChamadoRequestDTO request) {
        Chamado entidade = buscarEntidade(id);
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    @Override
    @Transactional
    public void excluirTodos() { repository.deleteAll(repository.listar()); }

    private Chamado buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado: " + id));
    }

    private void copiarDados(ChamadoRequestDTO request, Chamado entidade) {
        entidade.setTitulo(request.getTitulo().strip());
        entidade.setDescricao(request.getDescricao().strip());
        entidade.setEstado(request.getEstado());
        entidade.setPrioridade(request.getPrioridade());
        entidade.setSolicitante(usuarioRepository.buscarPorId(request.getSolicitanteId()).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + request.getSolicitanteId())));
        entidade.setTecnico(request.getTecnicoId() == null ? null : usuarioRepository.buscarPorId(request.getTecnicoId()).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + request.getTecnicoId())));
        entidade.setCategoria(categoriaRepository.buscarPorId(request.getCategoriaId()).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrado: " + request.getCategoriaId())));
        if (entidade.getTecnico() != null && entidade.getTecnico().getTipoUsuario() != TipoUsuario.TECNICO) {
            throw new BusinessException("O usuário atribuído como técnico deve ser do tipo TECNICO");
        }
        if (entidade.getEstado() == Estado.FINALIZADO) {
            if (entidade.getDataResolucao() == null) entidade.setDataResolucao(LocalDateTime.now());
        } else {
            entidade.setDataResolucao(null);
        }
    }

    private ChamadoResponseDTO toResponse(Chamado entidade) {
        ChamadoResponseDTO response = new ChamadoResponseDTO();
        response.setId(entidade.getId());
        response.setTitulo(entidade.getTitulo());
        response.setDescricao(entidade.getDescricao());
        response.setEstado(entidade.getEstado());
        response.setPrioridade(entidade.getPrioridade());
        response.setSolicitanteId(entidade.getSolicitante() == null ? null : entidade.getSolicitante().getId());
        response.setTecnicoId(entidade.getTecnico() == null ? null : entidade.getTecnico().getId());
        response.setCategoriaId(entidade.getCategoria() == null ? null : entidade.getCategoria().getId());
        response.setDataAbertura(entidade.getDataAbertura());
        response.setDataResolucao(entidade.getDataResolucao());
        return response;
    }
}
