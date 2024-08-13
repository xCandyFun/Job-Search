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
    private JPanel thirdPanel = new JPanel();
    private JPanel fourthPanel = new JPanel();
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
        cards.add(thirdPanel, "Third");
        cards.add(fourthPanel, "fourth");

        add(cards);


        setSize(1600, 700);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setFocusable(true);
        requestFocusInWindow();


        setVisible(true);
    }

    public void secondaryWindow() {

        secondaryPanel.setLayout(new GridLayout(10, 0));

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
        JButton SaveButton = new JButton("Save");
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

                JOptionPane.showMessageDialog(secondaryPanel, "Saved");

                //click on saved button return to main window
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

    public void thirdWindow() {
        thirdPanel.removeAll();

        List<String> data = mysqlCon.GetDataFromDatabase();

        thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.Y_AXIS));
        thirdPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        thirdPanel.add(Box.createVerticalGlue());

        for (String record : data) {
            JLabel label = new JLabel(record);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            thirdPanel.add(label);

        }

        JButton backButton = new JButton("Back");
        JButton exportButton = new JButton("Export to CSV");

        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainWindow();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate today = LocalDate.now();
                String filePath = today + ".csv";
                mysqlCon.exportDataToCsv(data, filePath);
                JOptionPane.showMessageDialog(thirdPanel, "Data exported to " + filePath);
                showMainWindow();
            }
        });

        thirdPanel.add(backButton);
        thirdPanel.add(exportButton);


        thirdPanel.add(Box.createVerticalGlue());

        thirdPanel.revalidate();
        thirdPanel.repaint();

    }

    public void fourthWindow() {

        fourthPanel.setLayout(new GridLayout(10, 0));

        JPanel panelLayout = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();

        fourthPanel.add(panelLayout);
        fourthPanel.add(panel);
        fourthPanel.add(buttonPanel);

        JTextField textField = new JTextField(10);

        JLabel label = new JLabel("Delete data from id: ");

        JButton DeleteDataButton = new JButton("Delete data");
        JButton BackButton = new JButton("Back");

        DeleteDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Id = textField.getText();

                System.out.println(Id);

                Integer intID = Integer.valueOf(Id);

                mysqlCon.DeleteDataFromId(intID);

                JOptionPane.showMessageDialog(fourthPanel, "Deleted");

                showMainWindow();

            }
        });

        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainWindow();
            }
        });

        panelLayout.add(label);
        panelLayout.add(textField);

        buttonPanel.add(DeleteDataButton);
        buttonPanel.add(BackButton);


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

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showThirdWindow();
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFourthWindow();
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel messageDialog = new JPanel();

                JLabel textForMessage = new JLabel("Do You Really want to drop the table: ");

                JTextField textField = new JTextField(10);

                messageDialog.add(textForMessage);
                messageDialog.add(textField);


                JOptionPane.showMessageDialog(null, messageDialog);

                String AreYouSure = textField.getText();

                if (Objects.equals(AreYouSure, "yes")) {
                    mysqlCon.DropTable();
                    JOptionPane.showMessageDialog(mainPanel, "Deleted");
                    showMainWindow();
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "The action did not go passed");
                }

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

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    showThirdWindow();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_4) {
                    showFourthWindow();
                }
            }
        });

    }

    private void showMainWindow() {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Main");
    }

    private void showSecondaryWindow() {
        secondaryPanel.removeAll();
        secondaryWindow();
        secondaryPanel.revalidate();
        secondaryPanel.repaint();
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Secondary");
    }

    private void showThirdWindow() {
        thirdWindow();
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "Third");
    }

    private void showFourthWindow() {
        fourthPanel.removeAll();
        fourthWindow();
        fourthPanel.revalidate();
        fourthPanel.repaint();
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "fourth");
    }
}
