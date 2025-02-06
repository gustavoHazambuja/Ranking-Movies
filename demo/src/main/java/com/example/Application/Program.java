package com.example.Application;

import java.util.Scanner;

import com.example.Entities.ListMovies;
import com.example.Exceptions.NoteException;
import com.example.Services.MovieService;

public class Program {
    public static void main(String[] args) {
        Scanner dados = new Scanner(System.in);
        ListMovies listMovies = new ListMovies();

        int option;

        do {
            System.out.println("Menu de opções");
            System.out.println();
            System.out.println("(1) Buscar filme na API");
            System.out.println("(2) Avaliar um filme");
            System.out.println("(3) Atualizar nota");
            System.out.println("(4) Exibir ranking pessoal");
            System.out.println("(5) Deletar filme do ranking");
            System.out.println("(6) Sair");

            option = dados.nextInt();
            dados.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Informe o nome do filme: ");
                    String movieTitle = dados.nextLine();
                    MovieService.searchMovie(movieTitle);
                    break;

                case 2:
                    System.out.print("Informe o nome do filme para avaliar: ");
                    String title = dados.nextLine();
                    System.out.print("Digite a nota (0 a 10): ");
                    double note = dados.nextDouble();
                    dados.nextLine();

                try{
                    listMovies.avaliateMovie(title, note);
                }catch(NoteException e){
                    System.out.println("Erro: " + e.getMessage());
                }
                break;

                case 3:
                    System.out.print("Informe o nome do filme para atualizar a nota: ");
                    title = dados.nextLine();
                    System.out.print("Digite a nota (0 a 10): ");
                    note = dados.nextDouble();
                    dados.nextLine();

                    try{
                        listMovies.updateReview(title, note);
                    }catch(NoteException e){
                        System.out.println("Erro: " + e.getMessage());
                    }
                    
                    break;

                case 4:
                    listMovies.displayMovies();
                    break;

                case 5:
                    System.out.println("Informe o nome do filme para deletar: ");
                    title = dados.nextLine();

                    listMovies.deleteMovie(title);
                    break;    

                case 6:
                    System.out.println("Saindo...");
                    break; 

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        } while (option != 6);
    

    }
}
