package minesweeper;

import java.util.Random;
import javax.swing.ImageIcon;

public class ComputerPlayer extends Player {
    
    public int level;
    
    public ComputerPlayer() {
        super("Computer", new ImageIcon("./media/img/zaszlo1.png"));
        this.level = 1;
    }
    
    public ComputerPlayer(int level) {
        super("Computer", new ImageIcon("./media/img/zaszlo1.png"));
        this.level = level;
    }
    
    public ComputerPlayer(int level, ImageIcon flagIcon) {
        super("Computer", flagIcon);
        this.level = level;
    }
    
    public Field pickField(Field[][] fields) {

        Random rand = new Random();
        
        int randRow = rand.nextInt((Game.getBoardSize().x + 1) -1) + 1;
        int randCol = rand.nextInt((Game.getBoardSize().y + 1) -1) + 1;
        
        
        boolean again = true;
        while (!again) {
            randRow = rand.nextInt((Game.getBoardSize().x + 1) -1) + 1;
            randCol = rand.nextInt((Game.getBoardSize().y + 1) -1) + 1;
            
            if (fields[randRow][randCol].hasMine() && fields[randRow][randCol].isClickable())
                again = !again;
        }
        
        //if(level == 3)
        //{
//            //while(!fields[randRow][randCol].isClickable()) //nincs vége a játéknak, ha eléri a 20-at (sem a legvégén)
//            while(!fields[randRow][randCol].hasMine() && !fields[randRow][randCol].isClickable()) //elvileg azonnal meg kellene találnia az összeset, ezzel szemben 8-16ot talál egyszerre és megáll, pedig még ő jönne
//            {
//                randRow = rand.nextInt((Game.getBoardSize().x + 1) -1) + 1;
//                randCol = rand.nextInt((Game.getBoardSize().y + 1) -1) + 1;
//            }                         
        //}
        System.out.println("CPU: " + randRow + '-' + randCol);
        return fields[randRow][randCol];
        // todo
        //return null;
    }   
}
