package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.*;
import com.wendson.sistemachamados.entity.*;
import com.wendson.sistemachamados.repository.*;
import com.wendson.sistemachamados.service.CategoriaService;
import com.wendson.sistemachamados.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listar() {
        List<Categoria> entidades = repository.listar();
        return entidades.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) { return toResponse(buscarEntidade(id)); }

    @Override
    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO request) {
        Categoria entidade = new Categoria();
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO request) {
        Categoria entidade = buscarEntidade(id);
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    private Categoria buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrado: " + id));
    }

    private void copiarDados(CategoriaRequestDTO request, Categoria entidade) {
        entidade.setNome(request.getNome().strip());
        entidade.setDescricao(request.getDescricao().strip());
    }

    private CategoriaResponseDTO toResponse(Categoria entidade) {
        CategoriaResponseDTO response = new CategoriaResponseDTO();
        response.setId(entidade.getId());
        response.setNome(entidade.getNome());
        response.setDescricao(entidade.getDescricao());
        return response;
    }
}
