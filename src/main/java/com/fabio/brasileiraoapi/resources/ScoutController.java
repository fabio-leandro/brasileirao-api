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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/${api.version}/scouts")
public class ScoutController {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ScoutRepository scoutRepository;

    @PostMapping("/serieA")
    public ResponseEntity<Flux<Scout>> save(@RequestBody Partida partida){
        Scout scoutMandante = Scout.builder()
                .temporada(LocalDate.now().getYear())
                .divisao(Divisao.PRIMEIRA)
                .clube(partida.getMandante())
                .contJogo(1)
                .vitoria(partida.getGolsMandante() > partida.getGolsVisitante() ? 1 : 0)
                .derrota(partida.getGolsMandante() == partida.getGolsVisitante() ? 1 : 0)
                .empate(partida.getGolsMandante() < partida.getGolsVisitante() ? 1 : 0)
                .golsMarcados(partida.getGolsMandante())
                .golsContra(partida.getGolsVisitante())
                .saldoGols(partida.getGolsMandante() - partida.getGolsVisitante())
                .build();
        if(scoutMandante.getVitoria() == 1) scoutMandante.setPontos(3);
        if(scoutMandante.getDerrota() == 1) scoutMandante.setPontos(0);
        if (scoutMandante.getEmpate() == 1) scoutMandante.setPontos(1);

        Scout scoutVisitante = Scout.builder()
                .temporada(LocalDate.now().getYear())
                .divisao(Divisao.PRIMEIRA)
                .clube(partida.getVisitante())
                .contJogo(1)
                .vitoria(partida.getGolsVisitante() > partida.getGolsMandante() ? 1 : 0)
                .derrota(partida.getGolsVisitante() < partida.getGolsMandante() ? 1 : 0)
                .empate(partida.getGolsVisitante() == partida.getGolsMandante() ? 1 : 0)
                .golsMarcados(partida.getGolsVisitante())
                .golsContra(partida.getGolsMandante())
                .saldoGols(partida.getGolsVisitante() - partida.getGolsMandante())
                .build();
        if(scoutVisitante.getVitoria() == 1) scoutVisitante.setPontos(3);
        if(scoutVisitante.getDerrota() == 1) scoutVisitante.setPontos(0);
        if (scoutVisitante.getEmpate() == 1) scoutVisitante.setPontos(1);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(scoutRepository.saveAll(List.of(scoutMandante,scoutVisitante)));
    }

    @GetMapping("/serieA")
    public ResponseEntity<Flux<Scout>> getAll(){
        return ResponseEntity.ok(scoutRepository.findAll());
    }



}
