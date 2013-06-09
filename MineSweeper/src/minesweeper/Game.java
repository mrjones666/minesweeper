package minesweeper;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class Game implements MouseListener {

    private static Game instance = null;
    private Player[] players;
    private Board board;
    private static BoardSize boardSize;
    private int pointsToWin;
    private boolean debugMode = false;

    /**
     * Only one instance.
     *
     * @return Game
     */
    public static Game instance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    private Game() {
        // create players and decide who starts
        this.players = new Player[2];
        this.players[0] = new HumanPlayer("George");
        this.players[1] = new ComputerPlayer(3);

        // sorsoljuk ki, hogy ki kezd
        //this.players[this.drawFirstPlayer()].setActive(true);
        this.players[0].setActive(true);

        // create view
        this.boardSize = new BoardSize(14, 14);
        this.board = Board.instance(this.boardSize);
        boolean[][] spots = this.createSpots(this.boardSize.x, this.boardSize.y);

        // add players to the view
        for (Player player : players) {
            this.board.addPlayer(player.getName(), player.isActive());
        }

        // tell the view how to work
        this.board.addFields(spots);
        this.board.setSize(750, 740);
        this.board.setLocationRelativeTo(null);
        this.board.setTitle("Aknakereső");
        this.board.setBackground(new Color(92, 92, 92));

        this.addListeners();
        this.board.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof Field) {
            Field field = (Field) e.getSource();
            this.handleFieldClick(field);
        }
    }

    private void handleFieldClick(Field field) {
        if (field.isClickable()) {

            field.setBorder(BorderFactory.createLineBorder(new Color(1, 153, 203), 2));
            field.setIcon(new ImageIcon("./media/img/aknaClicked.png"));

            // ne reagáljon kétszer ugyanarra a gombra
            field.disableClick();
           
            // check points
            this.newTurn(field.getXPos(), field.getYPos(), field.hasMine());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof Field) {
            Field field = (Field) e.getSource();
            if (field.isClickable()) {
                field.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof Field) {
            Field field = (Field) e.getSource();
            if (field.isClickable()) {
                field.setBorder(BorderFactory.createLineBorder(new Color(59, 153, 203), 2));
            }
        }
    }

    public void setDebugMode(boolean value) {
        this.debugMode = value;
    }

    private int drawFirstPlayer() {
        return Math.random() < 0.5 ? 0 : 1;
    }

    private int countSurroundingMines(int x, int y) {
        int result = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if ((i > 0 && i <= this.boardSize.x && j > 0 && j <= this.boardSize.y)
                        && this.board.fields[i][j].hasMine()) {
                    result++;
                }
            }
        }

        return result;
    }
    
    private Player getWinner() {
        for (Player player : players) {
            if (player.getPoints() >= this.pointsToWin)
                return player;
        }
        
        return null;
    }

    private void newTurn(int x, int y, boolean hadMine) {
        this.pointsToWin = 2; // debug
        
        int i = 0;
        for (Player player : players) {
            // nem volt akna a kattintott helyen, másik pléjer jön
            if (!hadMine) {
                player.toggleActive();

                int surroundingMines = this.countSurroundingMines(x, y);
                if (surroundingMines > 0) {
                    new Sound("field").play();
                } else {
                    new Sound("flood").play();
                    // floodfill! todo
                }

                this.board.refreshField(x, y, surroundingMines);
            } // volt akna, dobjunk egy pontot az aktív pléjernek és maradjon aktív
            else if (hadMine && player.isActive()) {
                player.addPoint();
                this.board.refreshField(x, y, player.flagIcon);
            }

            // gui frissítése az eredmény alapján
            this.board.refreshPlayer(i, player);
            
            if (player.getPoints() >= this.pointsToWin) {
                System.out.println(player.getName() + " has " + player.getPoints() + " points.");
                System.out.println("He won.");
                
                this.newGame();
            }
            
            i++;
        }

        if (this.getActivePlayer() instanceof ComputerPlayer) {
            ComputerPlayer player = (ComputerPlayer) this.getActivePlayer();
            // let it pick a field
            Field pickedField = player.pickField(this.board.fields);
            // new turn
            this.handleFieldClick(pickedField);
        }

    }

    private void newGame() {
        int i = 0;
        for (Player player : players) {
            player.reset();
            this.board.refreshPlayer(i, player);
            i++;
        }

        boolean[][] spots = this.createSpots(this.boardSize.x, this.boardSize.y);
        this.board.resetFields();
        this.board.addFields(spots);
    }

    private int isGameOver() {
        //System.out.println(this.pointsToWin + " aknát kell megtalálni a győzelemhez.");
//        this.pointsToWin = 2;
//        for (Player player : players) {
//            System.out.println(player.getName() + " has " + player.getPoints() + " points.");
//            if (player.getPoints() >= this.pointsToWin) {
//                
//                return 1;
//            } /*else if (player.getPoints() == this.pointsToWin - 1
//                    || player.getPoints() == this.pointsToWin - 3) {
//                return -1;
//            }*/
//        }
//
        return 0;
    }

    private Player getActivePlayer() {
        for (Player player : players) {
            if (player.isActive()) {
                return player;
            }
        }

        return null;
    }

    private boolean[][] createSpots(int x, int y) {
        boolean[][] generatedSpots = new boolean[(x * y) / 5 + 1][(x * y) / 5 + 1];

        for (int i = 1; i < generatedSpots.length; i++) {
            for (int j = 1; j < generatedSpots.length; j++) {
                generatedSpots[i][j] = false;
            }
        }

        int temp = 0;
        int randomRow = 0, randomCol = 0;
        while (temp != (x * y) / 5) {
            randomRow = (int) (Math.random() * (x)) + 1;
            randomCol = (int) (Math.random() * (y)) + 1;
            if (!generatedSpots[randomRow][randomCol]) {
                generatedSpots[randomRow][randomCol] = true;
                temp++;
            }
        }

        // ennyi aknát kell találni hogy valaki győzzön,
        // tároljuk el, hogy ne kelljen minden körben kiszámolni!
        this.pointsToWin = (int) ((temp / 2) + 1);

        return generatedSpots;
    }

    private void addListeners() {
        int count = 0;
        for (int i = 1; i <= this.boardSize.x; i++) {
            for (int j = 1; j <= this.boardSize.y; j++) {
                this.board.fields[i][j].addMouseListener(this);
                if (this.board.fields[i][j].hasMine()) {
                    count++;
                }
            }
        }
    }

    public static BoardSize getBoardSize() {
        return boardSize;
    }
}
