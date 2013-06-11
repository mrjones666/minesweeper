package minesweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MineField extends JPanel {

    private static MineField instance = null;
    private int x, y;
    public Field[][] fields;

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
        field.setText("");
        field.setIcon(flag);
    }

    public void refreshField(int x, int y, int surroundingMines) {
        Field field = this.fields[x][y];
        field.setText(surroundingMines + "");
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
}
