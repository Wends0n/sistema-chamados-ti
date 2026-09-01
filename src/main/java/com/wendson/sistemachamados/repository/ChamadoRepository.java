package com.wendson.sistemachamados.repository;

import java.util.List;

import com.wendson.sistemachamados.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado,Long>{
    List<Chamado> encontraTitulo(String titulo);

}
