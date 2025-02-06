package com.example.Entities;

public class MovieAvaliation {
    
    private String title;
    private int year;
    private double userNote;

    public MovieAvaliation(){

    }

    public MovieAvaliation(String title, int year, double userNote) {
        this.title = title;
        this.year = year;
        this.userNote = userNote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getUserNote() {
        return userNote;
    }

    public void setUserNote(double userNote) {
        this.userNote = userNote;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MovieAvaliation other = (MovieAvaliation) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString(){
        return String.format("\nTítulo\n: %s" + "Ano\n: %s" + "Nota do usuário: %.2f", title,year,userNote);
    }
}
