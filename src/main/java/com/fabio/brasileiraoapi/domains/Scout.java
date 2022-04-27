package com.fabio.brasileiraoapi.domains;


import com.fabio.brasileiraoapi.domains.enums.Divisao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tabelas")
public class Scout {

    private String id;
    private Partida jogo;
    private Integer temporada = LocalDate.now().getYear();
    private Divisao divisao;
    private Clube clube;
    private Integer pontos = 0;
    private Integer partida = 0;
    private Integer vitoria = 0;
    private Integer derrota = 0;
    private Integer golsMarcados = 0;
    private Integer golsContra = 0;
    private Integer saldoGols = 0;
}
