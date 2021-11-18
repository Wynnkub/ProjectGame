package projectgame;

import java.awt.event.MouseEvent;

public class Death{
    
    void mousePress(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        StartGame.restart();
        
        if (mx>60 && mx<210 && my>350 && my<390){
            StartGame.State = "menu";
        }
        else if(mx>60 && mx<210 && my>400 && my<440){
            StartGame.State="game";
        }
    }
}

