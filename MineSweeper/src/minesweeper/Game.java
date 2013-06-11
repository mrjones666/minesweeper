package minesweeper;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;

public class Game implements MouseListener {

    public int pointsToWin;
    private static Game instance = null;
    private Player[] players = new Player[2];
    private BoardSize boardSize = new BoardSize(14, 14);
    
    private boolean debugMode = true;

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
        // játékosok
        this.players[0] = new HumanPlayer("George");
//        this.players[1] = new HumanPlayer("HP", new ImageIcon("./media/img/zaszlo1.png"));
        this.players[1] = new ComputerPlayer();
        this.players[0].setActive(true);
        
        // játékosok felső barja
        StatusBar.instance().addPlayer(players);
        
        // játéktábla mérete
        MineField.instance().setSize1(this.boardSize.x, this.boardSize.y);

        // mezők létrehozása a játéktáblán
        this.createFields();
        
        // ablakbeállítások
        MainWindow.instance().setSize(750, 740);
        MainWindow.instance().setLocationRelativeTo(null);
        MainWindow.instance().setTitle("Aknakereső");
        MainWindow.instance().setBackground(new Color(92, 92, 92));
        MainWindow.instance().setVisible(true);
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
            field.disableClick();
    
            // check points
            this.newTurn(field);
            System.out.println(field.hasMine());
            boolean gameOver = this.getActivePlayer().getPoints() >= this.pointsToWin;
            if (gameOver) { 
                //System.out.println(this.getActivePlayer().getName() + " won!");
                this.newGame();
            }
            
            // ha a gép jönne, kérjük meg hogy rakjon
            if (this.getActivePlayer() instanceof ComputerPlayer) {
                
                ComputerPlayer player = (ComputerPlayer) this.getActivePlayer();
                // let it pick a field
                Field pickedField = player.pickField(MineField.instance().fields);
                // new turn
                this.handleFieldClick(pickedField);
            }
            
        }
    }
    
     private void newTurn(Field field) {
        this.pointsToWin = 2; // debug

        int i = 0;
        for (Player player : players) {
            // nem volt akna a kattintott helyen, másik pléjer jön
            if (!field.hasMine()) {
                player.toggleActive();

                int surroundingMines = MineField.instance().countSurroundingMines(field.getXPos(), field.getYPos());
                if (surroundingMines > 0) new Sound("field").play();
                else new Sound("flood").play(); // floodfill! todo
                
                MineField.instance().refreshField(field.getXPos(), field.getYPos(), surroundingMines);
            } // volt akna, dobjunk egy pontot az aktív pléjernek és maradjon aktív
            else if (field.hasMine() && player.isActive()) {
                player.addPoint();
                MineField.instance().refreshField(field.getXPos(), field.getYPos(), player.flagIcon);
            }

            // gui frissítése az eredmény alapján
            StatusBar.instance().refreshPlayer(player);

            i++;
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

    private int drawFirstPlayer() {
        return Math.random() < 0.5 ? 0 : 1;
    }

    private Player getWinner() {
        for (Player player : players) {
            if (player.getPoints() >= this.pointsToWin) {
                return player;
            }
        }

        return null;
    }

    private void newGame() {
        for (Player player : players) {
            player.reset();
            StatusBar.instance().refreshPlayer(player);
        }
        
        MineField.instance().resetFields();
//        this.createFields();
    }

    private Player getActivePlayer() {
        for (Player player : players) {
            if (player.isActive()) {
                return player;
            }
        }
        return null;
    }

    private void createFields() {
        Field field;
        boolean[][] generatedSpots = this.createSpots();
        for (int i = 1; i <= this.boardSize.x; i++) {
            for (int j = 1; j <= this.boardSize.y; j++) {
                // create a Field if its the first time
                if (MineField.instance().fields.length > i && MineField.instance().fields[i].length > j 
                        && MineField.instance().fields[i][j] instanceof Field) {
                    field = MineField.instance().fields[i][j];
                }
                else field = new Field();
                
                field.setPosition(i, j);
                if (generatedSpots[i][j]) {
                    field.addMine();
                    if (this.debugMode) {
                        field.setText("x");
                    }
                }

                field.addMouseListener(this);
                MineField.instance().addField(field);
            }
        }
    }

    private boolean[][] createSpots() {
        int x = this.boardSize.x, y = this.boardSize.y;
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

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public int getPointsToWin() {
        return pointsToWin;
    }

    public int getHumanPlayerPoints() {
        return players[0].getPoints();
    }

    public int getCompPlayerPoints() {
        return players[1].getPoints();
    }
}
