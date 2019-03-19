package com.codecool.gaborkallos.seriesminiproject.repository;

import com.codecool.gaborkallos.seriesminiproject.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeriesRepository extends JpaRepository<Series, Long> {

}
