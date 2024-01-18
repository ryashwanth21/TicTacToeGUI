import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private JLabel turnLabel;

    public TicTacToeGUI() {
        buttons = new JButton[3][3];
        currentPlayer = 'X';

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        turnLabel = new JLabel("Player " + currentPlayer + "'s turn");
        controlPanel.add(turnLabel);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        controlPanel.add(clearButton);

        JButton endGameButton = new JButton("End Game");
        endGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endGame();
            }
        });
        controlPanel.add(endGameButton);

        add(controlPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set initial window size to fullscreen mode
        //setUndecorated(true); // Remove window decorations for fullscreen

        //pack(); // Pack after setting undecorated and fullscreen
        //setLocationRelativeTo(null);
        setVisible(true);
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        turnLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private void checkWin() {
    // Check for a win in rows, columns, and diagonals
    if (checkRows() || checkColumns() || checkDiagonals()) {
        announceWinner();
    } else if (isTie()) {
        JOptionPane.showMessageDialog(this, "It's a tie!");
        resetGame();
    }
}

private boolean checkRows() {
    for (int i = 0; i < 3; i++) {
        if (buttons[i][0].getText().equals(String.valueOf(currentPlayer))
                && buttons[i][1].getText().equals(String.valueOf(currentPlayer))
                && buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
    }
    return false;
}

private boolean checkColumns() {
    for (int i = 0; i < 3; i++) {
        if (buttons[0][i].getText().equals(String.valueOf(currentPlayer))
                && buttons[1][i].getText().equals(String.valueOf(currentPlayer))
                && buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
    }
    return false;
}

private boolean checkDiagonals() {
    return (buttons[0][0].getText().equals(String.valueOf(currentPlayer))
            && buttons[1][1].getText().equals(String.valueOf(currentPlayer))
            && buttons[2][2].getText().equals(String.valueOf(currentPlayer)))
            || (buttons[0][2].getText().equals(String.valueOf(currentPlayer))
            && buttons[1][1].getText().equals(String.valueOf(currentPlayer))
            && buttons[2][0].getText().equals(String.valueOf(currentPlayer)));
}


    private boolean isTie() {
        // Check if all buttons are filled, and no winner has been declared
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false; // Found an empty button, game is not a tie
                }
            }
        }
        return true; // All buttons are filled, and no winner, so it's a tie
    }

    private void announceWinner() {
        JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
        resetGame();
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null);
            }
        }
        currentPlayer = 'X';
        turnLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private void endGame() {
        dispose(); // Close the JFrame
        System.exit(0); // Terminate the program
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                buttons[row][col].setBackground(new Color(200, 200, 255));
                checkWin();
                switchPlayer();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGUI());
    }
}
