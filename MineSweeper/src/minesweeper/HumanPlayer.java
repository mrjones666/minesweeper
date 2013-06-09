package minesweeper;

import javax.swing.ImageIcon;

public class HumanPlayer extends Player {
    
    public HumanPlayer(String name) {
        super(name, new ImageIcon("./media/img/zaszlo0.png"));
    }
    
    public HumanPlayer(String name, ImageIcon flagIcon) {
        super(name, flagIcon);
    }
    
}
