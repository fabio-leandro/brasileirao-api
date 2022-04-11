package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Estado;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EstadoRepository extends ReactiveMongoRepository<Estado,String> {

    Mono<Estado> findBySigla(String sigla);
}
