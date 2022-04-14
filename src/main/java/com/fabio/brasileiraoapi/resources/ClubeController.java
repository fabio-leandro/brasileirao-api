package com.fabio.brasileiraoapi.resources;

import com.fabio.brasileiraoapi.domains.Clube;
import com.fabio.brasileiraoapi.repositories.CidadeRepository;
import com.fabio.brasileiraoapi.repositories.ClubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/${api.version}/clubes")
public class ClubeController {

    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @PostMapping
    public Mono<ResponseEntity<Clube>> save(@RequestParam(value = "nomeCidade") String nomeCidade,
                                            @RequestBody Clube clube){
        return cidadeRepository.findByNome(nomeCidade)
                .flatMap( c -> {
                    clube.setCidade(c);
                    return clubeRepository.save(clube);
                }).map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<Flux<Clube>> getAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(clubeRepository.findAll());
    }



}
