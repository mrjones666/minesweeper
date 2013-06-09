package minesweeper;

import javax.swing.JButton;
//teszt
public class Field extends JButton {
    
    private int x, y;
    private boolean clickable = true;
    private boolean hasMine = false;
    
    public void setClickable(boolean value) {
        this.clickable = value;
    }
    
    public void setMine(boolean value) {
        this.hasMine = value;
    }
    
    public boolean isClickable() {
        return this.clickable;
    }
    
    public void disableClick() {
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
