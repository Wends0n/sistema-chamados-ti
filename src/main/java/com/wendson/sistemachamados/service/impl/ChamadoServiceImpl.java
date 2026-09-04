package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.ChamadoRequestDTO;
import com.wendson.sistemachamados.dto.ChamadoResponseDTO;
import com.wendson.sistemachamados.entity.Chamado;
import com.wendson.sistemachamados.exception.ResourceNotFoundException;
import com.wendson.sistemachamados.repository.ChamadoRepository;
import com.wendson.sistemachamados.service.ChamadoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ChamadoServiceImpl implements ChamadoService {
    private final ChamadoRepository repository;

    //Injeção de dependencia por construtor
    //ChamadoServiceImpl depende de Repository
    public ChamadoServiceImpl(ChamadoRepository repository) { this.repository = repository; }

    //Transação destinada apenas para leitura
    @Override @Transactional(readOnly = true)
    public List<ChamadoResponseDTO> listar(String titulo) {
        List<Chamado> chamados = (titulo == null || titulo.isBlank()) ? repository.findAll() : repository.findByTituloContainingIgnoreCase(titulo);
        return chamados.stream().map(this::toResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public ChamadoResponseDTO buscarPorId(Long id) { return toResponse(buscarEntidade(id)); }

    @Override @Transactional
    public ChamadoResponseDTO criar(ChamadoRequestDTO request) {
        Chamado chamado = new Chamado();
        copiarDados(request, chamado);
        return toResponse(repository.save(chamado));
    }

    @Override @Transactional
    public ChamadoResponseDTO atualizar(Long id, ChamadoRequestDTO request) {
        Chamado chamado = buscarEntidade(id);
        copiarDados(request, chamado);
        return toResponse(repository.save(chamado));
    }

    @Override @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    @Override @Transactional
    public void excluirTodos() { repository.deleteAll(); }

    private Chamado buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado: " + id));
    }

    private void copiarDados(ChamadoRequestDTO request, Chamado chamado) {
        chamado.setTitulo(request.getTitulo());
        chamado.setDescricao(request.getDescricao());
        chamado.setSolicitante(request.getSolicitante());
        chamado.setEstado(request.getEstado());
        chamado.setPrioridade(request.getPrioridade());
    }



    //Converte Entity -> DTO
    //Mantem o controller independente da entity
    private ChamadoResponseDTO toResponse(Chamado chamado) {
        ChamadoResponseDTO response = new ChamadoResponseDTO();
        response.setId(chamado.getId());
        response.setTitulo(chamado.getTitulo());
        response.setDescricao(chamado.getDescricao());
        response.setSolicitante(chamado.getSolicitante());
        response.setEstado(chamado.getEstado());
        response.setPrioridade(chamado.getPrioridade());
        response.setDataAbertura(chamado.getDataAbertura());
        return response;
    }
}
