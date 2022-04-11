package com.fabio.brasileiraoapi.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cidades")
public class Cidade {

    @Id
    private String id;
    private String nome;
    private Estado estado;

}
