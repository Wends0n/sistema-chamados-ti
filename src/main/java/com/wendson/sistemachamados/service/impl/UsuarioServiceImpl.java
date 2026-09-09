package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.UsuarioRequestDTO;
import com.wendson.sistemachamados.dto.UsuarioResponseDTO;
import com.wendson.sistemachamados.entity.Usuario;
import com.wendson.sistemachamados.exception.ConflictException;
import com.wendson.sistemachamados.exception.ResourceNotFoundException;
import com.wendson.sistemachamados.repository.UsuarioRepository;
import com.wendson.sistemachamados.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        List<Usuario> entidades = repository.listar();
        return entidades.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) { return toResponse(buscarEntidade(id)); }

    @Override
    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO request) {
        Usuario entidade = new Usuario();
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO request) {
        Usuario entidade = buscarEntidade(id);
        copiarDados(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    private Usuario buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + id));
    }

    private void copiarDados(UsuarioRequestDTO request, Usuario entidade) {
        entidade.setNome(request.getNome().strip());
        entidade.setEmail(request.getEmail().strip().toLowerCase(java.util.Locale.ROOT));
        entidade.setTipoUsuario(request.getTipoUsuario());
        if (repository.emailEmUso(entidade.getEmail(), entidade.getId() == null ? 0L : entidade.getId())) {
            throw new ConflictException("Email já cadastrado");
        }
    }

    private UsuarioResponseDTO toResponse(Usuario entidade) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(entidade.getId());
        response.setNome(entidade.getNome());
        response.setEmail(entidade.getEmail());
        response.setTipoUsuario(entidade.getTipoUsuario());
        return response;
    }
}
