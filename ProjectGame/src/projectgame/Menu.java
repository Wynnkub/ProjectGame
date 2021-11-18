package projectgame;

import java.awt.event.MouseEvent;

public class Menu {
    
    void mousePress(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mx>150 && mx<300 && my>550 && my<590)
            StartGame.State = "game";
        else if(mx>1000 && mx<1150 && my>550 && my<590)
            System.exit(1);
        
    }
}
