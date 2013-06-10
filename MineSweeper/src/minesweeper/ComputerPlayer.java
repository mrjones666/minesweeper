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
        
        Field [][] temp = new Field[fields.length][fields.length];
                        
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
          for (int i = 1; i <= Game.getBoardSize().x; i++) 
          {
             for (int j = 1; j <= Game.getBoardSize().y; j++) 
             {
                if(fields[i][j].getText() == "x")
                {
                    System.out.println(fields[i][j].getText() + ": " + fields[i][j].getXPos()
                    + " - " + fields[i][j].getYPos());
                }   
             }
          }
        }
        
        if(level == 3)
        {
            for (int i = 1; i <= Game.getBoardSize().x; i++) 
            {
                for (int j = 1; j <= Game.getBoardSize().y; j++) 
                {
                    if(fields[i][j].getText() != "3")
                    {
                        System.out.println(fields[i][j].getText() + ": " + fields[i][j].getXPos() 
                        + " - " + fields[i][j].getYPos());
                    }   
                 }
            }
            
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
