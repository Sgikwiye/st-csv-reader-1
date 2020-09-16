package com.st.csv.reader.util;




import com.st.csv.reader.model.Links;
import com.st.csv.reader.model.Movie;
import com.st.csv.reader.model.Ratings;
import com.st.csv.reader.model.Tags;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReader {

    public List<Movie> getMovie(String path, String mainDelimitter, String genreDelimitter){

        Path currentPath = Paths.get(path);
        try(Stream<String> lines = Files.lines(currentPath))
        {
            return lines.filter(f->!f.startsWith("rating"))
                    .flatMap(m -> extractMovies(mainDelimitter,genreDelimitter,m))
                    .collect(Collectors.toList());

        }catch(IOException e){
            throw new RuntimeException("Invalid File",e);
        }

    }


    public Stream<Movie> extractMovies(String mainDelimitter, String genresDelimitter, String m)
    {
        try
        {
            String[] line = m.split(mainDelimitter);
            Movie rating = new Movie();
            rating.setMovieId(Integer.parseInt(line[0]));
            rating.setTitle(line[1]);
            rating.setGenres(Arrays.asList(line[line.length-1].split(genresDelimitter)));
            return Stream.of(rating);
        }
        catch (Exception ex)
        {
            return Stream.empty();
        }
    }

    public Set<String> buildMovieCategory(String path,String mainDelimitter,String genreDelimitter)
    {
        try
        {
            List<Movie> myMovies = getMovie(path,mainDelimitter,genreDelimitter);//("src/test/resources/ratings.csv",",","\\|");
            Set<String> ratingCategory = new HashSet<String>() ;
            myMovies.forEach(v-> ratingCategory.addAll(v.getGenres()));
            return ratingCategory;
        }catch (Exception ex)
        {
            return Collections.<String>emptySet();
        }
    }
    public List<Ratings> getRatings(String path, String mainDelimitter){

        Path currentPath = Paths.get(path);
        try(Stream<String> lines = Files.lines(currentPath))
        {
            return lines
                    .flatMap(m -> extractRatings(mainDelimitter,m))
                    .collect(Collectors.toList());

        }catch(IOException e){
            throw new RuntimeException("Invalid File",e);
        }

    }


    public Stream<Ratings> extractRatings(String mainDelimitter, String m)
    {
        try
        {
            String[] line = m.split(mainDelimitter);
            Ratings rating = new Ratings();
            rating.setUserId(Integer.parseInt(line[0]));
            rating.setMovieId(Integer.parseInt(line[1]));
            rating.setRating(Float.parseFloat(line[2]));
            return Stream.of(rating);
        }
        catch (Exception ex)
        {
            return Stream.empty();
        }
    }

    public List<Links> getLinks(String path, String mainDelimitter){

        Path currentPath = Paths.get(path);
        try(Stream<String> lines = Files.lines(currentPath))
        {
            return lines
                    .flatMap(m -> extractLinks(mainDelimitter,m))
                    .collect(Collectors.toList());

        }catch(IOException e){
            throw new RuntimeException("Invalid File",e);
        }

    }


    public Stream<Links> extractLinks(String mainDelimitter, String m)
    {
        try
        {
            String[] line = m.split(mainDelimitter);
            Links links = new Links();
            links.setMovieId(Integer.parseInt(line[0]));
            links.setTmdbId(Integer.parseInt(line[2]));
            links.setImdbId(Integer.parseInt(line[1]));
            return Stream.of(links);
        }
        catch (Exception ex)
        {
            return Stream.empty();
        }
    }

    public List<Tags> getTags(String path, String mainDelimitter){

        Path currentPath = Paths.get(path);
        try(Stream<String> lines = Files.lines(currentPath))
        {
            return lines
                    .flatMap(m -> extractTags(mainDelimitter,m))
                    .collect(Collectors.toList());

        }catch(IOException e){
            throw new RuntimeException("Invalid File",e);
        }

    }


    public Stream<Tags> extractTags(String mainDelimitter, String m)
    {
        try
        {
            String[] line = m.split(mainDelimitter);
            Tags tags = new Tags();
            tags.setTimestamps(Long.parseLong(line[3]));
            tags.setTag(line[2]);
            tags.setMovieID(Integer.parseInt(line[1]));
            tags.setUserID(Integer.parseInt(line[0]));
            return Stream.of(tags);
        }
        catch (Exception ex)
        {
            return Stream.empty();
        }
    }



}
