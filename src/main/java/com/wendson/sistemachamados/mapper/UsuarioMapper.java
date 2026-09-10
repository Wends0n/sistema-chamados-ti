package com.wendson.sistemachamados.mapper;

import com.wendson.sistemachamados.dto.UsuarioResponseDTO;
import com.wendson.sistemachamados.dto.UsuarioRequestDTO;
import com.wendson.sistemachamados.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper extends TextoMapper{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", source = "nome", qualifiedByName = "normalizarTexto")
    @Mapping(target = "email", source = "email", qualifiedByName = "normalizarEmail")

    @Mapping(target = "chamadosSolicitados", ignore = true)
    @Mapping(target = "chamadosAtribuidos", ignore = true)
    @Mapping(target = "comentarioUsuario", ignore = true)
    @Mapping(target = "historicoChamadoUsuario", ignore = true)

    Usuario toEntity(UsuarioRequestDTO request);
    UsuarioResponseDTO toResponse(Usuario entidade);

}
