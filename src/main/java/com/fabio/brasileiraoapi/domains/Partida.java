package com.fabio.brasileiraoapi.domains;

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
@Document(collection = "partidas")
public class Partida {

    @Id
    private String id;
    private LocalDate data;
    private Clube mandante;
    private Clube visitante;
    private Integer golsMandante = 0;
    private Integer golsVisitante = 0;


}
