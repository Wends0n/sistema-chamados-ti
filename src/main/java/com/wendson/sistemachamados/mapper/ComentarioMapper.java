package com.wendson.sistemachamados.mapper;

import com.wendson.sistemachamados.dto.ComentarioRequestDTO;
import com.wendson.sistemachamados.dto.ComentarioResponseDTO;
import com.wendson.sistemachamados.entity.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ComentarioMapper extends TextoMapper{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mensagem", source = "mensagem", qualifiedByName = "normalizarTexto")

    @Mapping(target = "dataHora", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "chamado", ignore = true)

    Comentario toEntity(ComentarioRequestDTO request);

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "chamadoId", source = "chamado.id")

    ComentarioResponseDTO toResponse(Comentario entidade);

}
