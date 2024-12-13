package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[] [];
    public TileManager(GamePanel gp){
        this.gp = gp;

        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWordCol][gp.maxWordRow];

        getTIleImage();
        loadMap("/maps/world01.txt");
    }

    public void getTIleImage(){
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/wall.png"));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water.png"));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/earth.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tree.png"));
            tiles[4].collision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sand.png"));
        }
        catch (IOException e ){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWordCol && row < gp.maxWordRow){
                String line = br.readLine();
                while (col < gp.maxWordCol ){
                    String numbers[] = line.split(" ");

                    int num  = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;

                }
                if (col == gp.maxWordCol){
                    col = 0;
                    row++;
                }

            }
            br.close();
        } catch (Exception e) {

        }
    }
    public void draw(Graphics2D graphics2D){
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWordCol && worldRow < gp.maxWordRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize< gp.player.worldX + gp.player.screenX && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize< gp.player.worldY + gp.player.screenY){
                graphics2D.drawImage(tiles[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            }


            worldCol++;

            if (worldCol == gp.maxWordCol){
                worldCol = 0;
                worldRow++;
            }
        }

    }
}
