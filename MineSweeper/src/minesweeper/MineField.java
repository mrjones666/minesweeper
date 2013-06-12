package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MineField extends JPanel {

    public Field[][] fields;
    private static MineField instance = null;
    private int x, y;

    public static MineField instance() {
        if (instance == null) {
            instance = new MineField();
        }

        return instance;
    }

    private MineField() {
        super();
    }

    public void addField(Field field) {
        this.fields[field.getXPos()][field.getYPos()] = field;
        this.add(field);
    }

    public void refreshField(int x, int y, ImageIcon flag) {
        new Sound("flag").play();
        Field field = this.fields[x][y];
        field.disableClick();
        field.setText("");
        field.setIcon(flag);
    }

    public void refreshField(int x, int y, int surroundingMines) {
        Field field = this.fields[x][y];
        field.disableClick();
        field.setText(surroundingMines + "");
    }

    public void floodFill(int x, int y) {
        if ((x > 0 && x <= this.x && y > 0 && y <= this.y)) {
            int surroundingMines = this.countSurroundingMines(x, y);

            if (this.fields[x][y].isClickable()) {
                this.refreshField(x, y, surroundingMines);

                if (surroundingMines == 0) {
                    this.floodFill(x, y + 1); //jobboldala
                    this.floodFill(x, y - 1); //baloldala
                    this.floodFill(x + 1, y); //alatta
                    this.floodFill(x - 1, y); //felette
                    this.floodFill(x + 1, y - 1); // átlók
                    this.floodFill(x + 1, y + 1);
                    this.floodFill(x - 1, y + 1);
                    this.floodFill(x - 1, y - 1);
                }
            }
        }
    }

    public void resetFields() {
        for (int i = 1; i <= this.x; i++) {
            for (int j = 1; j <= this.y; j++) {
                this.fields[i][j].setClickable(true);
                this.fields[i][j].setMine(false);
                this.fields[i][j].setText("");
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return Integer
     */
    public int countSurroundingMines(int xPos, int yPos) {
        int result = 0;
        for (int i = xPos - 1; i < xPos + 2; i++) {
            for (int j = yPos - 1; j < yPos + 2; j++) {
                if ((i > 0 && i <= this.x && j > 0 && j <= this.y)
                        && this.fields[i][j].hasMine()) {
                    result++;
                }
            }
        }

        return result;
    }

    public void setSize1(int x, int y) {
        this.fields = new Field[x + 1][y + 1];
        this.x = x;
        this.y = y;
        // hány mező legyen?
        this.setLayout(new GridLayout(x, y));
        // méret beállítása az x - y alapján!
        this.setPreferredSize(new Dimension(640, 640));
    }

    public void onBomb(Field field) {
        int x = field.getXPos();
        int y = field.getYPos();
        try { //át kell írni, nagyon béna!
            if (x > 0) {
                this.fields[x - 1][y - 1].setBorder(
                        BorderFactory.createMatteBorder(
                        2, 2, 0, 0, Color.black));

                this.fields[x - 1][y + 1].setBorder(
                        BorderFactory.createMatteBorder(
                        2, 0, 0, 2, Color.black));

                this.fields[x + 1][y - 1].setBorder(
                        BorderFactory.createMatteBorder(
                        0, 2, 2, 0, Color.black));

                this.fields[x + 1][y + 1].setBorder(
                        BorderFactory.createMatteBorder(
                        0, 0, 2, 2, Color.black));
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void offBomb(Field field) {
        int x = field.getXPos();
        int y = field.getYPos();
        try { //át kell írni, nagyon béna!
            if (this.fields[x - 1][y - 1].isClickable()) {
                this.fields[x - 1][y - 1].setBorder(BorderFactory.createLineBorder(
                        new Color(59, 153, 203), 2));
            } else {
                this.fields[x - 1][y - 1].setBorder(BorderFactory.createLineBorder(new Color(136, 114, 115), 1));
            }

            if (this.fields[x - 1][y + 1].isClickable()) {
                this.fields[x - 1][y + 1].setBorder(
                        BorderFactory.createLineBorder(new Color(59, 153, 203), 2));
            } else {
                this.fields[x - 1][y + 1].setBorder(BorderFactory.createLineBorder(new Color(136, 114, 115), 1));
            }

            if (this.fields[x + 1][y - 1].isClickable()) {
                this.fields[x + 1][y - 1].setBorder(
                        BorderFactory.createLineBorder(new Color(59, 153, 203), 2));
            } else {
                this.fields[x + 1][y - 1].setBorder(BorderFactory.createLineBorder(new Color(136, 114, 115), 1));
            }
            if (this.fields[x + 1][y + 1].isClickable()) {
                this.fields[x + 1][y + 1].setBorder(
                        BorderFactory.createLineBorder(new Color(59, 153, 203), 2));
            } else {
                this.fields[x + 1][y + 1].setBorder(BorderFactory.createLineBorder(new Color(136, 114, 115), 1));
            }
        } catch (Exception e) {
            System.out.println("Exception, de most leszarom!");
        }
    }
}
