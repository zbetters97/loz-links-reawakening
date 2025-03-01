package entity.enemy;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Fireball;

import java.util.Random;

public class EMY_Zora extends Entity {

    public static final String emyName = "Octorok";

    public EMY_Zora(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        worldXStart = this.worldX;
        worldYStart = this.worldY;
        direction = "down";

        type = type_enemy;
        name = emyName;
        canSwim = true;

        maxLife = 10;
        life = maxLife;
        speed = 0;
        defaultSpeed = speed;
        animationSpeed = 12;
        attack = 1;
        knockbackPower = 0;

        projectile = new PRJ_Fireball(gp);
    }

    public void getImage() {
        up1 = down1 = left1 = right1 = setup("/enemy/zora_down_1");
        up2 = down2 = left2 = right2 = setup("/enemy/zora_down_2");
    }

    public void getAttackImage() {
        attackUp1 = attackDown1 = attackLeft1 = attackRight1 = setup("/enemy/zora_attack_down_1");
        attackUp2 = attackDown2 = attackLeft2 = attackRight2 = setup("/enemy/zora_attack_down_1");
    }

    // UPDATER
    public void update() {

        if (sleep) {
            return;
        }
        if (knockback) {
            knockbackEntity();
            manageValues();
            return;
        }
        if (stunned) {
            manageValues();
            return;
        }

        setAction();
        move();
        manageValues();
    }

    public void setAction() {
        if (onPath) {
            isOffPath(gp.player, 7);
            approachPlayer(10);
        }
        else {
            isOnPath(gp.player, 6);
        }
    }

    public void move() {
        if (onPath) {
            spriteNum = 2;

            if (!attacking) {
                prepareAttack(100);
            }
            else {
                spriteCounter++;
                if (spriteCounter == 30) {
                    useProjectile();
                }
                else if (spriteCounter >= 60) {
                    attacking = false;
                    spriteCounter = 0;
                }
            }
        }
        else {
            attacking = false;
            spriteNum = 1;
            spriteCounter = 0;
        }
    }

    private void prepareAttack(int rate) {
        int i = new Random().nextInt(rate);
        if (i == 0 && !projectile.alive && shotAvailableCounter == 30) {
            attacking = true;
        }
    }

    public void useProjectile() {
        if (!projectile.alive && shotAvailableCounter == 30) {

            projectile.set(worldX, worldY, direction, true, this);
            addProjectile(projectile);

            shotAvailableCounter = 0;

            projectile.playSE();
        }
    }

    public void damageReaction() {
        onPath = true;
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
    }
}