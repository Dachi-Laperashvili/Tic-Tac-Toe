import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe implements ActionListener {
    private int turn = 1;
    JFrame frame = new JFrame("TicTacToe");
    JLabel label = new JLabel("Tic-Tac-Toe", SwingConstants.CENTER);
    JPanel panel = new JPanel();
    JButton[][] buttons = new JButton[3][3];
    JButton restartBtn = new JButton("Restart");
    private boolean gameOver = false;

    public TicTacToe(){
        frame.setSize(500,500);
        frame.setResizable(false);
        label.setFont(new Font("Ink Free", Font.BOLD,48));
        label.setBackground(Color.black);
        label.setOpaque(true);
        label.setForeground(Color.green);
        frame.add(label,BorderLayout.NORTH);
        panel.setLayout(new GridLayout(3,3));

        for(int i=0; i<buttons.length; i++){
            for(int j=0; j<buttons[0].length; j++){
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.black);
                buttons[i][j].setForeground(Color.green);
                buttons[i][j].setFont(new Font("Arcade Classic",Font.BOLD,64));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }


        restartBtn.setBackground(Color.black);
        restartBtn.setForeground(Color.green);
        restartBtn.setFocusable(false);
        restartBtn.addActionListener(this);
        restartBtn.setFont(new Font("Arcade Classic",Font.BOLD,24));
        frame.add(restartBtn,BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String player = turn % 2 == 1 ? "X" : "O";

        if(e.getSource() == restartBtn){
            restart();
        }

        // performs game logic when clicking on each button in the grid
        for(int row = 0; row < buttons.length; row++){
            for(int col = 0; col < buttons[0].length; col++){
                if(buttons[row][col] == e.getSource()){
                    if(pickLocation(row,col)){
                        takeTurn(row,col);
                        if(checkWin()){
                            label.setText(player + " won the game");
                            disableAllBtns();
                            gameOver = true;
                        }
                    }
                    if(turn > 9 && !gameOver){
                        label.setText("It's a draw!");
                        disableAllBtns();
                        gameOver = true;
                    }
                }
            }
        }
    }

    //    this method disables all button
    public void disableAllBtns(){
        for(JButton[] button: buttons){
            for(JButton btn: button){
                btn.setEnabled(false);
            }
        }
    }


    //  this method returns true if space row,col is a valid space
    public boolean pickLocation(int row, int col){
        if(row > buttons.length - 1 || col > buttons[0].length - 1){
            return false;
        }else if(buttons[row][col].getText().equals("X") || buttons[row][col].getText().equals("O")){
            return false;
        }
        return true;
    }

    // this method places an X or O at specified location based on int turn
    public void takeTurn(int row,int col){
        if(turn % 2 == 1){
            buttons[row][col].setText("X");
            label.setText("O turn");
        }else{
            buttons[row][col].setText("O");
            label.setText("X turn");
        }
        turn++;
    }

    //  checks if rows are equal
    public boolean checkRow(){
        for (JButton[] button : buttons) {
            if (!button[0].getText().isEmpty() && button[0].getText().equals(button[1].getText()) && button[1].getText().equals(button[2].getText())) {
                button[0].setText("<html>" + button[0].getText() + "</html>");
                button[1].setText("<html>" + button[1].getText() + "</html>");
                button[2].setText("<html>" + button[2].getText() + "</html>");

                return true;
            }
        }
        return false;
    }

    //  checks if columns are equal
    public boolean checkCol(){
        for(int i=0; i < buttons[0].length; i++){
            if(!buttons[0][i].getText().isEmpty() && buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[1][i].getText().equals(buttons[2][i].getText())){
                buttons[0][i].setText("<html>" + buttons[0][i].getText() + "</html>");
                buttons[1][i].setText("<html>" + buttons[1][i].getText() + "</html>");
                buttons[2][i].setText("<html>" + buttons[2][i].getText() + "</html>");

                return true;
            }
        }
        return false;
    }

    //  checks if diagonals are equal
    public boolean checkDiag(){
        if(!buttons[0][0].getText().isEmpty() && buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText())){
            buttons[0][0].setText("<html>" + buttons[0][0].getText() + "</html>");
            buttons[1][1].setText("<html>" + buttons[1][1].getText() + "</html>");
            buttons[2][2].setText("<html>" + buttons[2][2].getText() + "</html>");

            return true;

        }

        if(!buttons[0][2].getText().isEmpty() && buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText())){
            buttons[0][2].setText("<html>" + buttons[0][2].getText() + "</html>");
            buttons[1][1].setText("<html>" + buttons[1][1].getText() + "</html>");
            buttons[2][0].setText("<html>" + buttons[2][0].getText() + "</html>");

            return true;
        }
        return false;
    }

    //  this method checks if X or O won the game
    public boolean checkWin(){
        return checkCol() || checkRow() || checkDiag();
    }

    public void restart(){
        turn = 1;
        label.setText("Tic-Tac-Toe");
        for(JButton[] row: buttons){
            for(JButton btn: row){
                btn.setEnabled(true);
                btn.setText("");
            }
        }
        gameOver = false;
    }
}
