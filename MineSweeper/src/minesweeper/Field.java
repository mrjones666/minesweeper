package minesweeper;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//teszt
public class Field extends JButton {

    private int x, y;
    private boolean clickable = true;
    private boolean hasMine = false;

    public Field() {
        //this.addMouseListener(Game.instance());
        this.setIcon(new ImageIcon("./media/img/akna.png"));
        this.setBorder(BorderFactory.createLineBorder(new Color(59, 153, 203), 2));
        this.setVerticalTextPosition(JButton.CENTER);
        this.setHorizontalTextPosition(JButton.CENTER);
    }

    public void setClickable(boolean value) {
        this.setIcon(new ImageIcon("./media/img/akna.png"));
        this.clickable = value;
    }

    public void setMine(boolean value) {
        this.hasMine = value;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public void disableClick() {
        this.setBorder(BorderFactory.createLineBorder(new Color(1, 153, 203), 2));
        this.setIcon(new ImageIcon("./media/img/aknaClicked.png"));
        this.clickable = false;
    }

    public void addMine() {
        this.hasMine = true;
    }

    public boolean hasMine() {
        return this.hasMine;
    }

    public int getXPos() {
        return this.x;
    }

    public int getYPos() {
        return this.y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
