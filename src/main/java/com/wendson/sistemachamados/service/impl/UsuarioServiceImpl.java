package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.UsuarioRequestDTO;
import com.wendson.sistemachamados.dto.UsuarioResponseDTO;
import com.wendson.sistemachamados.entity.Usuario;
import com.wendson.sistemachamados.exception.ConflictException;
import com.wendson.sistemachamados.exception.ResourceNotFoundException;
import com.wendson.sistemachamados.mapper.UsuarioMapper;
import com.wendson.sistemachamados.repository.UsuarioRepository;
import com.wendson.sistemachamados.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listar(Pageable pageable) {
        Page<Usuario> entidades = repository.listar(pageable);
        return entidades.map(usuarioMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        return usuarioMapper.toResponse(buscarEntidade(id));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO request) {
        Usuario entidade = usuarioMapper.toEntity(request);
        validarEmailDisponivel(entidade.getEmail(), 0L);
        return usuarioMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO request) {
        Usuario entidade = buscarEntidade(id);
        Usuario dados = usuarioMapper.toEntity(request);

        validarEmailDisponivel(dados.getEmail(), entidade.getId());

        entidade.setNome(dados.getNome());
        entidade.setEmail(dados.getEmail());
        entidade.setTipoUsuario(dados.getTipoUsuario());

        return usuarioMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) { repository.delete(buscarEntidade(id)); }

    private Usuario buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + id));
    }

    private void validarEmailDisponivel(String email, Long usuarioId) {
        if (repository.emailEmUso(email, usuarioId)) {
            throw new ConflictException("Email já cadastrado");
        }
    }

}
