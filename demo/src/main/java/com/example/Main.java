package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) throws Exception {
     Scanner dados = new Scanner(System.in);

        System.out.println("Informe o filme deseja buscar: ");
        String movieTitle = dados.nextLine();
        movieTitle = movieTitle.replace(" ", "%");

        try{
            URL url = new URL("https://api.themoviedb.org/3/search/movie?query=" + movieTitle + "&language=pt-BR");
            HttpURLConnection coon = (HttpURLConnection) url.openConnection();
            coon.setRequestMethod("GET");
            coon.setRequestProperty("accept", "application/json");
            coon.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ODI0MjU2MWEyMGIzNzA3NzYyNWVmYzMzYWNhNTc2MiIsIm5iZiI6MTczODYzMzA2Ny4wOTUsInN1YiI6IjY3YTE2ZjZiYTQ1Mjg3YjdmZGUyYzA4NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.37kNwFQQ3TinCTeQk9Ap5WMaaNQjvmbo2vbO3lO-340");

            BufferedReader bf = new BufferedReader(new InputStreamReader(coon.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while((line = bf.readLine())!= null){
                response.append(line);
            }

            bf.close();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());

            System.out.println(json);

            JSONArray resultados = (JSONArray) json.get("results");

            for(Object resultado: resultados){
                JSONObject filme = (JSONObject) resultado;
                System.out.println();
                System.out.println("**********************");
                System.out.println("Título: " + filme.get("title"));
                System.out.println("Descriçaõ: " + filme.get("overview"));
                System.out.println("Data de lançamento: " + filme.get("release_date"));
                System.out.println("Popularidade: " + filme.get("popularity"));
                System.out.println("Nota do TMDB: " + filme.get("vote_average"));
            }


        }catch (Exception e){
            System.out.println("Erro: " + e);
        }
    }

}
