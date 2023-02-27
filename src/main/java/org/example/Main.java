package org.example;

import java.io.*;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.opencsv.CSVReader;

public class Main {

    public static void main(String[] args)  {
        String filename = "movies.csv";
        String filePath = pathReader(filename);

        MovieStats movieStats = fetchData(filePath);

        actionMethod(movieStats);

    }

    static String pathReader(String filename){
        String docPath = System.getProperty("user.home") + "/Desktop/MovieInfo/src/main/resources/data";
        return  docPath + "/" + filename;
    }

    static MovieStats fetchData(String filePath){
        List<MovieInfo> MovieInfoSet = new ArrayList<>();
        int count = 0;

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        List list = null;

        try {
            list = reader.readAll();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //Getting the Iterator object
        Iterator it = list.iterator();
        it.next();
        while(it.hasNext()) {
            String[] str = (String[]) it.next();
            MovieInfo mvs = new MovieInfo(str[0], str[1], str[2], str[3], str[4], str[5], str[6], str[7]);
            MovieInfoSet.add(mvs);
            count++;
        }

        return new MovieStats(count, MovieInfoSet);
    }

    static void actionMethod(MovieStats movieStats){
        String sc;

          a: while(true){

            System.out.println("####### WELCOME TO MOVIE INFO #######");
            System.out.println("Press 1 for All Movie Inforamation");
            System.out.println("Press 2 for Search By Name");
            System.out.println("Press 3 for Search By Genre");
            System.out.println("Press 4 for Search By Date");
            System.out.println("Press 5 to Sort");
            System.out.println("Press 6 to exit");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your choice : ");
            String choice = scanner.next();
            System.out.println();
            switch (choice){
                case "1":     movieStats.showMovieInfo();
                              break;

                case "2":     System.out.print("Enter to Search : ");
                              sc = scanner.next();
                              System.out.println(sc);
                              movieStats.searchByName(sc);
                              break;

                case "3":     System.out.print("Enter a genre (eg: Comedy, Animation, Drama) : ");

                              sc = scanner.next();
                              movieStats.searchByGenre(sc);
                              break;

                case "4":     System.out.print("Enter Year : ");
                              sc = scanner.next();
                              movieStats.searchByDate(sc);
                              break;

                case "5":   System.out.println("Press 1 to sort by Name");
                            System.out.println("Press 2 to sort by Genre");
                            System.out.println("Press 3 to sort by Lead Studiio");
                            System.out.println("Press 4 to sort by year");
                            System.out.println("Enter a choice : ");
                            String ch = scanner.next();
                            if(ch.equals("1")){ movieStats.sortByName();}
                            if(ch.equals("2")){ movieStats.sortByGenre();}
                            if(ch.equals("3")){ movieStats.sortByStudio();}
                            if(ch.equals("4")){ movieStats.sortByYear();}
                            break;



                case "6":     break a;

                default:
                    System.out.println("Invalid Input ");
            }

        }


    }

}



// Film,Genre,Lead Studio,Audience score %,Profitability,Rotten Tomatoes %,Worldwide Gross,Year

class MovieStats {
    private int count = 0;
    private List<MovieInfo> movieSets;
    MovieStats(int count, List<MovieInfo> movieSets){
        this.count = count;
        this.movieSets = movieSets;
    }

    void showMovieInfo(){
        Iterator<MovieInfo> itr = movieSets.iterator();
        while(itr.hasNext()){
            itr.next().movieInfo();
        }
    }

    // Searching by Date
    void searchByDate(String year){
        Iterator<MovieInfo> itr = movieSets.iterator();
        int number = 0;

        while(itr.hasNext()){
            MovieInfo movie = itr.next();
            if(movie.getYear() == Integer.parseInt(year)){
                movie.movieInfo();
                number++;
            }
        }

        System.out.println("Total Movie of " + year + " : " + number);
    }

    // Search By Genre
    void searchByGenre(String input){
        Iterator<MovieInfo> itr = movieSets.iterator();
        int number = 0;

        while(itr.hasNext()){
            MovieInfo movie = itr.next();
            if(movie.getGenre().toLowerCase().equals(input.toLowerCase())){
                movie.movieInfo();
                number++;
            }
        }

        System.out.println("Total Number of Movie of " + input + " type : " + number);
    }

