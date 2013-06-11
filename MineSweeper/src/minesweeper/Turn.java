package minesweeper;

public class Turn {
    
    public int number, x, y;
    public boolean hit;

    public Turn(int number, int x, int y, boolean hit) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.hit = hit;
    }

    @Override
    public String toString() {
        return "Turn{" + "number=" + number + ", x=" + x + ", y=" + y + ", hit=" + hit + '}';
    }
    
    
    
}
