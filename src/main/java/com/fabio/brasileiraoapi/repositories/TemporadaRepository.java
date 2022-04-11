package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Temporada;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TemporadaRepository extends ReactiveMongoRepository<Temporada,String> {
}
