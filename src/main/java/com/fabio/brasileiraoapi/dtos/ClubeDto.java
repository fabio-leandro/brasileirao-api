package com.fabio.brasileiraoapi.dtos;

import com.fabio.brasileiraoapi.domains.enums.Divisao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubeDto {

    private String id;
    private String nome;
    private String cidade;
    private String estado;
    private Divisao divisao;
}
