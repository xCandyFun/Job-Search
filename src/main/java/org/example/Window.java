package org.example;

import org.example.ctd.Mysqlconnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window extends JFrame {

    static Mysqlconnection mysqlCon = new Mysqlconnection();

    public Window() {
        mainWindow();
        //removeAll();
    }

    private static JPanel panel = new JPanel();
    private static final JButton ExitTheApplication = new JButton("1: Exit");
    private static final JButton button2 = new JButton("2: Add data to database");
    private static final JButton button3 = new JButton("3: Get data from database");
    private static final JButton button4 = new JButton("4: Remove data from id");
    private static final JButton button5 = new JButton("5: Drop the table");

    public void buttonStyle(){

        ExitTheApplication.setFont(new Font("",Font.BOLD, 20));
        button2.setFont(new Font("",Font.BOLD, 20));
        button3.setFont(new Font("",Font.BOLD, 20));
        button4.setFont(new Font("",Font.BOLD, 20));
        button5.setFont(new Font("",Font.BOLD, 20));
    }


    public void mainWindow() {

        buttonStyle();

        panel.setLayout(new GridLayout(5, 3));

        // I know It looks bad, but it's looking as I want :D
        //TODO for loop for labels
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JLabel jLabel8 = new JLabel();
        JLabel jLabel9 = new JLabel();
        JLabel jLabel10 = new JLabel();

        panel.add(jLabel1, LEFT_ALIGNMENT);
        panel.add(ExitTheApplication, CENTER_ALIGNMENT);
        panel.add(jLabel2, RIGHT_ALIGNMENT);

        panel.add(jLabel3, LEFT_ALIGNMENT);
        panel.add(button2, CENTER_ALIGNMENT);
        panel.add(jLabel4, RIGHT_ALIGNMENT);

        panel.add(jLabel5, LEFT_ALIGNMENT);
        panel.add(button3, CENTER_ALIGNMENT);
        panel.add(jLabel6, RIGHT_ALIGNMENT);

        panel.add(jLabel7, LEFT_ALIGNMENT);
        panel.add(button4, CENTER_ALIGNMENT);
        panel.add(jLabel8, RIGHT_ALIGNMENT);

        panel.add(jLabel9, LEFT_ALIGNMENT);
        panel.add(button5, CENTER_ALIGNMENT);
        panel.add(jLabel10, RIGHT_ALIGNMENT);

        add(panel);

        actionListenerForButtons();
        keyListener();


        setSize(1600, 700);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setFocusable(true);
        requestFocusInWindow();


        setVisible(true);
    }

    public void secondaryWindow(){}

    public void actionListenerForButtons() {

        ExitTheApplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                mysqlCon.SaveVariablesToMySQL();
            }
        });

    }

    private void keyListener() {

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    System.exit(0);
                }
            }
        });

    }

}
