package com.example.Entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.Exceptions.NoteException;
import com.example.Exceptions.TitleException;

public class ListMovies {
    
        private Map<String, Double> movies;
    
        public ListMovies(){
            this.movies = new HashMap<>();
            uploadReviews();
            
        }
    
       public Map<String, Double> getMovies() {
           return movies;
       }
    
        public void avaliateMovie(String title, Double note){
    
            if(note < 0.0 || note > 10.0){
                throw new NoteException("Insira uma nota entre 0 e 10");
            }

            if(title.isEmpty()){
                throw new TitleException("O título não pode ser vazio.");
            }

            if(isValidMovie(title)){
                movies.put(title, note);
                System.out.println("Filme '" + title + "' avaliado com nota " + note);
            }
            else{
                throw new TitleException("Filme não encontrado no TMDb. Verifique o título.");
            }
    
           
           
            saveReviews();
        }

        public void updateReview(String title, Double note){
            if(!movies.containsKey(title)){
                System.out.println("Filme não encontrado.");
                return;
            }

            if(note < 0.0 || note > 10.0){
                throw new NoteException("Insira uma nota entre 0 e 10");
            }

            movies.put(title, note);
            System.out.println("Nota do filme '" + title + "' atualizada para " + note);
            saveReviews();
        }

        public void deleteMovie(String title){
            if(!movies.containsKey(title)){
                System.out.println("Filme não encontrado.");
                return;
            }

            movies.remove(title);
            System.out.println("Filme " + title + " removido.");
            saveReviews();
        }
    
        public void displayMovies(){
            if(movies.isEmpty()){
                System.out.println("Nenhum filme avaliado.");
            }
            
            movies.entrySet()
                .stream()
                .sorted((movie1,movie2) -> movie2.getValue().compareTo(movie1.getValue()))
                .forEach(m ->
                System.out.println("Filme: " + m.getKey() + " | Nota: " + m.getValue()));
        }
    
        public void saveReviews() {
            JSONObject json = new JSONObject();
            for (Map.Entry<String, Double> entry : movies.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
        
            try (FileWriter file = new FileWriter("avaliacoes.json", false)) { 
                file.write(json.toJSONString());
                file.flush(); // Garante que os dados são gravados no arquivo antes de fechar
                System.out.println("Avaliações salvas com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao salvar avaliações: " + e.getMessage());
            }
        }
        
    
        public void uploadReviews(){
            JSONParser parser = new JSONParser();
            try(FileReader reader = new FileReader("avaliacoes.json")){
                JSONObject json = (JSONObject) parser.parse(reader);
    
                for(Object key: json.keySet()){
                    String title = (String) key;
                    double note = ((Number) json.get(key)).doubleValue();
                    movies.put(title, note);
                }
                System.out.println("Avaliações carregadas com sucesso.");
            }catch(IOException | ParseException e){
            System.out.println("Nenhum arquivo encontrado ou erro ao carregar avaliações.");
        }
    }

    private boolean isValidMovie(String title) {
        
    try {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        String apiUrl = "https://api.themoviedb.org/3/search/movie?query=" + encodedTitle + "&language=pt-BR";

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ODI0MjU2MWEyMGIzNzA3NzYyNWVmYzMzYWNhNTc2MiIsIm5iZiI6MTczODYzMzA2Ny4wOTUsInN1YiI6IjY3YTE2ZjZiYTQ1Mjg3YjdmZGUyYzA4NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.37kNwFQQ3TinCTeQk9Ap5WMaaNQjvmbo2vbO3lO-340");

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

        return !results.isEmpty(); // Retorna verdadeiro se houver ao menos um filme encontrado

    } catch (Exception e) {
        System.out.println("Erro ao verificar filme: " + e.getMessage());
        return false;
    }
}
}
