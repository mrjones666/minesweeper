package minesweeper;

import javax.swing.ImageIcon;

public class Player {

    public ImageIcon flagIcon;
    private String name;
    private int points;
    private boolean active, bomb;

    public Player(String name, ImageIcon flagIcon) {
        this.name = name;
        this.flagIcon = flagIcon;

        // set initial values
        this.points = 0;
        this.bomb = true;
        this.active = false;
    }
    
    public void reset() {
        this.points = 0;
        this.bomb = true;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return this.name;
    }

    public boolean hasBomb() {
        return this.bomb;
    }

    public void useBomb() {
        this.bomb = !this.bomb;
    }

    public void addPoint() {
        this.points++;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public void toggleActive() {
        this.active = !this.active;
    }
    
    public void setFlag(ImageIcon flagIcon)
    {
        this.flagIcon = flagIcon;
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", points=" + points + ", active=" + active + ", bomb=" + bomb + '}';
    }
}
