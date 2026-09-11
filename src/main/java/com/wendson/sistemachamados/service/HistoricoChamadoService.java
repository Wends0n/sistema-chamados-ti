package com.wendson.sistemachamados.service;

import com.wendson.sistemachamados.dto.HistoricoChamadoRequestDTO;
import com.wendson.sistemachamados.dto.HistoricoChamadoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoricoChamadoService {
    Page<HistoricoChamadoResponseDTO> listar(Long chamadoId, Pageable pageable);
    HistoricoChamadoResponseDTO buscarPorId(Long id);
    HistoricoChamadoResponseDTO criar(HistoricoChamadoRequestDTO request);
    HistoricoChamadoResponseDTO atualizar(Long id, HistoricoChamadoRequestDTO request);
    void excluir(Long id);
}
