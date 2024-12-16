package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    public final  int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD SETTINGS
    public final int maxWordCol = 50;
    public final int maxWordRow = 50;
    public final int worldWidth = tileSize * maxWordCol;
    public final int worldHeight = tileSize * maxScreenRow;

    int FPS = 60;

    TileManager tileManager = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();
    Sound music = new Sound();



    public CollisionChecker cChecker = new CollisionChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    public UI ui = new UI(this);

    public Player player = new Player(this,keyH);

    public SuperObject obj[] = new SuperObject[10];

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }
    public void setupGame(){
        aSetter.setObject();

        playMusic(0);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTIme = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta+=(currentTime - lastTIme) / drawInterval;
            timer += (currentTime - lastTIme);
            lastTIme = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        for (int i = 0; i < obj.length; i++){
            if (obj[i] != null){
                obj[i].draw(g2,this);
            }
        }

        player.draw(g2);

        ui.draw(g2);

        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();

    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