    // Search By Name
    void searchByName(String name){
        Iterator<MovieInfo> itr = movieSets.iterator();
        int number = 0;
        while(itr.hasNext()){
            MovieInfo movie = itr.next();
            if(movie.getFilmName().toLowerCase().contains(name.toLowerCase())){
                movie.movieInfo();
                number++;
            }
        }

        System.out.println("Total Number of Movies : " + number);

    }

    // Sort By Date
    void sortByYear(){
        Collections.sort(movieSets, new CompareByYear());
        this.showMovieInfo();
    }

    void sortByName(){
        Collections.sort(movieSets, new CompareByName());
        showMovieInfo();
    };

    void sortByGenre(){
        Collections.sort(movieSets, new CompareByGenre());
        showMovieInfo();
    };

    void sortByStudio(){
        Collections.sort(movieSets, new CompareByStudio());
        showMovieInfo();
    };

}

class CompareByName implements Comparator<MovieInfo> {

    @Override
    public int compare(MovieInfo o1, MovieInfo o2) {
        int val = o1.getFilmName().toLowerCase().compareTo(o2.getFilmName().toLowerCase());
        if(val > 0) return 1;
        else if(val < 0) return -1;
        else return 0;
    }
}

class CompareByGenre implements Comparator<MovieInfo> {

    @Override
    public int compare(MovieInfo o1, MovieInfo o2) {
        int val = o1.getGenre().toLowerCase().compareTo(o2.getGenre().toLowerCase());
        if(val > 0) return 1;
        else if(val < 0) return -1;
        else {
            val = o1.getFilmName().toLowerCase().compareTo(o2.getFilmName().toLowerCase());
            if(val > 0) return 1;
            else if(val < 0) return -1;
            else return 0;
        }
    }

}

class CompareByStudio implements Comparator<MovieInfo> {

    @Override
    public int compare(MovieInfo o1, MovieInfo o2) {
        int val = o1.getStudio().toLowerCase().compareTo(o2.getStudio().toLowerCase());
        if(val > 0) return 1;
        else if(val < 0) return -1;
        else {
            val = o1.getFilmName().toLowerCase().compareTo(o2.getFilmName().toLowerCase());
            if(val > 0) return 1;
            else if(val < 0) return -1;
            else return 0;

        }
    }

}

class CompareByYear implements Comparator<MovieInfo> {

    @Override
    public int compare(MovieInfo o1, MovieInfo o2) {
        if(o1.getYear() > o2.getYear()){
            return 1;
        }
        else if(o1.getYear() < o2.getYear()){
            return -1;
        }
        else {
            int val = o1.getFilmName().toLowerCase().compareTo(o2.getFilmName().toLowerCase());
            if(val > 0) return 1;
            else if(val < 0) return -1;
            else return 0;

        }
    }

}

class MovieInfo {
    private String filmName;
    private String genre;
    private String studio;
    private int score;
    private double profit;
    private int rottenTomato;
    private Double gross;
    private int year;

    public MovieInfo(String filmName, String genre, String studio, String score, String profit, String rottenTomato, String gross, String year) {
        setFilmName(filmName);
        setGenre(genre);
        setStudio(studio);
        setScore(score);
        setProfit(profit);
        setRottenTomato(rottenTomato);
        setGross(gross);
        setYear(year);
    }

    public void movieInfo(){
        System.out.println("Film : " + getFilmName());
        System.out.println("Genre : " + getGenre());
        System.out.println("Lead Studio : " + getStudio());
        System.out.println("Audience Score % : " + getScore());
        System.out.println("Profitability : " + getProfit());
        System.out.println("Rotten Tomatoes % : " + getRottenTomato());
        System.out.println("Gross : $" + getGross());
        System.out.println("Year : " + getYear());
        System.out.println();
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = Integer.valueOf(score);
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = Double.valueOf(profit);
    }

    public int getRottenTomato() {
        return rottenTomato;
    }

    public void setRottenTomato(String rottenTomato) {
        this.rottenTomato = Integer.valueOf(rottenTomato);
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = Double.valueOf(gross.substring(1));
    }

    public int getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = Integer.valueOf(year);
    }
}




