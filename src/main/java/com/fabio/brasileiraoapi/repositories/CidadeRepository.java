package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Cidade;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CidadeRepository extends ReactiveMongoRepository<Cidade,String> {

    Flux<Cidade> findCidadeByEstadoSigla(String sigla);
}
