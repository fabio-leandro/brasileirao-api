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
public class ClubeDtoUpdate {

    private String id;
    private String nome;
    private String apelido;
    private Divisao divisao;
}
