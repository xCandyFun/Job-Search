package org.example;

import org.example.ctd.Mysqlconnection;

import javax.swing.*;
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

    private static void run(){
        runWindow();
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
                    System.out.println("Id to Remove?");
                    Integer TheIdToDelete = sc.nextInt();
                    mysqlCon.DeleteDataFromId(TheIdToDelete);
                    menu();
                }
                case "5" -> {
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
        4. Remove data from id
        5. Drop the table
        """;
        System.out.println(menutext);
    }

    public static void runWindow(){
        SwingUtilities.invokeLater(Window::new);
    }

}