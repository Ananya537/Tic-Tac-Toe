import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HardPlayer {
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
    String[][] checkBoard = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""}
    };
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    String firstPlayer = playerX;
    int playerXScore = 0;
    int playerOScore = 0;

    boolean gameOver = false;
    int turns = 0;

    HardPlayer() {
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

                        JButton clickedButton = (JButton) e.getSource();
                        if (clickedButton.getText().equals("")) {
                            clickedButton.setText(currentPlayer);
                            turns++;

                            // Find the position of the clicked button in the checkBoard and update it
                            for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {
                                    if (board[i][j] == clickedButton) {
                                        checkBoard[i][j] = currentPlayer;
                                        break;
                                    }
                                }
                            }

                            // Check for a winner after the player's move
                            checkWinner(checkBoard, currentPlayer, true);

                            if (!gameOver) {
                                currentPlayer = playerO;
                                computeMove();
                                // Check for a winner after the computer's move
                                checkWinner(checkBoard, currentPlayer, true);
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
    
    void computeMove() {
        turns++;
        int[] bestMove = bestMove(checkBoard);
        if (bestMove[0] != -1 && bestMove[1] != -1) {
            // Simulate computer's move
            checkBoard[bestMove[0]][bestMove[1]] = playerO;
            board[bestMove[0]][bestMove[1]].setText(playerO);
            return;
        }
    }


    boolean checkWinner(String[][] tempboard, String player, boolean check) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (tempboard[i][0] == player && tempboard[i][1] == player && tempboard[i][2] == player) {
            	if (check) {
            		for (int col = 0; col < 3; col++) {
                        setWinner(board[i][col]);
                    }
                    scoreChange();
                    gameOver = true;
            	}
                return true;
            }
            if (tempboard[0][i] == player && tempboard[1][i] == player && tempboard[2][i] == player) {
            	if (check) {
            		for (int row = 0; row < 3; row++) {
                        setWinner(board[row][i]);
                    }
                    scoreChange();
                    gameOver = true;
            	}
            	return true;
            }
        }

        // Check diagonals
        if(tempboard[0][0] == player && tempboard[1][1] == player && tempboard[2][2] == player) {
        	if(check) {
        		for (int i = 0; i < 3; i++) {
                    setWinner(board[i][i]);
                }
                scoreChange();
                gameOver = true;
        	}
        	return true;
        }
               
        if(tempboard[0][2] == player && tempboard[1][1] == player && tempboard[2][0] == player) {
			if(check) {
				setWinner(board[0][2]);
	            setWinner(board[1][1]);
	            setWinner(board[2][0]);
	            scoreChange();
	            gameOver = true;
			}
			return true;
        }
        
        if (check && turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
		return check;
    }
    
    
    boolean isBoardFull(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == "") {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    int[] bestMove(String[][] board) {
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        
        // Iterate over all empty cells and find the best move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == "") {
                    board[i][j] = playerO;  // Simulate computer's move

                    int score = miniMax(board, 0, false);

                    board[i][j] = "";  // Undo the move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    int miniMax(String[][] board, int depth, boolean isMaximizing) {
        if (checkWinner(board, playerX, false)) {
            return -1;
        }
        if (checkWinner(board, playerO, false)) {
            return 1;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == "") {
                        board[i][j] = playerO;
                        bestScore = Math.max(bestScore, miniMax(board, depth + 1, false));
                        board[i][j] = "";  // Undo the move
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == "") {
                        board[i][j] = playerX;
                        bestScore = Math.min(bestScore, miniMax(board, depth + 1, true));
                        board[i][j] = "";  // Undo the move
                    }
                }
            }
            return bestScore;
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
                checkBoard[r][c] = "";
            }
        }

        // Reset game variables
        turns = 0;
        if(firstPlayer == "X") {
        	currentPlayer = playerX;
        	firstPlayer = playerO;
        	computeMove();
        }
        else {
        	currentPlayer = playerX;
        	firstPlayer = playerX;
        }
        gameOver = false;
        

        textLabel.setText(currentPlayer + "'s turn.");
    }
}
