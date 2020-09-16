package com.st.csv.reader.util;

import com.st.csv.reader.model.Movie;
import com.st.csv.reader.model.Ratings;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CsvReaderTest {

    @Test
    public void getMovies() {

        CsvReader reader = new CsvReader();
        List<Movie> movies = reader.getMovie("src/test/resources/movies.csv", ",", "\\|");
        assertThat(movies).isNotEmpty();
        assertThat(movies).hasSize(9742);
        assertThat(movies.get(0).getGenres()).containsExactlyInAnyOrder("Adventure", "Animation", "Children", "Comedy", "Fantasy");
        assertThat(reader.buildMovieCategory("src/test/resources/movies.csv", ",", "\\|")).hasSize(20);

    }
    @Test
    public void getRatings() {

        CsvReader reader = new CsvReader();
        List<Ratings> rating = reader.getRatings("src/test/resources/ratings.csv", ",");
        assertThat(rating).isNotEmpty();
        assertThat(rating).hasSize(100836);
        // Checking if all the ratings are the same data
        for (Ratings ratings : rating) {
            assertThat(ratings.getRating()).isInstanceOf(Float.class);
            assertThat(ratings.getMovieId()).isInstanceOf(Integer.class);
            assertThat(ratings.getTimestamp()).isInstanceOf(Long.class);
            assertThat(ratings.getUserId()).isInstanceOf(Integer.class);

        }
    }
}