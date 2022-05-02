package com.fabio.brasileiraoapi.resources;

import com.fabio.brasileiraoapi.domains.Partida;
import com.fabio.brasileiraoapi.repositories.ClubeRepository;
import com.fabio.brasileiraoapi.repositories.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/${api.version}/partidas")
public class PartidaController {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeRepository clubeRepository;

    @PostMapping
    public ResponseEntity<Mono<Partida>> save(@RequestBody Partida partida){
       return ResponseEntity.status(HttpStatus.CREATED).body(partidaRepository.save(partida));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Partida> getAll(){
        return partidaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Partida>> getById(@PathVariable String id){
        return partidaRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Partida>> updateById(@PathVariable String id, @RequestBody Partida partida){
        return partidaRepository.findById(id)
                .flatMap(c -> {
                    c.setData(partida.getData());
                    c.setMandante(partida.getMandante());
                    c.setVisitante(partida.getVisitante());
                    c.setGolsMandante(partida.getGolsMandante());
                    c.setGolsVisitante(partida.getGolsVisitante());
                    return partidaRepository.save(c);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id){
        return partidaRepository.findById(id)
                .flatMap( c -> partidaRepository.deleteById(c.getId())
                        .then(Mono.just(ResponseEntity.noContent().build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }



}
