package com.fabio.brasileiraoapi.resources;

import com.fabio.brasileiraoapi.domains.Partida;
import com.fabio.brasileiraoapi.domains.Scout;
import com.fabio.brasileiraoapi.domains.enums.Divisao;
import com.fabio.brasileiraoapi.repositories.PartidaRepository;
import com.fabio.brasileiraoapi.repositories.ScoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/${api.version}/scouts")
public class ScoutController {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ScoutRepository scoutRepository;

    @PostMapping
    public ResponseEntity<Flux<Scout>> save(@RequestParam(value ="divisao") String divisao, @RequestBody Partida partida){
        Scout scoutMandante = Scout.builder()
                .temporada(LocalDate.now().getYear())
                .divisao(Divisao.valueOf(divisao.toUpperCase()))
                .clube(partida.getMandante())
                .contJogo(1)
                .vitoria(partida.getGolsMandante() > partida.getGolsVisitante() ? 1 : 0)
                .derrota(partida.getGolsMandante() < partida.getGolsVisitante() ? 1 : 0)
                .empate(partida.getGolsMandante() == partida.getGolsVisitante() ? 1 : 0)
                .golsMarcados(partida.getGolsMandante())
                .golsContra(partida.getGolsVisitante())
                .saldoGols(partida.getGolsMandante() - partida.getGolsVisitante())
                .jogo(partida.getMandante()+partida.getVisitante()+partida.getData())
                .build();
        if(scoutMandante.getVitoria() == 1) scoutMandante.setPontos(3);
        if(scoutMandante.getDerrota() == 1) scoutMandante.setPontos(0);
        if (scoutMandante.getEmpate() == 1) scoutMandante.setPontos(1);

        Scout scoutVisitante = Scout.builder()
                .temporada(LocalDate.now().getYear())
                .divisao(Divisao.valueOf(divisao.toUpperCase()))
                .clube(partida.getVisitante())
                .contJogo(1)
                .vitoria(partida.getGolsVisitante() > partida.getGolsMandante() ? 1 : 0)
                .derrota(partida.getGolsVisitante() < partida.getGolsMandante() ? 1 : 0)
                .empate(partida.getGolsVisitante() == partida.getGolsMandante() ? 1 : 0)
                .golsMarcados(partida.getGolsVisitante())
                .golsContra(partida.getGolsMandante())
                .saldoGols(partida.getGolsVisitante() - partida.getGolsMandante())
                .jogo(partida.getMandante()+partida.getVisitante()+partida.getData())
                .build();
        if(scoutVisitante.getVitoria() == 1) scoutVisitante.setPontos(3);
        if(scoutVisitante.getDerrota() == 1) scoutVisitante.setPontos(0);
        if (scoutVisitante.getEmpate() == 1) scoutVisitante.setPontos(1);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(scoutRepository.saveAll(List.of(scoutMandante,scoutVisitante)));
    }

    @GetMapping
    public ResponseEntity<Flux<Scout>> getAll(@RequestParam(value = "divisao") String divisao){
        return ResponseEntity.ok(scoutRepository.findAllByDivisao(divisao));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Scout>> getById(@PathVariable String id){
        return scoutRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Scout>> update(@PathVariable String id, @RequestBody Scout scout){
        return scoutRepository.findById(id)
                .flatMap(s ->{
                    s.setTemporada(scout.getTemporada());
                    s.setDivisao(scout.getDivisao());
                    s.setClube(scout.getClube());
                    s.setContJogo(scout.getContJogo());
                    s.setVitoria(scout.getVitoria());
                    s.setDerrota(scout.getDerrota());
                    s.setEmpate(scout.getEmpate());
                    s.setGolsMarcados(scout.getGolsMarcados());
                    s.setGolsContra(scout.getGolsContra());
                    s.setSaldoGols(scout.getSaldoGols());
                    return scoutRepository.save(s);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<Object>> delete(@RequestParam(value = "mandante") String mandante,
                                               @RequestParam(value = "visitante") String visitante,
                                               @RequestParam(value = "data") String data){
        return scoutRepository.findAllByJogo(mandante+visitante+data)
                .flatMap(s -> scoutRepository.delete(s)).then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
