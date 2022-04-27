package com.fabio.brasileiraoapi.resources;

import com.fabio.brasileiraoapi.domains.Clube;
import com.fabio.brasileiraoapi.dtos.ClubeDto;
import com.fabio.brasileiraoapi.dtos.ClubeDtoUpdate;
import com.fabio.brasileiraoapi.repositories.CidadeRepository;
import com.fabio.brasileiraoapi.repositories.ClubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @GetMapping("/confronto")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Clube> findAllById(@RequestParam(value = "idMandante") String idMandante,
                                    @RequestParam(value = "idVisitante") String idVisitante){
        return clubeRepository.findAllById(List.of(idMandante,idVisitante));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Clube>> getById(@PathVariable String id){
        return clubeRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Flux<Clube>> getAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(clubeRepository.findAll());
    }


    @GetMapping("/resumo")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Object> getAllDto(){
        return clubeRepository.findAll()
                .flatMap(c ->{
                    ClubeDto clubeDto = ClubeDto.builder()
                            .id(c.getId())
                            .nome(c.getNome())
                            .apelido(c.getApelido())
                            .cidade(c.getCidade().getNome())
                            .estado(c.getCidade().getEstado().getSigla())
                            .divisao(c.getDivisao())
                            .build();
                    return Flux.just(clubeDto);
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClubeDto>> update(@PathVariable String id, @RequestBody ClubeDtoUpdate clubeDtoUpdate){
        return clubeRepository.findById(id)
                .flatMap(c -> {
                    c.setNome(clubeDtoUpdate.getNome());
                    c.setApelido(clubeDtoUpdate.getApelido());
                    c.setDivisao(clubeDtoUpdate.getDivisao());
                    return clubeRepository.save(c);
                }).flatMap(c -> {
                    ClubeDto clubeDto = ClubeDto.builder()
                            .id(c.getId())
                            .nome(c.getNome())
                            .apelido(c.getApelido())
                            .cidade(c.getCidade().getNome())
                            .estado(c.getCidade().getEstado().getSigla())
                            .divisao(c.getDivisao())
                            .build();
                    return Mono.just(clubeDto);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id){
        return clubeRepository.findById(id)
                .flatMap( c -> clubeRepository.deleteById(c.getId())
                        .then(Mono.just(ResponseEntity.noContent().build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
