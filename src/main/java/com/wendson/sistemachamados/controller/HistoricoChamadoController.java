package com.wendson.sistemachamados.controller;

import com.wendson.sistemachamados.dto.HistoricoChamadoRequestDTO;
import com.wendson.sistemachamados.dto.HistoricoChamadoResponseDTO;
import com.wendson.sistemachamados.service.HistoricoChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/historicos-chamados")
@RequiredArgsConstructor
public class HistoricoChamadoController {
    private final HistoricoChamadoService service;

    @GetMapping
    public ResponseEntity<Page<HistoricoChamadoResponseDTO>> listar(@RequestParam(required = false) Long chamadoId, Pageable pageable) {
        return ResponseEntity.ok(service.listar(chamadoId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<HistoricoChamadoResponseDTO> criar(@Valid @RequestBody HistoricoChamadoRequestDTO request) {
        var response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/historicos-chamados/" + response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoChamadoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody HistoricoChamadoRequestDTO request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
