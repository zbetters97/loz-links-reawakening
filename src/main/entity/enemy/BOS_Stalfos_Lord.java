package entity.enemy;

import application.GamePanel;
import entity.Entity;

import java.awt.*;

public class BOS_Stalfos_Lord extends Entity {

    public static final String emyName = "Stalfos Lord";

    public BOS_Stalfos_Lord(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        worldXStart = this.worldX;
        worldYStart = this.worldY;

        type = type_boss;
        name = emyName;
        sleep = true;
        capturable = true;
        canTarget = false;

        int hbScale = gp.tileSize * 5;
        hitbox = new Rectangle(gp.tileSize, gp.tileSize, hbScale - (gp.tileSize * 2), hbScale - gp.tileSize);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitboxDefaultWidth = hitbox.width;
        hitboxDefaultHeight = hitbox.height;

        swingSpeed1 = 45;
        swingSpeed2 = 80;

        attackbox.width = 170;
        attackbox.height = 170;

        resetValues();
        setDialogue();
    }

    public void getImage() {

        int scale = gp.tileSize * 5;

        if (currentBossPhase == bossPhase_1) {
            up1 = setup("/boss/skeletonlord_up_1", scale, scale);
            up2 = setup("/boss/skeletonlord_up_2", scale, scale);
            down1 = setup("/boss/skeletonlord_down_1", scale, scale);
            down2 = setup("/boss/skeletonlord_down_2", scale, scale);
            left1 = setup("/boss/skeletonlord_left_1", scale, scale);
            left2 = setup("/boss/skeletonlord_left_2", scale, scale);
            right1 = setup("/boss/skeletonlord_right_1", scale, scale);
            right2 = setup("/boss/skeletonlord_right_2", scale, scale);
        }
        else {
            up1 = setup("/boss/skeletonlord_phase2_up_1", scale, scale);
            up2 = setup("/boss/skeletonlord_phase2_up_2", scale, scale);
            down1 = setup("/boss/skeletonlord_phase2_down_1", scale, scale);
            down2 = setup("/boss/skeletonlord_phase2_down_2", scale, scale);
            left1 = setup("/boss/skeletonlord_phase2_left_1", scale, scale);
            left2 = setup("/boss/skeletonlord_phase2_left_2", scale, scale);
            right1 = setup("/boss/skeletonlord_phase2_right_1", scale, scale);
            right2 = setup("/boss/skeletonlord_phase2_right_2", scale, scale);
        }
    }

    public void getAttackImage() {

        int scale = gp.tileSize * 5;

        if (currentBossPhase == bossPhase_1) {
            attackUp1 = setup("/boss/skeletonlord_attack_up_1", scale, scale * 2);
            attackUp2 = setup("/boss/skeletonlord_attack_up_2", scale, scale * 2);
            attackDown1 = setup("/boss/skeletonlord_attack_down_1", scale, scale * 2);
            attackDown2 = setup("/boss/skeletonlord_attack_down_2", scale, scale * 2);
            attackLeft1 = setup("/boss/skeletonlord_attack_left_1", scale * 2, scale);
            attackLeft2 = setup("/boss/skeletonlord_attack_left_2", scale * 2, scale);
            attackRight1 = setup("/boss/skeletonlord_attack_right_1", scale * 2, scale);
            attackRight2 = setup("/boss/skeletonlord_attack_right_2", scale * 2, scale);
        }
        else {
            attackUp1 = setup("/boss/skeletonlord_phase2_attack_up_1", scale, scale * 2);
            attackUp2 = setup("/boss/skeletonlord_phase2_attack_up_2", scale, scale * 2);
            attackDown1 = setup("/boss/skeletonlord_phase2_attack_down_1", scale, scale * 2);
            attackDown2 = setup("/boss/skeletonlord_phase2_attack_down_2", scale, scale * 2);
            attackLeft1 = setup("/boss/skeletonlord_phase2_attack_left_1", scale * 2, scale);
            attackLeft2 = setup("/boss/skeletonlord_phase2_attack_left_2", scale * 2, scale);
            attackRight1 = setup("/boss/skeletonlord_phase2_attack_right_1", scale * 2, scale);
            attackRight2 = setup("/boss/skeletonlord_phase2_attack_right_2", scale * 2, scale);
        }
    }

    public void setDialogue() {
        dialogues[0][0] = "Who dares enter my treasure room?!";
        dialogues[0][1] = "You mere mortal will never be able to\nwield the power of the Blue Heart!";
        dialogues[0][2] = "I forbid you from completing your quest!";
    }

    public void setAction() {

        if (!captured) {
            if (currentBossPhase == 1) {

                // DON'T CHASE PLAYER WHEN ATTACKING
                if (getTileDistance(gp.player) < 10 && !attacking) {
                    approachPlayer(60);
                }
                else if (!attacking) {
                    getDirection(60);
                }

                if (!attacking) {
                    if (isAttacking(60, gp.tileSize * 7, gp.tileSize * 5)) {
                        attacking = true;
                    }
                    speed = defaultSpeed;
                }
                // STOP MOVEMENT WHEN ATTACKING
                else {
                    speed = 0;
                }

                if (life < maxLife / 2) {
                    currentBossPhase = 2;
                    attack++;
                    defaultSpeed++;
                    speed = defaultSpeed;
                    getImage();
                }
            }
            else if (currentBossPhase == 2) {

                // DON'T CHASE PLAYER WHEN ATTACKING
                if (getTileDistance(gp.player) < 10 && !attacking) {
                    approachPlayer(45);
                }
                else if (!attacking) {
                    getDirection(45);
                }

                if (!attacking) {
                    if (isAttacking(45, gp.tileSize * 7, gp.tileSize * 5)) {
                        attacking = true;
                    }
                    speed = defaultSpeed;
                }
                // STOP MOVEMENT WHEN ATTACKING
                else {
                    speed = 0;
                }
            }
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
    }

    public void resetValues() {
        direction = "down";
        maxLife = 50;
        life = maxLife;
        attack = 10;
        knockbackPower = 5;
        speed = 1;
        defaultSpeed = speed;
        animationSpeed = 10;
        currentBossPhase = bossPhase_1;
        sleep = true;
        attacking = false;
        attackCounter = 0;
        getImage();
        getAttackImage();
    }

    public void playAttackSE() {
        gp.playSE(3, 3);
    }

    public void playHurtSE() {
        gp.playSE(3, 4);
    }

    public void playDeathSE() {
        gp.playSE(3, 5);
    }

    // DROPPED ITEM
    public void checkDrop() {
        gp.csManager.scene = gp.csManager.boss_1_defeat;
        gp.gameState = gp.cutsceneState;
    }
}