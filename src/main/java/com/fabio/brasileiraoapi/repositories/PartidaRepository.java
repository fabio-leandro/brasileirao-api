package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Partida;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PartidaRepository extends ReactiveMongoRepository<Partida,String> {
}
