package com.fabio.brasileiraoapi.repositories;

import com.fabio.brasileiraoapi.domains.Scout;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ScoutRepository extends ReactiveMongoRepository<Scout, String> {
}
