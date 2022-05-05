package com.fabio.brasileiraoapi.domains;


import com.fabio.brasileiraoapi.domains.enums.Divisao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tabelas")
public class Scout {

    @Id
    private String id;
    private Integer temporada;
    private Divisao divisao;
    private String clube;
    private Integer contJogo;
    private Integer vitoria;
    private Integer derrota;
    private Integer empate;
    private Integer pontos = 0;
    private Integer golsMarcados;
    private Integer golsContra;
    private Integer saldoGols;
    private String jogo; //concatenação do nome do mandante, mais nome do visitante e data do jogo
}
