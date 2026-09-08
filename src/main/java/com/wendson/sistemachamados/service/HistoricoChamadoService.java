package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.HistoricoChamadoRequestDTO;
import com.wendson.sistemachamados.dto.HistoricoChamadoResponseDTO;

import java.util.List;

public interface HistoricoChamadoService {
    List<HistoricoChamadoResponseDTO> listar(Long chamadoId);
    HistoricoChamadoResponseDTO buscarPorId(Long id);
    HistoricoChamadoResponseDTO criar(HistoricoChamadoRequestDTO request);
    HistoricoChamadoResponseDTO atualizar(Long id, HistoricoChamadoRequestDTO request);
    void excluir(Long id);
}
