package com.fabio.brasileiraoapi.domains;

import com.fabio.brasileiraoapi.domains.enums.Divisao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "clubes")
public class Clube {

    @Id
    private String id;
    private String nome;
    private String apelido;
    private Cidade cidade;
    private Divisao divisao;

}
