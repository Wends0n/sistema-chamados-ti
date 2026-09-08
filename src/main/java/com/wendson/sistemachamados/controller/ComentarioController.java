package com.wendson.sistemachamados.controller;

import com.wendson.sistemachamados.dto.*;
import com.wendson.sistemachamados.service.ComentarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {
    private final ComentarioService service;

    @GetMapping
    public ResponseEntity<List<ComentarioResponseDTO>> listar(@RequestParam(required = false) Long chamadoId) {
        return ResponseEntity.ok(service.listar(chamadoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> criar(@Valid @RequestBody ComentarioRequestDTO request) {
        var response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/comentarios/" + response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComentarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ComentarioRequestDTO request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
