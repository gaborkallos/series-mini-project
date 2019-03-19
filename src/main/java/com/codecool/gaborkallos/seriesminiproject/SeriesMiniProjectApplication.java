package com.codecool.gaborkallos.seriesminiproject;

import com.codecool.gaborkallos.seriesminiproject.entity.Episode;
import com.codecool.gaborkallos.seriesminiproject.entity.Season;
import com.codecool.gaborkallos.seriesminiproject.entity.Series;
import com.codecool.gaborkallos.seriesminiproject.repository.EpisodeRepository;
import com.codecool.gaborkallos.seriesminiproject.repository.SeasonRepository;
import com.codecool.gaborkallos.seriesminiproject.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.sql.Time;
import java.time.LocalDate;

@SpringBootApplication
public class SeriesMiniProjectApplication {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    private Series series;
    private Season season;
    private Episode episode;


    public static void main(String[] args) {
        SpringApplication.run(SeriesMiniProjectApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {

        return args -> {
            series = getSeries("Game of Thrones");
            seriesRepository.save(series);
            season = getSeason(series, 1);
            seasonRepository.save(season);
            episode = getEpisode(season, "Winter is coming", "1:02:00", 2011, 04, 17);
            episodeRepository.save(episode);
            episode = getEpisode(season, "The Kingsroad", "0:56:00", 2011, 04, 25);
            episodeRepository.save(episode);
            episode = getEpisode(season, "Lord Snow", "0:58:00", 2011, 05, 02);
            episodeRepository.save(episode);

            series = getSeries("Criminal Minds");
            seriesRepository.save(series);
            season = getSeason(series, 1);
            seasonRepository.save(season);
            season = getSeason(series, 2);
            seasonRepository.save(season);
            season = getSeason(series, 3);
            seasonRepository.save(season);


        };

    }

    private Episode getEpisode(Season season, String title, String length, int year, int month, int day) {
        return Episode.builder()
                .title(title)
                .length(Time.valueOf(length))
                .releaseDate(LocalDate.of(year, month, day))
                .season(season)
                .build();
    }

    private Season getSeason(Series series, Integer seasonNumber) {
        return Season.builder()
                .seasonNumber(seasonNumber)
                .series(series)
                .build();
    }

    private Series getSeries(String title) {
        return Series.builder()
                .title(title)
                .build();

    }

}
