package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Cidade;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CidadeRepository extends ReactiveMongoRepository<Cidade,String> {
}
