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

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ChamadoController {

    @Autowired
    ChamadoRepository chamadoRepository;

    @GetMapping("/chamados")
    public ResponseEntity<List<Chamado>> getAllChamados(@RequestParam(required = false) String titulo){
        try{
            List<Chamado> chamados = new ArrayList<Chamado>();
            if (titulo == null)
                chamadoRepository.findAll().forEach(chamados::add);
            else
                chamadoRepository.findByTituloContaining(titulo).forEach(chamados::add);
            if (chamados.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(chamados,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chamados/{id}")
    public ResponseEntity<Chamado> getChamadoId(@PathVariable("id") long id){
        Optional<Chamado> chamadoData = chamadoRepository.findById(id);

        if(chamadoData.isPresent())
            return new ResponseEntity<>(chamadoData.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/chamados")
    public ResponseEntity<Chamado> createChamado(@RequestBody Chamado chamado){
        try{
            Chamado _chamado = chamadoRepository.save(new Chamado(chamado.getTitulo(),chamado.getDescricao(),chamado.getSolicitante(),chamado.getPrioridade(),chamado.getEstado()));
            return new ResponseEntity<>(_chamado, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/chamados/{id}")
    public ResponseEntity<Chamado> updateChamado(@PathVariable("id") long id, @RequestBody Chamado chamado){
        Optional<Chamado> chamadoData = chamadoRepository.findById(id);

        if(chamadoData.isPresent()){
            Chamado chamadoResposta = chamadoData.get();
            chamadoResposta.setTitulo(chamadoResposta.getTitulo());
            chamadoResposta.setDescricao(chamadoResposta.getDescricao());
            chamadoResposta.setSolicitante(chamadoResposta.getSolicitante());
            chamadoResposta.setPrioridade(chamadoResposta.getPrioridade());
            chamadoResposta.setEstado(chamadoResposta.getEstado());
            return new ResponseEntity<>(chamadoRepository.save(chamadoResposta), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/chamados/{id}")
    public ResponseEntity<HttpStatus> deleteChamado(@PathVariable("id") long id ){
        try{
            chamadoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/chamados")
    public ResponseEntity<HttpStatus> deleteAllChamados(){
        try{
            chamadoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
