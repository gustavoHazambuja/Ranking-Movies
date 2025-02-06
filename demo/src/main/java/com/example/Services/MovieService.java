package com.example.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MovieService {
    
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ODI0MjU2MWEyMGIzNzA3NzYyNWVmYzMzYWNhNTc2MiIsIm5iZiI6MTczODYzMzA2Ny4wOTUsInN1YiI6IjY3YTE2ZjZiYTQ1Mjg3YjdmZGUyYzA4NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.37kNwFQQ3TinCTeQk9Ap5WMaaNQjvmbo2vbO3lO-340";
    private static final String BASE_URL = "https://api.themoviedb.org/3/search/movie?query=";
    private static final String LANGUAGE = "&language=pt-BR";

    public static void searchMovie(String movieTitle) {
        try {
            movieTitle = movieTitle.replace(" ", "%20");
            URL url = new URL(BASE_URL + movieTitle + LANGUAGE);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());
            JSONArray results = (JSONArray) json.get("results");

            if (results.isEmpty()) {
                System.out.println("Nenhum filme encontrado.");
                return;
            }

            for (Object obj : results) {
                JSONObject movie = (JSONObject) obj;
                System.out.println();
                System.out.println("**********************");
                System.out.println("Título: " + movie.get("title"));
                System.out.println("Descrição: " + movie.get("overview"));
                System.out.println("Data de lançamento: " + movie.get("release_date"));
                System.out.println("Popularidade: " + movie.get("popularity"));
                System.out.println("Nota do TMDB: " + movie.get("vote_average"));
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar filme: " + e.getMessage());
        }
    }
}
