package tile.tile_interactive;

import application.GamePanel;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends Entity {

    public InteractiveTile(GamePanel gp) {
        super(gp);
    }

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
    }

    public void update() {

        if (grabbed) {
            grabbed();
        }
        else if (thrown) {
            thrown();
        }

        // SHIELD AFTER HIT
        if (invincible) {
            invincibleCounter++;

            // 1 SECOND REFRESH TIME
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void setLoot(Entity loot) {
        this.loot = loot;
    }

    private void grabbed() {
        worldX = gp.player.worldX;
        worldY = gp.player.worldY - gp.tileSize + 8;
        collision = false;
        xT = worldX;
        yT = worldY;
    }

    private void thrown() {
        if (tossEntity()) {

            gp.cChecker.checkHazard(this, false);

            if (alive) {
                breakTile();
            }
        }
    }

    public boolean correctItem(Entity entity) {
        boolean correctItem = false;
        return correctItem;
    }

    public void breakTile() {
        playSE();
        thrown = false;

        Entity enemy = getEnemy(this);
        if (enemy != null) {
            gp.player.damageEnemy(enemy, this, 2, 2);
        }

        int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
        gp.player.damageInteractiveTile(iTileIndex, this);

        checkDrop();
        generateParticle(this);

        throwCounter = 0;
        tTime = 0;
        alive = false;

        gp.player.action = Action.IDLE;
        gp.player.grabbedObject = null;
        gp.player.throwCounter = 0;
        gp.player.throwNum = 1;
    }

    public void resetValues() {
        throwCounter = 0;
        tTime = 0;
        alive = false;

        gp.player.action = Action.IDLE;
        gp.player.grabbedObject = null;
        gp.player.throwCounter = 0;
        gp.player.throwNum = 1;
    }

    public void playSE() {

    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        boolean offCenter = false;

        if (getScreenX() > worldX) {
            screenX = worldX;
            offCenter = true;
        }
        if (getScreenY() > worldY) {
            screenY = worldY;
            offCenter = true;
        }

        // FROM PLAYER TO RIGHT-EDGE OF SCREEN
        int rightOffset = gp.screenWidth - getScreenX();

        // FROM PLAYER TO RIGHT-EDGE OF WORLD
        if (rightOffset > gp.worldWidth - worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
            offCenter = true;
        }

        // FROM PLAYER TO BOTTOM-EDGE OF SCREEN
        int bottomOffSet = gp.screenHeight - getScreenY();

        // FROM PLAYER TO BOTTOM-EDGE OF WORLD
        if (bottomOffSet > gp.worldHeight - worldY) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
            offCenter = true;
        }

        if (thrown) {
            g2.setColor(Color.BLACK);
            g2.fillOval(screenX + 10, screenY + 40, 30, 10);
        }

        image = switch (direction) {
            case "up", "upleft", "upright" -> up1;
            case "down", "downleft", "downright" -> down1;
            case "left" -> left1;
            case "right" -> right1;
            default -> null;
        };

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            if (thrown) {
                g2.setColor(Color.BLACK);
                switch (direction) {
                    case "up", "upleft", "upright",
                         "down", "downleft", "downright":
                        g2.fillOval(screenX + 5, screenY + 70 - throwCounter, 38, 10);
                        break;
                    case "left", "right":
                        g2.fillOval(screenX + 5, tWorldY - gp.player.worldY + gp.player.screenY + 40, 38, 10);
                        break;
                }
            }

            g2.drawImage(image, screenX, screenY, null);
        }
        else if (offCenter) {
            g2.drawImage(image, screenX, screenY, null);
        }

        if (gp.keyH.debug) {
            g2.setColor(Color.RED);
            g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
        }
    }
}