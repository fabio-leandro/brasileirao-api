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

    @GetMapping("/nomeCidade")
    public Mono<ResponseEntity<Cidade>> getByNome(@RequestParam(value = "nomeCidade") String nomeCidade){
        return cidadeRepository.findByNome(nomeCidade)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Cidade>> update(@PathVariable String id, @RequestBody Cidade cidade){
        return cidadeRepository.findById(id)
                .flatMap(c ->{
                    c.setNome(cidade.getNome());
                    return cidadeRepository.save(c);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id){
        return cidadeRepository.findById(id)
                .flatMap( c -> cidadeRepository.deleteById(c.getId())
                        .then(Mono.just(ResponseEntity.noContent().build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
