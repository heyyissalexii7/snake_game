
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener{
    
    static int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNIT = (SCREEN_WIDTH * SCREEN_HEIGHT)/ UNIT_SIZE;
    static final int DELAY = 75;
    final int x [] = new int [GAME_UNIT];
    final int y [] = new int [GAME_UNIT];
    int bodypart = 7;
    int foodEaten;
    int foodX;
    int foodY;
    char direction = 'p';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() 
    {
        random = new Random();
        this.setPreferredSize(new Dimension (SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    
    public void startGame() 
    {
        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g) 
    {
        
        if (running) {
            
        
        /*
        for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
            
            g.drawLine(i* UNIT_SIZE, 0, i* UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            
        }*/
        
        g.setColor(Color.red);
        g.fillOval(foodX,foodY,UNIT_SIZE,UNIT_SIZE);
        
        for (int i = 0; i < bodypart; i++) 
        {
            if (i == 0) {
                g.setColor(Color.yellow);
                g.fillRect(i, i, UNIT_SIZE, UNIT_SIZE);
            }
            else{
                g.setColor(new Color(40, 180, 0));
                g.setColor(Color.blue);
                g.fillRect(i, i, UNIT_SIZE, UNIT_SIZE);
            }          
        }
        
        g.setColor(Color.red);
        g.setFont(new Font("Helvetica", Font.BOLD,30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score" + foodEaten, (SCREEN_WIDTH = metrics1.stringWidth("Score" + foodEaten)) / 2, g.getFont().getSize());
        
        }
         else{
                  GameOver(g);
                    }
    }
    
    
    public void newFood(){
        
        foodX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE ;
        foodY = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE ;
    }
    
    public void move(){
        
        for (int i = 0; i < bodypart; i++) {
            x [i] = x[i-1];
            y [i] = y[i+1];
        }
        switch (direction){
            case 'U' -> y[0]= y[0] - UNIT_SIZE;
            case 'D' -> y[0]= y[0] + UNIT_SIZE;
            case 'L' -> x[0]= x[0] - UNIT_SIZE;
            case 'R' -> x[0]= x[0] + UNIT_SIZE;
        }
        
    }
    
    public void checkFood(){
        
        if ((x [0] == foodX) && (y [0] ==foodY)) {
            bodypart++;
            foodEaten++;
            newFood();
        }
        
    }
    
    public void checkCollisions(){
        
        for (int i = bodypart; i >0; i--) {
            if ((x [0] ==x[i] && y[0] == y[i])) {
                running = false;
            }
            
        }
        
        if (x [0] < 0) {
            running =false;
        }
        
        if (x [0] > SCREEN_WIDTH) {
            running = false;
        }
        
        if (y [0] <0) {
            running = false;
        }
        
        if (y [0] > SCREEN_WIDTH) {
            running =false;
        }
        
        if (running) {
            timer.stop();
        }
        
    }
    
    public void GameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Helvetica", Font.BOLD,30));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score" + foodEaten, (SCREEN_WIDTH = metrics1.stringWidth("Score" + foodEaten)) / 2, g.getFont().getSize());
        
        
        g.setColor(Color.red);
        g.setFont(new Font("Helvetica", Font.BOLD,30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH = metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         
        if (running) {
            move();
            checkFood();
            checkCollisions();
            
        }
        repaint();
        
    }
    
    public class MyKeyAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) 
        {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }
                
                  case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }
                  
                    case KeyEvent.VK_UP -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }
                    
                    case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
                
            }
        }
        
    }
    
    
    
}
