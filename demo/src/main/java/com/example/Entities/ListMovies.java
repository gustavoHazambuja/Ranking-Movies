package com.example.Entities;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.Exceptions.NoteException;

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
    
            movies.put(title, note);
            System.out.println("Filme '" + title + "' avaliado com nota " + note);
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
    
        public void saveReviews(){
            JSONObject json = new JSONObject();
            for(Map.Entry<String,Double> entry : movies.entrySet()){
                json.put(entry.getKey(), entry.getValue());
            }
    
            try(FileWriter file = new FileWriter("avaliacoes.json")){
                file.write(json.toJSONString());
                System.out.println("Avaliações salvas com sucesso!");
            }catch(IOException e){
                System.out.println("Erro ao salvar avaliações: " + e.getMessage());
            }
        }
    
        public void uploadReviews(){
            JSONParser parser = new JSONParser();
            try(FileReader reader = new FileReader("avaliacoes.json")){
                JSONObject json = (JSONObject) parser.parse(reader);
    
                for(Object key: json.keySet()){
                    String title = (String) key;
                    double note = ((Number) json.get(key)).intValue();
                    movies.put(title, note);
                }
                System.out.println("Avaliações carregadas com sucesso.");
            }catch(IOException | ParseException e){
            System.out.println("Nenhum arquivo encontrado ou erro ao carregar avaliações.");
        }
    }
}
