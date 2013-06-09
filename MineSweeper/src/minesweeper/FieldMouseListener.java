package minesweeper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FieldMouseListener extends MouseAdapter {
    
    public int x, y;
    
    public FieldMouseListener(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered for rating " + this.x);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited for rating " + this.x);
    }
}
