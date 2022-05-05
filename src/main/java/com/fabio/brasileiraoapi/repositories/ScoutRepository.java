package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Scout;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ScoutRepository extends ReactiveMongoRepository<Scout, String> {

    Flux<Scout> findAllByDivisao(String divisao);
    Flux<Scout> findAllByJogo(String jogo);
    Flux<Void>  deleteAllByJogo(String jogo);
}
