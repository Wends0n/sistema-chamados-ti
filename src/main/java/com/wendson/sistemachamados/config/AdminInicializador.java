package com.wendson.sistemachamados.config;

import com.wendson.sistemachamados.entity.TipoUsuario;
import com.wendson.sistemachamados.entity.Usuario;
import com.wendson.sistemachamados.repository.UsuarioRepository;
import com.wendson.sistemachamados.util.SenhaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;

@Component
public class AdminInicializador implements ApplicationRunner {
    private final UsuarioRepository repository;
    private final String email;
    private final String senha;

    public AdminInicializador(UsuarioRepository repository,
                              @Value("${app.admin.email}") String email,
                              @Value("${app.admin.senha}") String senha) {
        this.repository = repository;
        this.email = email;
        this.senha = senha;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // Não sobrescreve a senha nem os dados de um administrador existente.
        if (repository.existeAdministrador()) return;
        String emailNormalizado = email.strip().toLowerCase(Locale.ROOT);
        if (repository.emailEmUso(emailNormalizado, 0L)) {
            throw new IllegalStateException("O email configurado para o administrador já pertence a outro usuário");
        }
        Usuario admin = new Usuario(null, "Administrador", emailNormalizado, TipoUsuario.ADMIN);
        admin.setSenha(SenhaUtil.gerarHash(senha));
        repository.save(admin);
    }
}
