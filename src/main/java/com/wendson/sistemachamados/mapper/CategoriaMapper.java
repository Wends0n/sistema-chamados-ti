package com.wendson.sistemachamados.mapper;

import com.wendson.sistemachamados.dto.CategoriaRequestDTO;
import com.wendson.sistemachamados.dto.CategoriaResponseDTO;
import com.wendson.sistemachamados.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper extends TextoMapper{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", source = "nome", qualifiedByName = "normalizarTexto")
    @Mapping(target = "descricao", source = "descricao", qualifiedByName = "normalizarTexto")

    @Mapping(target = "categoriaChamado", ignore = true)

    Categoria toEntity(CategoriaRequestDTO request);
    CategoriaResponseDTO toResponse(Categoria entidade);

}
