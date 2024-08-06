package org.example;

import org.example.ctd.Mysqlconnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Window extends JFrame {

    static Mysqlconnection mysqlCon = new Mysqlconnection();

    public Window() {
        mainWindow();
    }

    private static JPanel panel = new JPanel();
    private static JPanel panel2 = new JPanel();
    private static JPanel panel3 = new JPanel();
    private static JPanel panel4 = new JPanel();
    private static JPanel panel5 = new JPanel();
    private static JPanel panel6 = new JPanel();
    private static final JButton ExitTheApplication = new JButton("1: Exit");
    private static final JButton button2 = new JButton("2: Add data to database");
    private static final JButton button3 = new JButton("3: Get data from database");
    private static final JButton button4 = new JButton("4: Remove data from id");
    private static final JButton button5 = new JButton("5: Drop the table");
    private static List<Object> work;

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

    public void secondaryWindow(){


        panel2.setLayout(new GridLayout(3, 0));
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel2.add(panel3);
        panel2.add(panel4);
        panel2.add(panel5);
        panel.add(panel2);


        // Create a JTextField
        JTextField textField = new JTextField(10);
        JTextField textField2 = new JTextField(10);

        // Create a button
        JButton button = new JButton("Saved");
        JButton button2 = new JButton("Saved");
        JButton SaveButton = new JButton("Saved");

        // Create a label to display the result
        JLabel label = new JLabel("What is the company name?: ");
        JLabel label2 = new JLabel("Here is the name: ");


        JLabel label3 = new JLabel("What is the topic?: ");
        JLabel label4 = new JLabel("Here is the name: ");

        JLabel label5 = new JLabel("Here is the name: ");

        // Add an ActionListener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the text from the JTextField
//                String company = textField.getText();
//
//                label2.setText("Here is the name: " + company);
//                work.add(company);

            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String topic = textField2.getText();
//
//                label4.setText("Here is the name: " + topic);
//                LocalDate today = LocalDate.now();
//                work.add(topic);
//                work.add(today);
//                System.out.println(work);

            }
        });


        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String company = textField.getText();
                String topic = textField2.getText();
                LocalDate today = LocalDate.now();

                work = new ArrayList<>();


                work.add(company);
                work.add(topic);
                work.add(today);

                mysqlCon.SaveVariablesToMySQL();

                clearScreen();
                mainWindow();
            }
        });

        panel3.add(label);
        panel3.add(textField);


        panel4.add(label3);
        panel4.add(textField2);


        panel5.add(SaveButton);



        // Make the frame visible
        setVisible(true);

    }

    public static List<Object> GetWorkList(){
        return new ArrayList<>(work);
    }


    public void clearScreen(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

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
                clearScreen();
                secondaryWindow();

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
