import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TwoPlayer {
    int boardWidth = 600;
    int boardHeight = 750;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JLabel scorePlayerXLabel = new JLabel();
    JLabel scorePlayerOLabel = new JLabel();
    JLabel scoreLabel = new JLabel();
    JButton resetButton = new JButton("Reset");
    JButton backButton = new JButton("Back");

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    String firstPlayer = playerX;
    int playerXScore = 0;
    int playerOScore = 0;

    boolean gameOver = false;
    int turns = 0;

    TwoPlayer() {
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

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);
        
        scorePanel.setLayout(new GridLayout(2, 2));
        scorePanel.setBackground(Color.darkGray);
    
        scorePlayerXLabel.setText("Player X: " + playerXScore );
        scorePlayerXLabel.setBackground(Color.darkGray);
        scorePlayerXLabel.setForeground(Color.white);
        scorePlayerXLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        scorePlayerXLabel.setHorizontalAlignment(JLabel.CENTER);
        scorePlayerXLabel.setOpaque(true);
	    scorePanel.add(scorePlayerXLabel);
	    
	    scorePlayerOLabel.setText("Player O: " + playerOScore );
	    scorePlayerOLabel.setBackground(Color.darkGray);
	    scorePlayerOLabel.setForeground(Color.white);
	    scorePlayerOLabel.setFont(new Font("Arial", Font.PLAIN, 25));
	    scorePlayerOLabel.setHorizontalAlignment(JLabel.CENTER);
	    scorePlayerOLabel.setOpaque(true);
	    scorePanel.add(scorePlayerOLabel);
	
	    scorePanel.add(resetButton);
	    scorePanel.add(backButton);
	
	    frame.add(scorePanel, BorderLayout.SOUTH);
        
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();  
                TicTacToe ticTacToe = new TicTacToe();
            }
        });

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver)
                            return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == "") {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }

                    }
                });
            }
        }
    }

    void checkWinner() {
    	
        // horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "")
                continue;

            if (board[r][0].getText() == board[r][1].getText() &&
                    board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                scoreChange();
                gameOver = true;
                return;
            }
        }

        // vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "")
                continue;

            if (board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                scoreChange();
                gameOver = true;
                return;
            }
        }

        // diagonally
        if (board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            scoreChange();
            gameOver = true;
            return;
        }

        // anti-diagonally
        if (board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            scoreChange();
            gameOver = true;
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
     }
    
    void scoreChange() {
    	if(currentPlayer == "X") {
        	playerXScore++;
        	scorePlayerXLabel.setText("Player X: " + playerXScore );
        }
        else {
        	playerOScore++;
        	scorePlayerOLabel.setText("Player O: " + playerOScore );
        }
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }
    
    void resetGame() {
        // Clear the text on all buttons
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.darkGray);
                board[r][c].setForeground(Color.white);
            }
        }

        // Reset game variables
        if(firstPlayer == "X") {
        	currentPlayer = playerO;
        	firstPlayer = playerO;
        }
        else {
        	currentPlayer = playerX;
        	firstPlayer = playerX;
        }
        gameOver = false;
        turns = 0;
        textLabel.setText(currentPlayer + "'s turn.");
    }
}
