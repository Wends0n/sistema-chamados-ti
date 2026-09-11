package com.wendson.sistemachamados.mapper;

import com.wendson.sistemachamados.dto.ChamadoRequestDTO;
import com.wendson.sistemachamados.dto.ChamadoResponseDTO;
import com.wendson.sistemachamados.entity.Chamado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChamadoMapper extends TextoMapper{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "titulo", source = "titulo", qualifiedByName = "normalizarTexto")
    @Mapping(target = "descricao", source = "descricao", qualifiedByName = "normalizarTexto")

    @Mapping(target = "dataAbertura", ignore = true)
    @Mapping(target = "dataResolucao", ignore = true)
    @Mapping(target = "solicitante", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    @Mapping(target = "categoria", ignore = true)

    @Mapping(target = "comentarioChamado", ignore = true)
    @Mapping(target = "historicoChamado", ignore = true)

    Chamado toEntity(ChamadoRequestDTO request);

    @Mapping(target = "solicitanteId", source = "solicitante.id")
    @Mapping(target = "tecnicoId", source = "tecnico.id")
    @Mapping(target = "categoriaId", source = "categoria.id")

    ChamadoResponseDTO toResponse(Chamado entidade);

}
