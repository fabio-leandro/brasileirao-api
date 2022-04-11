package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Clube;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClubeRepository extends ReactiveMongoRepository<Clube,String> {
}
