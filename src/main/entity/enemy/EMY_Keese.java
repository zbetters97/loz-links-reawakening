package entity.enemy;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;

import java.awt.*;

public class EMY_Keese extends Entity {

    public static final String emyName = "Keese";

    public EMY_Keese(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        worldXStart = this.worldX;
        worldYStart = this.worldY;

        type = type_enemy;
        name = emyName;
        onGround = false;
        capturable = true;

        maxLife = 4;
        life = maxLife;
        speed = 2;
        defaultSpeed = speed;
        animationSpeed = 5;
        attack = 1;
        knockbackPower = 0;

        hitbox = new Rectangle(2, 18, 44, 30);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitboxDefaultWidth = hitbox.width;
        hitboxDefaultHeight = hitbox.height;
    }

    public void getImage() {
        up1 = down1 = left1 = right1 = setup("/enemy/keese_down_1");
        up2 = down2 = left2 = right2 = setup("/enemy/keese_down_2");
    }

    public void setAction() {
        if (!captured) {
            getDirection(25);
        }
    }

    public void attacking() {
        attacking = false;
    }

    public void damageReaction() {
        actionLockCounter = 0;
    }

    public void playHurtSE() {
        gp.playSE(3, 0);
    }

    public void playDeathSE() {
        gp.playSE(3, 2);
    }

    // DROPPED ITEM
    public void checkDrop() {
        super.checkDrop();
        dropItem(new COL_Heart(gp));
    }
}