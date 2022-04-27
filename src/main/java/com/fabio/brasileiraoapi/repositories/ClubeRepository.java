package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Clube;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ClubeRepository extends ReactiveMongoRepository<Clube,String> {

    Flux<Clube> findAllById(List<String> ids);
}
