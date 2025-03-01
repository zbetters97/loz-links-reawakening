package entity.object.object_interactive;

import application.GamePanel;
import entity.Entity;
import entity.object.OBJ_Door_Closed;
import tile.tile_interactive.IT_Plate_Metal;
import tile.tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;

public class OI_Block_Pushable extends Entity {

    public static final String obj_iName = "Pushable Block";
    public int pushCounter = 0;

    public OI_Block_Pushable(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;

        type = type_obstacle_i;
        name = obj_iName;
        direction = "down";
        collision = true;
        hookGrabbable = true;
        speed = 1;
        defaultSpeed = speed;

        hitbox = new Rectangle(1, 1, 46, 46);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitboxDefaultWidth = hitbox.width;
        hitboxDefaultHeight = hitbox.height;
    }

    public void getImage() {
        up1 = down1 = left1 = right1 = setup("/objects_interactive/block_pushable");
        up2 = down2 = left2 = right2 = setup("/objects_interactive/block_pushable");
    }

    public void move(String dir) {

        if (!moving) {

            // INCREASE SPEED TO DETECT COLLISION
            direction = dir;
            speed = gp.tileSize;

            checkCollision();

            if (isCorrectTile() && !collisionOn) {
                playSE();
                gp.player.action = Action.PUSHING;
                moving = true;
                speed = 1;
            }
        }
    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkPlayer(this);
        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntity(this, gp.iTile);
        gp.cChecker.checkEntity(this, gp.obj);
        gp.cChecker.checkEntity(this, gp.obj_i);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.enemy);
    }

    public void update() {

        if (moving) {
            pushCounter++;
            if (pushCounter <= gp.tileSize) {
                push();
            }
            else {
                moving = false;
                pushCounter = 0;
            }
        }
    }

    private void push() {

        // MOVE IN DIRECTION PUSHED
        switch (direction) {
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }

        detectPlate();
        detectPlacement();
    }

    private boolean isCorrectTile() {

        boolean correctTile = false;

        int x = worldX / gp.tileSize;
        int y = worldY / gp.tileSize;

        switch (direction) {
            case "up":
                y--;
                break;
            case "down":
                y++;
                break;
            case "left":
                x--;
                break;
            case "right":
                x++;
                break;
            default:
                return false;
        }

        int tile = gp.tileM.mapTileNum[gp.currentMap][x][y];
        if (tile == gp.tileM.blockTile1 || tile == gp.tileM.blockTile2) {
            correctTile = true;
        }

        return correctTile;
    }

    private void detectPlacement() {

        if (gp.currentMap == 2) {
            if (worldX == 1392 && worldY == 2544) {
                gp.openDoor(26, 54, OBJ_Door_Closed.objName);
            }
        }
    }

    private void detectPlate() {

        // CREATE PLATE LIST
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        for (int i = 0; i < gp.iTile[1].length; i++) {
            if (gp.iTile[gp.currentMap][i] != null &&
                    gp.iTile[gp.currentMap][i].name != null &&
                    gp.iTile[gp.currentMap][i].name.equals(IT_Plate_Metal.itName)) {
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }

        // CREATE BLOCK LIST
        ArrayList<Entity> blockList = new ArrayList<>();
        for (int i = 0; i < gp.obj_i[1].length; i++) {
            if (gp.obj_i[gp.currentMap][i] != null &&
                    gp.obj_i[gp.currentMap][i].name != null &&
                    gp.obj_i[gp.currentMap][i].name.equals(OI_Block_Pushable.obj_iName)) {
                blockList.add(gp.obj_i[gp.currentMap][i]);
            }
        }

        // SCAN PLATES
        for (InteractiveTile interactiveTile : plateList) {

            int xDistance = Math.abs(worldX - interactiveTile.worldX);
            int yDistance = Math.abs(worldY - interactiveTile.worldY);
            int distance = Math.max(xDistance, yDistance);

            // ROCK IS ON PLATE (within 15 px)
            if (distance < 15) {
                if (linkedEntity == null) {
                    interactiveTile.playSE();
                    linkedEntity = interactiveTile;
                }
            }
            // ROCK MOVED OFF PLATE
            else if (linkedEntity == interactiveTile) {
                linkedEntity = null;
            }
        }

        // TRACK HOW MANY PLATES HAVE BOULDERS
        int count = 0;
        for (Entity entity : blockList) {
            if (entity.linkedEntity != null) {
                count++;
            }
        }

        if (gp.currentMap == 2) {
            if (count == 2 && !data.Progress.puzzle_1_1) {
                data.Progress.puzzle_1_1 = true;
                gp.csManager.worldX = 45;
                gp.csManager.worldY = 89;
                gp.csManager.scene = gp.csManager.puzzle_solve;
                gp.gameState = gp.cutsceneState;
            }
        }
    }

    public void playSE() {
        gp.playSE(4, 5);
    }
}