package com.wendson.sistemachamados.controller;

import java.util.List;

import com.wendson.sistemachamados.dto.ChamadoRequestDTO;
import com.wendson.sistemachamados.dto.ChamadoResponseDTO;
import com.wendson.sistemachamados.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/chamados")

//Representa a camada HTTP
//Nao deve conter regra de acesso ao banco
//HTTP -> Controller -> Service

public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) { this.service = service; }


    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listar(@RequestParam(required = false) String titulo) {
        return ResponseEntity.ok(service.listar(titulo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criar(@Valid @RequestBody ChamadoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ChamadoRequestDTO request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> excluirTodos() {
        service.excluirTodos();
        return ResponseEntity.noContent().build();
    }

}
