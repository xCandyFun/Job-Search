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

    private JPanel cards;
    private JPanel mainPanel = new JPanel();
    private JPanel secondaryPanel = new JPanel();
    //private static final JPanel panel = new JPanel();
    //private static final JPanel panel2 = new JPanel();
    //private static final JPanel panel3 = new JPanel();
    //private static final JPanel panel4 = new JPanel();
    //private static final JPanel panel5 = new JPanel();
    private static final JButton ExitTheApplication = new JButton("1: Exit");
    private static final JButton button2 = new JButton("2: Add data to database");
    private static final JButton button3 = new JButton("3: Get data from database");
    private static final JButton button4 = new JButton("4: Remove data from id");
    private static final JButton button5 = new JButton("5: Drop the table");
    private static List<Object> work;

    public void buttonStyle() {

        ExitTheApplication.setFont(new Font("", Font.BOLD, 20));
        button2.setFont(new Font("", Font.BOLD, 20));
        button3.setFont(new Font("", Font.BOLD, 20));
        button4.setFont(new Font("", Font.BOLD, 20));
        button5.setFont(new Font("", Font.BOLD, 20));
    }


    public void mainWindow() {

        buttonStyle();

        mainPanel.setLayout(new GridLayout(5, 3));

        JLabel[] labels = new JLabel[10];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
        }

        mainPanel.add(labels[0], LEFT_ALIGNMENT);
        mainPanel.add(ExitTheApplication, CENTER_ALIGNMENT);
        mainPanel.add(labels[1], RIGHT_ALIGNMENT);

        mainPanel.add(labels[2], LEFT_ALIGNMENT);
        mainPanel.add(button2, CENTER_ALIGNMENT);
        mainPanel.add(labels[3], RIGHT_ALIGNMENT);

        mainPanel.add(labels[4], LEFT_ALIGNMENT);
        mainPanel.add(button3, CENTER_ALIGNMENT);
        mainPanel.add(labels[5], RIGHT_ALIGNMENT);

        mainPanel.add(labels[6], LEFT_ALIGNMENT);
        mainPanel.add(button4, CENTER_ALIGNMENT);
        mainPanel.add(labels[7], RIGHT_ALIGNMENT);

        mainPanel.add(labels[8], LEFT_ALIGNMENT);
        mainPanel.add(button5, CENTER_ALIGNMENT);
        mainPanel.add(labels[9], RIGHT_ALIGNMENT);

        //add(panel);

        actionListenerForButtons();
        keyListener();

        cards = new JPanel(new CardLayout());
        cards.add(mainPanel, "Main");
        cards.add(secondaryPanel, "Secondary");

        add(cards);


        setSize(1600, 700);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setFocusable(true);
        requestFocusInWindow();


        setVisible(true);
    }

    public void secondaryWindow() {

        secondaryPanel.setLayout(new GridLayout(10,0));

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        secondaryPanel.add(panel3);
        secondaryPanel.add(panel4);
        secondaryPanel.add(panel5);


        // Create a JTextField
        JTextField textField = new JTextField(10);
        JTextField textField2 = new JTextField(10);

        // Create a button
        JButton SaveButton = new JButton("Saved");
        JButton BackButton = new JButton("Back");

        // Create a label to display the result
        JLabel label = new JLabel("What is the company name?: ");

        JLabel label3 = new JLabel("What is the topic?: ");

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

                showMainWindow();
            }
        });

        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainWindow();
            }
        });

        panel3.add(label);
        panel3.add(textField);


        panel4.add(label3);
        panel4.add(textField2);


        panel5.add(SaveButton);
        panel5.add(BackButton);

    }

    public static List<Object> GetWorkList() {
        return new ArrayList<>(work);
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
                showSecondaryWindow();
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

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    showSecondaryWindow();
                }

            }
        });

    }

    private void showMainWindow(){
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Main");
    }

    private void showSecondaryWindow(){
        secondaryPanel.removeAll();
        secondaryWindow();
        secondaryPanel.revalidate();
        secondaryPanel.repaint();
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Secondary");
    }

}
