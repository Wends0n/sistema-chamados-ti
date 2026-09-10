package com.wendson.sistemachamados.mapper;

import org.mapstruct.Named;
import java.util.Locale;

///Sera herdada pelos outros mappers, nao precisa de @Mapper pq fornece apenas metodos auxiliares
public interface TextoMapper {
    @Named("normalizarTexto")
    default String normalizarTexto(String valor){
        return valor == null ? null : valor.strip();
    }
    @Named("normalizarEmail")
    default String normalizarEmail(String valor){
        return valor == null ? null : valor.strip().toLowerCase(Locale.ROOT);
    }

}
