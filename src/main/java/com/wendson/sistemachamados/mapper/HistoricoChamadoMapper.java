package com.wendson.sistemachamados.mapper;

import com.wendson.sistemachamados.dto.HistoricoChamadoRequestDTO;
import com.wendson.sistemachamados.dto.HistoricoChamadoResponseDTO;
import com.wendson.sistemachamados.entity.HistoricoChamado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoricoChamadoMapper extends TextoMapper{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "descricao", source = "descricao", qualifiedByName = "normalizarTexto")

    @Mapping(target = "dataHora", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "chamado", ignore = true)

    HistoricoChamado toEntity(HistoricoChamadoRequestDTO request);

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "chamadoId", source = "chamado.id")

    HistoricoChamadoResponseDTO toResponse(HistoricoChamado entidade);

}
