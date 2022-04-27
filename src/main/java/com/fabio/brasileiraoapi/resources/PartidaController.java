package com.fabio.brasileiraoapi.resources;

import com.fabio.brasileiraoapi.domains.Clube;
import com.fabio.brasileiraoapi.domains.Partida;
import com.fabio.brasileiraoapi.dtos.PartidaDtoNew;
import com.fabio.brasileiraoapi.repositories.ClubeRepository;
import com.fabio.brasileiraoapi.repositories.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/${api.version}/partidas")
public class PartidaController {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeRepository clubeRepository;

    @PostMapping
    public Mono<ResponseEntity<Partida>> save(@RequestParam(value = "idMandante") String idMandante,
                                              @RequestParam(value = "idVisitante") String idVisitante,
                                              @RequestBody PartidaDtoNew partidaDtoNew){

        return null;
    }




}
