package entity.enemy;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Green;

import java.awt.*;
import java.util.Random;

public class EMY_Bubble extends Entity {

    public static final String emyName = "Bubble";

    public EMY_Bubble(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        worldXStart = this.worldX;
        worldYStart = this.worldY;
        direction = "upleft";

        type = type_enemy;
        name = emyName;
        onGround = false;
        canTarget = false;

        speed = 4;
        defaultSpeed = speed;
        animationSpeed = 5;
        maxLife = 8;
        life = maxLife;
        attack = 2;
        knockbackPower = 0;

        hitbox = new Rectangle(8, 8, 32, 32);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitboxDefaultWidth = hitbox.width;
        hitboxDefaultHeight = hitbox.height;
    }

    public void getImage() {
        up1 = down1 = left1 = right1 = setup("/enemy/bubble_down_1");
        up2 = down2 = left2 = right2 = setup("/enemy/bubble_down_1");
    }

    public void update() {
        move();
        manageValues();
    }

    public void move() {

        gp.cChecker.detectBounce(this);
        gp.cChecker.detectBounce(this, gp.iTile);
        gp.cChecker.detectBounce(this, gp.obj);
        gp.cChecker.detectBounce(this, gp.obj_i);
        gp.cChecker.detectBounce(this, gp.npc);

        switch (direction) {
            case "upleft":
                worldY -= speed - 1;
                worldX -= speed - 1;
                break;
            case "upright":
                worldY -= speed - 1;
                worldX += speed - 1;
                break;
            case "downleft":
                worldY += speed - 1;
                worldX -= speed - 1;
                break;
            case "downright":
                worldY += speed;
                worldX += speed - 1;
                break;
        }
    }

    public void playHurtSE() {
        gp.playSE(3, 0);
    }

    public void playDeathSE() {
        gp.playSE(3, 2);
    }

    public void checkDrop() {
        super.checkDrop();

        int i = new Random().nextInt(100) + 1;

        if (i < 50) {
            dropItem(new COL_Heart(gp));
        }
        else {
            dropItem(new COL_Rupee_Green(gp));
        }
    }
}