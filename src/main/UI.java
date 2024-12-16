package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCount = 0;
    public boolean gameFinished = false;

    double playTIme;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp ){
        this.gp = gp;

        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        if (gameFinished){
            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();


            x = gp.screenWidth/2-textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text,x,y);

            g2.setFont(new Font("Arial",Font.BOLD, 80));
            g2.setColor(Color.yellow);
            text = "Congratulations";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2-textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*3);
            g2.drawString(text,x,y);

            text = "You're time:"+ dFormat.format(playTIme)+"!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2-textLength/2;
            y = gp.screenHeight/2  + (gp.tileSize*5);
            g2.drawString(text,x,y);

            gp.gameThread = null;

        }else {
            g2.setFont(new Font("Arial",Font.PLAIN, 40));
            g2.setColor(Color.white);
            g2.drawImage(keyImage,gp.tileSize/2,gp.tileSize/2,gp.tileSize,gp.tileSize,null);
            g2.drawString("X"+ gp.player.hasKey,74,65);

            playTIme += (double) 1/60;
            g2.drawString("Time:"+dFormat.format(playTIme),gp.tileSize*11,65);

            if (messageOn){
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gp.tileSize/2,gp.tileSize*5);

                messageCount++;

                if (messageCount > 120){
                    messageCount = 0;
                    messageOn = false;
        }

            }
        }

    }

}
