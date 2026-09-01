package com.wendson.sistemachamados.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wendson.sistemachamados.repository.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.wendson.sistemachamados.entity.Chamado;
import com.wendson.sistemachamados.controller.ChamadoController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ChamadoController {

    @Autowired
    ChamadoRepository chamadoRepository;

    @GetMapping("/chamado")
    public ResponseEntity<List<Chamado>> getTodosChamados(@RequestParam(required = false) String titulo){
        try{
            List<Chamado> chamados = new ArrayList<Chamado>();
            if (titulo == null)
                chamadoRepository.findAll().forEach(chamados::add);
            else
                chamadoRepository.encontraTitulo(titulo).forEach(chamados::add);
            if (chamados.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(chamados,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chamado/{id}")
    public ResponseEntity<Chamado> getChamadoId(@PathVariable("id") long id){
        Optional<Chamado> chamadoData = chamadoRepository.findById(id);

        if(chamadoData.isPresent())
            return new ResponseEntity<>(chamadoData.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/chamado")
    public ResponseEntity<Chamado> criarChamado(@RequestBody Chamado chamado){
        try{
            Chamado _chamado = chamadoRepository.save(new Chamado(chamado.getTitulo(),chamado.getDescricao()));
            return new ResponseEntity<>(_chamado, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}
