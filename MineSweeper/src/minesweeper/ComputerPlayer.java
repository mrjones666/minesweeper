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
        
        boolean cheat;
        
        boolean again = true;
        
        Random rand = new Random();
        
        int randRow = rand.nextInt((Game.getBoardSize().x + 1) -1) + 1;
        int randCol = rand.nextInt((Game.getBoardSize().y + 1) -1) + 1;
        
        if(level == 0)
        {
            ;
        }
        
        if(level == 1)
        {    
            while(!fields[randRow][randCol].isClickable())
            {
                randRow = rand.nextInt((Game.getBoardSize().x + 1) -1) + 1;
                randCol = rand.nextInt((Game.getBoardSize().y + 1) -1) + 1;
            }
           
            System.out.println("CPU: " + randRow + '-' + randCol + " - " + 
            "hasMine: " + fields[randRow][randCol].hasMine() + " - " + "clickable: " 
            + fields[randRow][randCol].isClickable());
        }
        
        if(level == 2)
        {
            ;
        }
        
        if(level == 3)
        {
            while(!fields[randRow][randCol].isClickable())
            {         
                randRow = rand.nextInt((Game.getBoardSize().x + 1) -1) + 1;
                randCol = rand.nextInt((Game.getBoardSize().y + 1) -1) + 1;
                
                if(Game.getPointsToWin() - Game.getHumanPlayerPoints() <= 3)
                {
                    do 
                    {   
                       randRow = rand.nextInt((Game.getBoardSize().x + 1) -1) + 1;
                       randCol = rand.nextInt((Game.getBoardSize().y + 1) -1) + 1;
                        
                        while(!fields[randRow][randCol].isClickable() && !fields[randRow][randCol].hasMine())
                        {
                            randRow = rand.nextInt((Game.getBoardSize().x + 1) -1) + 1;
                            randCol = rand.nextInt((Game.getBoardSize().y + 1) -1) + 1;
                        }
                        
                    } while (Game.getPointsToWin() - Game.getCompPlayerPoints() != 1);
                }
             }
           }                          
        return fields[randRow][randCol];
    }   
}
