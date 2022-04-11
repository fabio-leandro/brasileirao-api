package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Tabela;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TabelaRepository extends ReactiveMongoRepository<Tabela, String> {
}
