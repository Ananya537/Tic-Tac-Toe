import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 750; 

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(4, 1));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        JButton tile1 = new JButton();
        boardPanel.add(tile1);
        tile1.setBackground(Color.green);
        tile1.setForeground(Color.white);
        tile1.setFont(new Font("Arial", Font.BOLD, 50));
        tile1.setFocusable(false);
        tile1.setText("Easy");

        JButton tile2 = new JButton();
        boardPanel.add(tile2);
        tile2.setBackground(Color.orange);
        tile2.setForeground(Color.white);
        tile2.setFont(new Font("Arial", Font.BOLD, 50));
        tile2.setFocusable(false);
        tile2.setText("Medium");

        JButton tile3 = new JButton();
        boardPanel.add(tile3);
        tile3.setBackground(Color.red);
        tile3.setForeground(Color.white);
        tile3.setFont(new Font("Arial", Font.BOLD, 50));
        tile3.setFocusable(false);
        tile3.setText("Hard");

        JButton tile4 = new JButton();
        boardPanel.add(tile4);
        tile4.setBackground(Color.blue);
        tile4.setForeground(Color.white);
        tile4.setFont(new Font("Arial", Font.BOLD, 50));
        tile4.setFocusable(false);
        tile4.setText("2 Player");
        
        tile1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                EasyPlayer easyPlayer = new EasyPlayer();
            }
        });
        
        tile2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                MediumPlayer mediumPlayer = new MediumPlayer();
            }
        });
        
        tile3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                HardPlayer hardPlayer = new HardPlayer();
            }
        });

        tile4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                TwoPlayer twoPlayer = new TwoPlayer();
            }
        });
    }
}