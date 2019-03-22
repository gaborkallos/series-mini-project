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

    @Column(nullable = false, unique = true)
    private String title;

    private Time length;
    private LocalDate releaseDate;

    @ManyToOne
    private Season season;

    public Episode(String title, Time length, LocalDate releaseDate, Season season) {
        this.title = title;
        this.length = length;
        this.releaseDate = releaseDate;
        this.season = season;
    }
}
