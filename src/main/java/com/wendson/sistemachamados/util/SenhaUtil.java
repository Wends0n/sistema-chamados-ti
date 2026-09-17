package com.wendson.sistemachamados.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

/** Armazena somente o hash. Não implementa login nem controle de permissões. */
public final class SenhaUtil {
    private static final int ITERACOES = 600_000;
    private static final SecureRandom RANDOM = new SecureRandom();

    private SenhaUtil() {
    }

    public static String gerarHash(String senha) {
        if (senha == null || senha.isBlank() || senha.length() < 8 || senha.length() > 128) {
            throw new IllegalArgumentException("A senha deve ter entre 8 e 128 caracteres e não pode ser em branco");
        }
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, ITERACOES, 256);
        try {
            byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
            return "pbkdf2_sha256$" + ITERACOES + "$"
                    + Base64.getEncoder().encodeToString(salt) + "$"
                    + Base64.getEncoder().encodeToString(hash);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Não foi possível gerar o hash da senha", e);
        } finally {
            spec.clearPassword();
        }
    }
}
