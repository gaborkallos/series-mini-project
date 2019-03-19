package com.codecool.gaborkallos.seriesminiproject.repository;

import com.codecool.gaborkallos.seriesminiproject.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
