package com.codecool.gaborkallos.seriesminiproject.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Episode {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Time length;
    private LocalDate releaseDate;

    @ManyToOne
    private Season season;

}
