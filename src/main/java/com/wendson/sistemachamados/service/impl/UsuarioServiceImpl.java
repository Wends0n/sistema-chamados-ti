package com.wendson.sistemachamados.service.impl;

import com.wendson.sistemachamados.dto.UsuarioRequestDTO;
import com.wendson.sistemachamados.dto.UsuarioResponseDTO;
import com.wendson.sistemachamados.entity.Usuario;
import com.wendson.sistemachamados.entity.Tecnico;
import com.wendson.sistemachamados.entity.TipoUsuario;
import com.wendson.sistemachamados.exception.BusinessException;
import com.wendson.sistemachamados.util.SenhaUtil;
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
        if (request.getTipoUsuario() == TipoUsuario.ADMIN) {
            throw new BusinessException("O administrador é criado automaticamente na inicialização");
        }
        validarEspecialidade(request);
        Usuario entidade = usuarioMapper.toEntity(request);
        validarEmailDisponivel(entidade.getEmail(), 0L);
        entidade.setSenha(SenhaUtil.gerarHash(request.getSenha()));
        atualizarEspecialidade(entidade, request);
        return usuarioMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO request) {
        Usuario entidade = buscarEntidade(id);
        Usuario dados = usuarioMapper.toEntity(request);
        validarEspecialidade(request);
        if (entidade.getTipoUsuario() != dados.getTipoUsuario()) {
            if (entidade.getTipoUsuario() == TipoUsuario.ADMIN || dados.getTipoUsuario() == TipoUsuario.ADMIN) {
                throw new BusinessException("Não é permitido alterar o tipo do administrador ou promover usuários a ADMIN");
            }
            if (entidade.getTipoUsuario() == TipoUsuario.TECNICO && !entidade.getChamadosAtribuidos().isEmpty()) {
                throw new BusinessException("Reatribua os chamados antes de alterar o tipo do técnico");
            }
        }

        validarEmailDisponivel(dados.getEmail(), entidade.getId());

        entidade.setNome(dados.getNome());
        entidade.setEmail(dados.getEmail());
        entidade.setTipoUsuario(dados.getTipoUsuario());
        entidade.setSenha(SenhaUtil.gerarHash(request.getSenha()));
        atualizarEspecialidade(entidade, request);

        return usuarioMapper.toResponse(repository.save(entidade));
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Usuario entidade = buscarEntidade(id);
        if (entidade.getTipoUsuario() == TipoUsuario.ADMIN) {
            throw new BusinessException("O administrador inicial não pode ser excluído");
        }
        repository.delete(entidade);
    }

    private void validarEspecialidade(UsuarioRequestDTO request) {
        if (request.getTipoUsuario() == TipoUsuario.TECNICO) {
            if (request.getEspecialidade() == null || request.getEspecialidade().isBlank()) {
                throw new BusinessException("Especialidade é obrigatória para técnicos");
            }
        } else if (request.getEspecialidade() != null && !request.getEspecialidade().isBlank()) {
            throw new BusinessException("Somente técnicos possuem especialidade");
        }
    }

    private void atualizarEspecialidade(Usuario entidade, UsuarioRequestDTO request) {
        if (entidade.getTipoUsuario() == TipoUsuario.TECNICO) {
            if (entidade.getTecnico() == null) entidade.setTecnico(new Tecnico());
            entidade.getTecnico().setEspecialidade(request.getEspecialidade().strip());
        } else {
            entidade.setTecnico(null);
        }
    }

    private Usuario buscarEntidade(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado: " + id));
    }

    private void validarEmailDisponivel(String email, Long usuarioId) {
        if (repository.emailEmUso(email, usuarioId)) {
            throw new ConflictException("Email já cadastrado");
        }
    }

}
