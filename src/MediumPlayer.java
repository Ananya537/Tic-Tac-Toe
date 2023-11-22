import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class MediumPlayer {
	int boardWidth = 600;
    int boardHeight = 750;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JLabel scorePlayerXLabel = new JLabel();
    JLabel scorePlayerOLabel = new JLabel();
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

    MediumPlayer() {
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
                                currentPlayer = playerO;
                                generateRandomMove();
                                checkWinner(); 
                                if (!gameOver) {
	                                currentPlayer = playerX; 
	                                textLabel.setText(currentPlayer + "'s turn.");
                                }
                            }
                        }

                    }
                });
            }
        }
    }
    
    void generateRandomMove() {
    	turns++;
        Random random = new Random();

        // Winning move for playerO horizontally
        for (int row = 0; row < 3; row++) {
            int countO = 0;
            int countEmpty = 0;
            int emptyCol = -1;

            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().equals(playerO)) {
                    countO++;
                } 
                else if (board[row][col].getText().equals("")) {
                    countEmpty++;
                    emptyCol = col;
                }
            }

            if (countO == 2 && countEmpty == 1) {
                board[row][emptyCol].setText(playerO);
                return;
            }
        }

        // Winning move for playerO vertically
        for (int col = 0; col < 3; col++) {
            int countO = 0;
            int countEmpty = 0;
            int emptyRow = -1;

            for (int row = 0; row < 3; row++) {
                if (board[row][col].getText().equals(playerO)) {
                    countO++;
                } 
                else if (board[row][col].getText().equals("")) {
                    countEmpty++;
                    emptyRow = row;
                }
            }

            if (countO == 2 && countEmpty == 1) {
                board[emptyRow][col].setText(playerO);
                return;
            }
        }

        

        // Winning move for playerO diagonally (left to right)
        int countO = 0;
        int countEmpty = 0;
        int emptyDiagonal = -1;

        for (int i = 0; i < 3; i++) {
            if (board[i][i].getText().equals(playerO)) {
                countO++;
            } 
            else if (board[i][i].getText().equals("")) {
                countEmpty++;
                emptyDiagonal = i;
            }
        }
        if (countO == 2 && countEmpty == 1) {
            board[emptyDiagonal][emptyDiagonal].setText(playerO);
            return;
        }
        
        
        
	    // Winning move for playerO diagonally (right to left)
        countO = 0;
        countEmpty = 0;
        emptyDiagonal = -1;

        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i].getText().equals(playerO)) {
                countO++;
            } 
            else if (board[i][2 - i].getText().equals("")) {
                countEmpty++;
                emptyDiagonal = i;
            }
        }

        if (countO == 2 && countEmpty == 1) {
            board[emptyDiagonal][2 - emptyDiagonal].setText(playerO);
            return;
        }
        
        
        
        // Blocking move for playerX horizontally
        for (int row = 0; row < 3; row++) {
            int countX = 0;
            countEmpty = 0;
            int emptyCol = -1;

            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().equals(playerX)) {
                    countX++;
                } 
                else if (board[row][col].getText().equals("")) {
                    countEmpty++;
                    emptyCol = col;
                }
            }

            if (countX == 2 && countEmpty == 1) {
                board[row][emptyCol].setText(playerO);
                return;
            }
        }
        
        
        
        // Blocking move for playerX vertically
        for (int col = 0; col < 3; col++) {
            int countX = 0;
            countEmpty = 0;
            int emptyRow = -1;

            for (int row = 0; row < 3; row++) {
                if (board[row][col].getText().equals(playerX)) {
                    countX++;
                } 
                else if (board[row][col].getText().equals("")) {
                    countEmpty++;
                    emptyRow = row;
                }
            }

            if (countX == 2 && countEmpty == 1) {
                board[emptyRow][col].setText(playerO);
                return;
            }
        }
        
        
        
        // Blocking move for playerX diagonally (left to right)
        int countX = 0;
        countEmpty = 0;
        emptyDiagonal = -1;

        for (int i = 0; i < 3; i++) {
            if (board[i][i].getText().equals(playerX)) {
                countX++;
            } 
            else if (board[i][i].getText().equals("")) {
                countEmpty++;
                emptyDiagonal = i;
            }
        }

        if (countX == 2 && countEmpty == 1) {
            board[emptyDiagonal][emptyDiagonal].setText(playerO);
            return;
        }

        

        // Blocking move for playerX diagonally (right to left)
        countX = 0;
        countEmpty = 0;
        emptyDiagonal = -1;

        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i].getText().equals(playerX)) {
                countX++;
            } 
            else if (board[i][2 - i].getText().equals("")) {
                countEmpty++;
                emptyDiagonal = i;
            }
        }

        if (countX == 2 && countEmpty == 1) {
            board[emptyDiagonal][2 - emptyDiagonal].setText(playerO);
            return;
        }

        // No winning or blocking move, generate a random move
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board[row][col].getText().equals(""));
        board[row][col].setText(playerO);
        return;
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
        if (currentPlayer == playerO) {
        	textLabel.setText("Computer won!");
        }
        else {
        	textLabel.setText("You won!");
        }
        return;
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
        return;
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
        turns = 0;
        if(firstPlayer == "X") {
        	currentPlayer = playerX;
        	firstPlayer = playerO;
        	generateRandomMove();
        }
        else {
        	currentPlayer = playerX;
        	firstPlayer = playerX;
        }
        gameOver = false;
        

        textLabel.setText(currentPlayer + "'s turn.");
    }
}
