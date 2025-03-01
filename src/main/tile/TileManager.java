package tile;

import application.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TileManager {

    private final GamePanel gp;
    public Tile[] tile;

    public int blockTile1 = 38;
    public int blockTile2 = 49;

    public int waterTile = 4;
    public int oceanTile1 = 19;
    public int oceanTile2 = 39;
    public int spikeTile = 77;

    private int waterCounter = 0;
    private final int waterCounterMax = 45;
    private int waterNum = 1;

    // [MAP NUMBER][ROW][COL]
    public int[][][] mapTileNum;

    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();
    ArrayList<String> waterStatus = new ArrayList<>();
    ArrayList<String> pitStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        loadTileData();

        gp.maxWorldCol = 100;
        gp.maxWorldRow = 100;
        gp.worldWidth = gp.tileSize * 100;
        gp.worldHeight = gp.tileSize * 100;

        mapTileNum = new int[gp.maxMap][100][100];
    }

    public void loadMap() {

        InputStream inputStream = getClass().getResourceAsStream("/maps/" + gp.mapFiles[gp.currentMap]);
        int mapLength = 0;

        try {
            Scanner sc = new Scanner(Objects.requireNonNull(inputStream));

            for (int row = 0; sc.hasNextLine(); row++) {

                String line = sc.nextLine();
                String[] numbers = line.split(" ");
                mapLength = numbers.length;

                for (int col = 0; col < numbers.length; col++) {
                    int tileNum = Integer.parseInt(numbers[col]);
                    mapTileNum[gp.currentMap][col][row] = tileNum;
                }
            }

            sc.close();

            // ASSIGN NEW WORLD DIMENSIONS
            gp.maxWorldCol = mapLength;
            gp.maxWorldRow = mapLength;
            gp.worldWidth = gp.tileSize * mapLength;
            gp.worldHeight = gp.tileSize * mapLength;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTileData() {

        // IMPORT TILE DATA
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

        // ADD TILE DATA TO ARRAYS
        try {
            String line;
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
                waterStatus.add(br.readLine());
                pitStatus.add(br.readLine());
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // ASSIGN TILE COLLISION
        tile = new Tile[fileNames.size()];
        getTileImage();
    }

    public void getTileImage() {

        // loop through all tile data in fileNames
        for (int i = 0; i < fileNames.size(); i++) {

            String fileName;
            boolean collision, water, pit;

            // assign each name to fileName
            fileName = fileNames.get(i);

            // assign tile status
            collision = collisionStatus.get(i).equals("true");
            water = waterStatus.get(i).equals("true");
            pit = pitStatus.get(i).equals("true");

            setup(i, fileName, collision, water, pit);
        }
    }

    public void setup(int index, String imageName, boolean collision, boolean water, boolean pit) {

        try {
            tile[index] = new Tile();

            tile[index].image = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/tiles/" + imageName)
            ));
            tile[index].image = GamePanel.utility.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);

            tile[index].collision = collision;
            tile[index].water = water;
            tile[index].pit = pit;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;
        boolean offCenter = false;

        waterCounter++;
        if (waterCounter >= waterCounterMax) {
            waterCounter = 0;
            if (waterNum == 1) {
                waterNum = 2;
            }
            else {
                waterNum = 1;
            }
        }

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            // TILE NUMBERS FROM MAP TXT
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            // WORLD X,Y
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // PLAYER SCREEN POSITION X,Y OFFSET TO CENTER
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // STOP CAMERA MOVEMENT AT WORLD BOUNDARY
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
                offCenter = true;
            }
            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
                offCenter = true;
            }

            // FROM PLAYER TO RIGHT-EDGE OF SCREEN
            int rightOffset = gp.screenWidth - gp.player.screenX;

            // FROM PLAYER TO RIGHT-EDGE OF WORLD
            if (rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
                offCenter = true;
            }

            // FROM PLAYER TO BOTTOM-EDGE OF SCREEN
            int bottomOffSet = gp.screenHeight - gp.player.screenY;

            // FROM PLAYER TO BOTTOM-EDGE OF WORLD
            if (bottomOffSet > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
                offCenter = true;
            }

            // DRAW TILES WITHIN PLAYER BOUNDARY
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                if (tileNum == oceanTile1) {
                    if (waterNum == 2) {
                        tileNum = oceanTile2;
                    }
                }

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            else if (offCenter) {
                if (tileNum == oceanTile1) {
                    if (waterNum == 2) {
                        tileNum = oceanTile2;
                    }
                }
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            // TO NEXT COLUMN
            worldCol++;

            // TO NEXT ROW
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}