package org.example;

import org.example.ctd.Mysqlconnection;

import javax.swing.*;

public class Main {

    static Mysqlconnection mysqlCon = new Mysqlconnection();

    public static void main(String[] args) {
        mysqlCon.connectTodatabase();
        runWindow();
    }

    public static void runWindow(){
        SwingUtilities.invokeLater(Window::new);
    }

}