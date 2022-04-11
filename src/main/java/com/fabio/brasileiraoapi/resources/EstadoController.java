package com.fabio.brasileiraoapi.resources;

import com.fabio.brasileiraoapi.domains.Estado;
import com.fabio.brasileiraoapi.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/${api.version}/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @PostMapping
    public ResponseEntity<Mono<Estado>> saveEstado(@RequestBody Estado estado){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estadoRepository.save(estado));
    }

    @GetMapping
    public ResponseEntity<Flux<Estado>> getEstados(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(estadoRepository.findAll());
    }

    @GetMapping("/id")
    public Mono<ResponseEntity<Estado>> getById(@RequestParam(value = "id") String id){
        return estadoRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/sigla")
    public Mono<ResponseEntity<Estado>> getBySigla(@RequestParam(value = "sigla") String sigla){
        return estadoRepository.findBySigla(sigla)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Estado>> updateById(@PathVariable String id, @RequestBody Estado estado){
        return estadoRepository.findById(id)
                .flatMap(e -> {
                    e.setNome(estado.getNome());
                    e.setSigla(estado.getSigla());
                    return estadoRepository.save(e);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable String id){
        return estadoRepository.findById(id)
                .flatMap( e ->estadoRepository.deleteById(e.getId())
                        .then(Mono.just(ResponseEntity.noContent().build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
