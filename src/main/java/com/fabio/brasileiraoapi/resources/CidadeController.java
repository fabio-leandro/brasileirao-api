package com.fabio.brasileiraoapi.resources;

import com.fabio.brasileiraoapi.domains.Cidade;
import com.fabio.brasileiraoapi.repositories.CidadeRepository;
import com.fabio.brasileiraoapi.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/${api.version}/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @PostMapping
    public Mono<ResponseEntity<Cidade>> saveCidade(@RequestBody Cidade cidade){
        return estadoRepository.findBySigla(cidade.getEstado().getSigla())
                .flatMap(a ->{
                    cidade.setEstado(a);
                    return cidadeRepository.save(cidade);
                }).map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Flux<Cidade>> getAll(){
        return ResponseEntity.ok(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Cidade>> getById(@PathVariable String id){
        return cidadeRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/estados")
    public ResponseEntity<Flux<Cidade>> getCidadeByEstado(@RequestParam(value = "sigla") String sigla){
        return ResponseEntity.ok(cidadeRepository.findCidadeByEstadoSigla(sigla));
    }





}
