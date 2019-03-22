package com.codecool.gaborkallos.seriesminiproject.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Series {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Singular
    @OneToMany
    @EqualsAndHashCode.Exclude
    private Set<Season> seasons;

    public Series(String title) {
        this.title = title;
    }
}
