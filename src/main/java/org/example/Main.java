package org.example;

import org.example.ctd.Mysqlconnection;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static Mysqlconnection mysqlCon = new Mysqlconnection();

    static private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        mysqlCon.connectTodatabase();
        run();
    }

    public static List<Object> GetDataFromUser(){
        System.out.println("\nWhat is the company name? ");
        String company = sc.next();

        System.out.println("\nWhat is the topic");
        String topic = sc.next();


        LocalDate today = LocalDate.now();

        List<Object> work = new ArrayList<>();

        work.add(company);
        work.add(topic);
        work.add(today);

        return work;
    }

    private static void run(){
        menu();
        while (true){
            String choice = sc.next();
            switch (choice){
                case "1" -> System.exit(1);
                case "2" -> {
                    mysqlCon.SaveVariablesToMySQL();
                    menu();
                }
                case "3" -> {
                    mysqlCon.GetDataFromDatabase();
                    menu();
                }
                case "4" -> {
                    CheckForOkToDropTable();
                    menu();
                }
            }
        }
    }

    private static void CheckForOkToDropTable(){
        System.out.println("are you sure?");
        String OkToDrop = sc.next();
        String yes = "yes";
        if (Objects.equals(OkToDrop, yes)){
            mysqlCon.DropTable();
            mysqlCon.connectTodatabase();
        }else {
            System.exit(1);
        }
    }

    private static void menu(){
        String menutext = """
        1. Exit
        2. Add data to database
        3. Get data from database
        4. Drop the table
        """;
        System.out.println(menutext);
    }
}