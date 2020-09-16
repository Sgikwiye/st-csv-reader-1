package com.st.csv.reader.util;

import com.st.csv.reader.model.Movie;
import com.st.csv.reader.model.Ratings;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class CsvBuilder {

    private List<Movie> movieList;
    private HashMap<String,Integer> category_counts;
    private Set<String> movieCategory;

    private String path = "src/test/resources/movies.csv";
    private String pathR = "src/test/resources/ratings.csv";
    private String endPathEX1 = "src/test/resources/EX1.csv";
    private String endPathEX2 = "src/test/resources/EX2.csv";
    public void csvBuilder()  {
        CsvReader reader = new CsvReader();
        movieList = reader.getMovie(path, ",", "\\|");
        movieCategory = reader.buildMovieCategory(path, ",",  "\\|");
        category_counts = new HashMap<>();
        for (String cat:movieCategory
        ) {
            category_counts.put(cat,0);
        }
        movieList.forEach(x->{
            x.getGenres().forEach(y->{
                if (category_counts.containsKey(y))
                {
                    category_counts.put(y, category_counts.get(y) + 1);
                }
                else
                    {

                }
            });
        });

        try(FileWriter writer = new FileWriter(endPathEX1,false))
        {
            writer.write("Genres");
            writer.write(",");
            writer.write("No. of Movies");
            writer.write("\r\n");

            category_counts.entrySet().forEach(x -> {
                try {
                    writer.write(x.getKey().toString()+","+ x.getValue().toString()+"\r\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        }catch (IOException io)
        {
            io.printStackTrace();
        }
        HashMap<Integer,List<Float>> mapMovieScore = new HashMap<Integer,List<Float>>();
        List<Ratings> ratingsList = reader.getRatings(pathR,",");
        ratingsList.forEach(x-> {
            if(!mapMovieScore.containsKey(x.getMovieId()))
            {
                List<Float> score = new ArrayList<>();
                score.add(x.getRating());
                mapMovieScore.put(x.getMovieId(),score);
            }else {
                mapMovieScore.get(x.getMovieId()).add(x.getRating());
            }
        });
        HashMap<Integer,Float> mapMovieMeanScore= new HashMap<>();
        mapMovieScore.entrySet().forEach(x->{
            mapMovieMeanScore.put(x.getKey(),averageOfList(x.getValue()));
        });

        LinkedHashMap mapMovieMeanScore2 = mapMovieMeanScore.entrySet().stream().sorted(Map.Entry.<Integer,Float>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<Integer> movId = new ArrayList<>();
        List<Integer> first100= new ArrayList<>();
        HashMap<String,Float> titleScore = new HashMap<>();

       mapMovieMeanScore2.keySet().forEach(x-> movId.add((Integer)x));
       first100 = movId.stream().limit(99).collect(Collectors.toList());



        for (Movie mov:movieList){
            if (first100.contains(mov.getMovieId()))
            {
                titleScore.put(mov.getTitle(),mapMovieMeanScore.get(mov.getMovieId()));

            }else{}
        }
        try(FileWriter writer = new FileWriter(endPathEX2 ,false) )
        {
            writer.write("Title");
            writer.write(",");
            writer.write("Mean Score");
            writer.write("\r\n");

            titleScore.entrySet().forEach(x ->
            {
                try
                {
                writer.write(x.getKey().toString()+","+x.getValue().toString()+"\r\n");

                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            });

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public float averageOfList(List<Float> inputList){
        float sum = 0;
        int listSize = inputList.size();
        for (Float num:inputList
             ) {
            sum+=num;

        }
        return sum/listSize;
    }
}
