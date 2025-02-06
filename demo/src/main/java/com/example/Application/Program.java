package com.example.Application;

import com.example.Entities.ListMovies;
import com.example.Exceptions.NoteException;

public class Program {
    public static void main(String[] args) {
        ListMovies list = new ListMovies();

        try{
            list.avaliateMovie("Homem aranha 2", 9.5);
            list.avaliateMovie("Todo Poderoso", 10.1);
        }catch(NoteException e){
            System.out.println("Erro: " + e.getMessage());
        }

        

        list.displayMovies();

    }
}
