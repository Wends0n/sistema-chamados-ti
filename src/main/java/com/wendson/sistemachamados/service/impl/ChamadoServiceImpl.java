package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.*;
import com.wendson.sistemachamados.entity.*;
import com.wendson.sistemachamados.mapper.ChamadoMapper;
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

public class ChamadoServiceImpl implements ChamadoService {
    private final ChamadoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ChamadoMapper chamadoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ChamadoResponseDTO> listar(String titulo) {
        List<Chamado> entidades = (titulo == null || titulo.isBlank()) ? repository.listar() : repository.buscarPorTitulo(titulo);
        return entidades.stream().map(chamadoMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ChamadoResponseDTO buscarPorId(Long id) {
        return chamadoMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional
    public ChamadoResponseDTO criar(ChamadoRequestDTO request) {
        Chamado entidade = chamadoMapper.toEntity(request);

        entidade.setSolicitante(buscarUsuario(request.getSolicitanteId()));
        entidade.setTecnico(buscarTecnico(request.getTecnicoId()));
        entidade.setCategoria(buscarCategoria(request.getCategoriaId()));
        entidade.setDataAbertura(LocalDateTime.now());

        ajustarDataResolucao(entidade);

        return chamadoMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public ChamadoResponseDTO atualizar(Long id, ChamadoRequestDTO request) {
        Chamado entidade = buscarEntidade(id);
        Chamado dados = chamadoMapper.toEntity(request);

        Usuario solicitante = buscarUsuario(request.getSolicitanteId());
        Usuario tecnico = buscarTecnico(request.getTecnicoId());
        Categoria categoria = buscarCategoria(request.getCategoriaId());

        entidade.setTitulo(dados.getTitulo());
        entidade.setDescricao(dados.getDescricao());
        entidade.setEstado(dados.getEstado());
        entidade.setPrioridade(dados.getPrioridade());

        entidade.setSolicitante(solicitante);
        entidade.setTecnico(tecnico);
        entidade.setCategoria(categoria);

        ajustarDataResolucao(entidade);

        return chamadoMapper.toResponse(repository.save(entidade));
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

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + id));
    }

    private Usuario buscarTecnico(Long id) {
        if (id == null) {
            return null;
        }

        Usuario tecnico = buscarUsuario(id);

        if (tecnico.getTipoUsuario() != TipoUsuario.TECNICO) {
            throw new BusinessException(
                    "O usuário atribuído como técnico deve ser do tipo TECNICO"
            );
        }

        return tecnico;
    }

    private Categoria buscarCategoria(Long id) {
        return categoriaRepository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrado: " + id));
    }

    private void ajustarDataResolucao(Chamado entidade) {
        if (entidade.getEstado() == Estado.FINALIZADO) {
            if (entidade.getDataResolucao() == null) {
                entidade.setDataResolucao(LocalDateTime.now());
            }
        } else {
            entidade.setDataResolucao(null);
        }
    }

}
