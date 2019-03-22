package com.codecool.gaborkallos.seriesminiproject;

import com.codecool.gaborkallos.seriesminiproject.entity.Episode;
import com.codecool.gaborkallos.seriesminiproject.entity.Season;
import com.codecool.gaborkallos.seriesminiproject.entity.Series;
import com.codecool.gaborkallos.seriesminiproject.repository.EpisodeRepository;
import com.codecool.gaborkallos.seriesminiproject.repository.SeasonRepository;
import com.codecool.gaborkallos.seriesminiproject.repository.SeriesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")

public class SeriesMiniProjectApplicationTest {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Test
    public void saveOneSimple() {
        Series testSeries = Series.builder()
                .title("TEST")
                .build();

        Season testSeason = Season.builder()
                .seasonNumber(1)
                .series(testSeries)
                .build();

        Episode testEpisode = Episode.builder()
                .title("title")
                .length(Time.valueOf("1:06:01"))
                .releaseDate(LocalDate.of(2019, 03, 21))
                .season(testSeason)
                .build();

        seriesRepository.save(testSeries);
        List<Series> series = seriesRepository.findAll();
        assertThat(series).hasSize(1);

        seasonRepository.save(testSeason);
        List<Season> seasons = seasonRepository.findAll();
        assertThat(seasons).hasSize(1);

        episodeRepository.save(testEpisode);
        List<Episode> episodes = episodeRepository.findAll();
        assertThat(episodes).hasSize(1);
    }

    @Test
    public void givenSeriesRepository_whenSaveAndReceiveEntity_thenOK() {
        Series genericSeries = seriesRepository.save(
                new Series("TEST"));
        Series foundSeries = seriesRepository.getOne(
                genericSeries.getId());

        assertNotNull(foundSeries);
        assertEquals(genericSeries, foundSeries);
    }

    @Test
    public void givenSeasonRepository_whenSaveAndReceiveEntity_thenOK() {
        Series genericSeries = seriesRepository.save(
                new Series("TEST"));
        Season genericSeason = seasonRepository.save(
                new Season(1, genericSeries));
        Season foundSeason = seasonRepository.getOne(
                genericSeason.getId());

        assertNotNull(foundSeason);
        assertEquals(genericSeason,foundSeason);
    }

    @Test
    public void givenEpisodeRepository_whenSaveAndReceiveEntity_thenOK() {
        Series genericSeries = seriesRepository.save(
                new Series("Test Series"));
        Season genericSeason = seasonRepository.save(
                new Season(1,
                        genericSeries));
        Episode genericEpisode = episodeRepository.save(
                new Episode("Test Episode",
                        Time.valueOf("12:12:12"),
                        LocalDate.of(2019,3,20),
                        genericSeason));
        Episode foundEpisode = episodeRepository.getOne(
                genericEpisode.getId());

        assertNotNull(foundEpisode);
        assertEquals(genericEpisode, foundEpisode);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveUniqueTitleFieldTwiceToSeries() {
        Series firstTestSeries = Series.builder()
                .title("UNIQUE TEST SERIES")
                .build();
        seriesRepository.save(firstTestSeries);

        Series secondTestSeries = Series.builder()
                .title("UNIQUE TEST SERIES")
                .build();
        seriesRepository.saveAndFlush(secondTestSeries);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveUniqueSeasonNumberFieldTwiceToSeason() {
        Series testSeries = Series.builder()
                .title("UNIQUE TEST SERIES-SEASON")
                .build();

        Season firstTestSeason = Season.builder()
                .seasonNumber(1)
                .series(testSeries)
                .build();

        Season secondTestSeason = Season.builder()
                .seasonNumber(1)
                .series(testSeries)
                .build();

        seriesRepository.save(testSeries);
        seasonRepository.save(firstTestSeason);
        seasonRepository.saveAndFlush(secondTestSeason);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveUniqueTitleFieldTwiceToEpisode() {
        Series testSeries = Series.builder()
                .title("UNIQUE TEST SERIES-EPISODE")
                .build();

        Season testSeason = Season.builder()
                .seasonNumber(1)
                .series(testSeries)
                .build();

        Episode firstEpisodeTest = Episode.builder()
                .title("UNIQUE EPISODE TEST IS COMMING")
                .build();

        Episode secondEpisodeTest = Episode.builder()
                .title("UNIQUE EPISODE TEST IS COMMING")
                .build();

        seriesRepository.save(testSeries);
        seasonRepository.save(testSeason);
        episodeRepository.save(firstEpisodeTest);
        episodeRepository.saveAndFlush(secondEpisodeTest);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void seriesTitleShouldBeNotNull() {
        Series series = Series.builder()
                .build();

        seriesRepository.save(series);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void seasonSeasonNumberShouldBeNotNull() {
        Series series = Series.builder()
                .title("SEASON NUMBER SHOULD BE NOT NULL")
                .build();

        Season season = Season.builder()
                .series(series)
                .build();

        seriesRepository.save(series);
        seasonRepository.save(season);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void episodeTitleShouldBeNotNull() {
        Series series = Series.builder()
                .title("EPISODE TITLE SHOULD BE NOT NULL")
                .build();

        Season season = Season.builder()
                .series(series)
                .build();

        Episode episode = Episode.builder()
                .season(season)
                .build();

        seriesRepository.save(series);
        seasonRepository.save(season);
        episodeRepository.save(episode);
    }
}
