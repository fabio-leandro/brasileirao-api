package com.fabio.brasileiraoapi.domains;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tabelas")
public class Tabela {

    private String id;
    private Temporada temporada;
    private Clube clube;
    private Integer pontos;
    private Integer partida;
    private Integer vitoria;
    private Integer derrota;
    private Integer golsMarcados;
    private Integer golsContra;
    private Integer saldoGols;
}
