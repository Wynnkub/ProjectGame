package projectgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StartGame extends JPanel implements Runnable, KeyListener, MouseListener{
    
    private boolean ctrl_press;
    ArrayList projectiles;
    
    static StartGame starter = new StartGame();
    
    private static Robot robot;
    private BufferedImage currentSprite,stand_r,stand_l,walk_r,walk_r_1,walk_l,walk_l_1,crouch_r,
            crouch_l,menu,bullet_r,bullet_l,Jumped_r,Jumped_l,background1,background2,
            e_r,e_r_1,e_l,e_l_1,play,exit,logo,start,deathimg,menubutton,playagain;
    private static Background bg1,bg2,bg3,bg4;
    
    public static BufferedImage tiledirt,grasstop,tilestone,tiletree,tilerock;
    private static ArrayList<Tile> tilearray = new ArrayList<Tile>();
    public Animation anim_r,anim_l,anim,anim_still,anim_still_r,anim_still_l, 
                crouchdown_r,crouchdown_r1,crouchdown_l;
    public static Animation hanim_l,hanim_r;
    static String State = "start";
    Menu MENU;
    Death DEATH;
    
    public int times;
    boolean timestart = true;
    JLabel counterLabel;	
    Timer timer;	
    static int second = 250;
    
    static int HP = 1000;

    public void countdownTimer() {		
		timer = new Timer(1000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                second--;	
                 
				if(second==0) {  
					timer.stop();
                                        StartGame.restart();
				}
			}
                    });
		}

    public static void restart(){
                robot.getProjectiles().clear();
                tilearray.clear();
                Enemy.enemies.clear();
                bg1 = new Background(0, 0);
		bg2 = new Background(1620, 0);
                bg3 = new Background(-1920,0);
                bg4 = new Background(-1920*2,0);
                robot = new Robot();
                second = 250;
                HP = 1000;
                try {
                    starter.loadMap("data/map1.txt");
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
        }
        
        public void init() throws Exception{
            setFocusable(true);
            addKeyListener(this);
            addMouseListener(this);
            
                    Jumped_r = ImageIO.read(new File("data/jumped-r.png"));
                    Jumped_l = ImageIO.read(new File("data/jumped-l.png"));

                    background1 = ImageIO.read(new File("data/bg1.png"));
                    background2 = ImageIO.read(new File("data/bg2.png"));


                    tiledirt = ImageIO.read(new File("data/tiledirt.png"));
                    grasstop = ImageIO.read(new File("data/grasstop.png"));
                    tilestone = ImageIO.read(new File("data/tilestone.png"));
                    tiletree = ImageIO.read(new File("data/tiletree.png"));
                    tilerock = ImageIO.read(new File("data/tilerock.png"));

                    bullet_r = ImageIO.read(new File("data/bullet-r.png"));
                    bullet_l = ImageIO.read(new File("data/bullet-l.png"));
                    
                    stand_r = ImageIO.read(new File("data/stand-r.png"));
                    stand_l = ImageIO.read(new File("data/stand-l.png"));
                    walk_r = ImageIO.read(new File("data/walk-r.png"));
                    walk_r_1 = ImageIO.read(new File("data/walk-r-1.png"));
                    walk_l = ImageIO.read(new File("data/walk-l.png"));
                    walk_l_1 = ImageIO.read(new File("data/walk-l-1.png"));
                    
                    crouch_r = ImageIO.read(new File("data/crouch-r.png"));
                    crouch_l = ImageIO.read(new File("data/crouch-l.png"));
                    
                    menu = ImageIO.read(new File("data/menu.png"));
                    logo= ImageIO.read(new File("data/logo.png"));
                    play= ImageIO.read(new File("data/play.png"));
                    exit= ImageIO.read(new File("data/exit.png"));
                    
                    start = ImageIO.read(new File("data/intro1.png"));
                    
                    deathimg = ImageIO.read(new File("data/deathimg.png"));
                    menubutton = ImageIO.read(new File("data/menuButton.png"));
                    playagain = ImageIO.read(new File("data/playagainButton.png"));
                    
                    e_r = ImageIO.read(new File("data/heliboy-r.png"));
                    e_r_1 = ImageIO.read(new File("data/heliboy-r-1.png"));
                    e_l = ImageIO.read(new File("data/heliboy-l.png"));
                    e_l_1 = ImageIO.read(new File("data/heliboy-l-1.png"));
                    
                    hanim_l = new Animation(false);
                    hanim_l.addFrame(e_l, 500);
                    hanim_l.addFrame(e_l_1, 500);
                    
                    hanim_r = new Animation(false);
                    hanim_r.addFrame(e_r, 500);
                    hanim_r.addFrame(e_r_1, 500);                    
                    
                    crouchdown_r = new Animation(true);    
                    crouchdown_r.addFrame(stand_r, 20);
                    crouchdown_r.addFrame(crouch_r, 20);
            
                    crouchdown_l = new Animation(true);    
                    crouchdown_l.addFrame(stand_l, 20);
                    crouchdown_l.addFrame(crouch_l, 20);
            
                    anim_r = new Animation(false);
                    anim_r.addFrame(stand_r, 50);
                    anim_r.addFrame(walk_r, 50);
                    anim_r.addFrame(walk_r_1, 50);
                    
                    anim_l = new Animation(false);
                    anim_l.addFrame(stand_l, 50);
                    anim_l.addFrame(walk_l, 50);
                    anim_l.addFrame(walk_l_1, 50);
                    
            
                    anim = anim_r;
                    currentSprite = walk_r;  
                    
            }
            
        public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(1920, 0);
                bg3 = new Background(-1920,0);
                bg4 = new Background(-1920*2,0);
                robot = new Robot();
                MENU = new Menu();
                DEATH = new Death();
                countdownTimer();
        
                try {
                    loadMap("data/map1.txt");
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
             
                Thread thread = new Thread(this);
		thread.start();
            
	}

                
    public void run() {
		while (true) {
                    
                    switch (State){
                        
                        case "start":                            
                            break;
                        
                        case "menu":
                            break;
                            
                        case "dead":                            
                            break;                          
                            
                        case "game": 
                            gameUpdate();
                            timer.start();	
                            break;
                        
                    }
			repaint();
                        
			try {
				Thread.sleep(17);
			} 
                        catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
        }
    
    public void gameUpdate(){
                        
                        robot.update();
                        bg1.update();
			bg2.update();
                        bg3.update();
                        bg4.update();
                        Enemy.update();
                        updateTiles();

                        hanim_l.update(50);
                        hanim_r.update(50);
                        
			ArrayList projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isVisible() == true) {
					p.update();
				} else {
					projectiles.remove(i);
				}
			}
                        
                        for (int i = 0; i < Enemy.enemies.size(); i++){
                            if (Enemy.enemies.get(i).getIsDead())
                                Enemy.enemies.remove(i);
                        }
                
                        if (robot.isJumped() && robot.getDirection() == "right") {
				currentSprite = Jumped_r;
			}
                        else if (robot.isJumped() && robot.getDirection() == "left") {
				currentSprite = Jumped_l;
			} 
                        else if ((robot.getDirection() == "right") && (robot.getSpeedX() == 0)&& robot.isDucked()==false){
                            currentSprite = walk_r;
                        }
                        else if ((robot.getDirection() == "left") && (robot.getSpeedX() == 0) && robot.isDucked()==false){
                            currentSprite = walk_l;
                        }
                        
                        else {
                                if (robot.getSpeedX()<0){
                                    anim = anim_l;
                                }
                                else if (robot.getSpeedX()>0){
                                    anim = anim_r;
                                }
                                else if (robot.isDucked() && robot.getDirection() == "right"){
                                    anim = crouchdown_r;
                                }
                                else if (robot.isDucked() && robot.getDirection() == "left"){
                                    anim = crouchdown_l;
                                }
                        currentSprite = anim.getImage();
                        anim.update(10);
                        }
                        if (robot.getCenterY()>1400){
                            State = "dead";
                        }
        }
        public void paint(Graphics g) {
            
            switch (State) {
                
                case "start":
                    g.drawImage(start, 0, 0, this);
                    break;
                
                case "menu":
                    
                    try{
                    g.drawImage(menu, -800,-800, this);
                    g.drawImage(logo, 510, 32, this);
                    g.drawImage(play,150,550,this);
                    g.drawImage(exit,1000,550,this);
                    }
                    catch(Exception e){ }
                    break;
                    
                case "dead":
                    g.drawImage(deathimg, 0,0,this);
                    g.drawImage(menubutton,60,350, this);
                    g.drawImage(playagain,60,400, this);                   
                    break;

                case "game":
                    g.drawImage(background1, bg1.getBgX(), bg1.getBgY(), this);
                    g.drawImage(background2, bg2.getBgX(), bg2.getBgY(), this);
                    g.drawImage(background2, bg3.getBgX(), bg3.getBgY(), this);
                    g.drawImage(background1, bg4.getBgX(), bg4.getBgY(), this);
                    g.setFont(new Font("Hobo Std",Font.HANGING_BASELINE,50));
                    g.drawString("Time "+second,1100,80);
                    g.setFont(new Font("Hobo Std",Font.HANGING_BASELINE,50));
                    g.drawString("HP "+HP,50,80);
                    paintTiles(g);
                    paintProjectiles(g);
                    paintEnemies(g);
                    g.drawImage(currentSprite, robot.getCenterX() - 61,robot.getCenterY() - 63, this);
                       
                    break;
                    
                
            }
	}
        
        private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}
	}
        
        private void paintEnemies(Graphics g){
            for (Enemy i: Enemy.enemies){
                        if (i.getCenterX()>-50 && i.getCenterX()<1366){
                            if (i.direction == "right")
                                g.drawImage(hanim_r.getImage(), i.getCenterX() - 48, i.getCenterY() - 48, this);
                            else if (i.direction == "left")
                                g.drawImage(hanim_l.getImage(), i.getCenterX() - 48, i.getCenterY() - 48, this);
                        }
                    }
        }
        
        private void paintProjectiles(Graphics g){
                    projectiles = robot.getProjectiles();
                    for (int i = 0; i < projectiles.size(); i++) {
                        Projectile p = (Projectile) projectiles.get(i);
                        
                        if (robot.getDirection() == "right")
                            g.drawImage(bullet_r, p.getX(), p.getY(), this);
                        else if (robot.getDirection() == "left")
                            g.drawImage(bullet_l, p.getX(), p.getY(), this);
                    }   
        }
        
        private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}
        private void loadMap(String filename) throws IOException {
            ArrayList lines = new ArrayList();
            int width = 0;
            int height = 0;

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    reader.close();
                    break;
                }

                if (!line.startsWith("!")) {
                    lines.add(line);
                    width = Math.max(width, line.length());

                }
            }
            height = lines.size();

            for (int j = 0; j < 20; j++) {
                String line = (String) lines.get(j);
                for (int i = 0; i < width; i++) {

                    if (i < line.length()) {
                        char ch = line.charAt(i);
                        if (ch == '*'){
                            Enemy.enemies.add(new Enemy(i*40, j*40-80));
                        }
                        else{
                            Tile t = new Tile(i, j, Character.getNumericValue(ch));
                            tilearray.add(t);
                        }
                    }

                }
            }

    }

        public void keyPressed(KeyEvent e) {
            
                if (State == "start"){
                    if (e.getKeyCode() == KeyEvent.VK_SPACE)
                        State = "menu";
                }         
                if (State=="game"){
                    
                    switch (e.getKeyCode()) {

                        case KeyEvent.VK_ESCAPE:
                                State="menu";
                                break;

                        case KeyEvent.VK_UP:
                                System.out.println("Move up");
                                break;

                        case KeyEvent.VK_DOWN:
                                robot.setDucked( true);
                                break;

                        case KeyEvent.VK_LEFT:
                                robot.moveLeft();
                                break;

                        case KeyEvent.VK_RIGHT:
                                robot.moveRight();
                                break;

                        case KeyEvent.VK_SPACE:
                                robot.jump();
                                break;
                        
                        case KeyEvent.VK_ENTER:
                            State="game";
                            break;

                        case KeyEvent.VK_CONTROL:
                            ctrl_press = true;
                    }
                }
        }
    public void keyReleased(KeyEvent e) {
                
            
            switch (e.getKeyCode()) {
                
		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_DOWN:
                    if (State == "game"){
                        crouchdown_r.currentFrame = 0;
                        crouchdown_l.currentFrame = 0;
			robot.setDucked(false);
                    }
			break;

		case KeyEvent.VK_LEFT:
                        if (State == "game")
                        	robot.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
                        if (State == "game")
        			robot.stopRight();
			break;

		case KeyEvent.VK_SPACE:
                        if (State == "game"){
                            robot.setMovingLeft(false);
                            robot.setMovingRight(false);
                        }
			break;
                
                case KeyEvent.VK_CONTROL:
                    if (State == "game"){
                        if (ctrl_press){
                            robot.shoot();
                        }
                    }
                    break;

		}

	}
        @Override
        public void keyTyped(KeyEvent e) {
            
	}
    @Override
    public void mouseClicked(MouseEvent me) {
      
    }
    
    public void mousePressed(MouseEvent me) {
        
        switch (State){
            case "dead":
                DEATH.mousePress(me);
                break;
            case "menu":
                MENU.mousePress(me);
                break;
        }
    }
    
    public void mouseReleased(MouseEvent me) {

    }
    @Override
    public void mouseEntered(MouseEvent me) {
       
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
        public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}
        
        public static Background getBg3(){
            return bg3;
        }
        
        public static Background getBg4(){
            return bg4;
        }
        
        public static Robot getRobot(){
		return robot;
	}
        
        public static ArrayList getTileArray(){
            return tilearray;
        }
        
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("LOST");
                frame.setSize(1366, 768); 
		frame.setBackground(Color.black);
                frame.add(starter);
                frame.setUndecorated(true);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
                try{
                    starter.init();
                    starter.start();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
    }
}
