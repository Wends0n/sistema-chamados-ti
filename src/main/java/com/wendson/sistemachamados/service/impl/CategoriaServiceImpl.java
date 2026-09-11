package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.*;
import com.wendson.sistemachamados.entity.*;
import com.wendson.sistemachamados.mapper.CategoriaMapper;
import com.wendson.sistemachamados.repository.*;
import com.wendson.sistemachamados.service.CategoriaService;
import com.wendson.sistemachamados.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository repository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listar() {
        List<Categoria> entidades = repository.listar();
        return entidades.stream().map(categoriaMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        return categoriaMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO request) {
        Categoria entidade = categoriaMapper.toEntity(request);

        return categoriaMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO request) {
        Categoria entidade = buscarEntidade(id);
        Categoria dados = categoriaMapper.toEntity(request);

        entidade.setNome(dados.getNome());
        entidade.setDescricao(dados.getDescricao());

        return categoriaMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    private Categoria buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrado: " + id));
    }
}
