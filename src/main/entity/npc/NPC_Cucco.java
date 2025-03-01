package entity.npc;

import application.GamePanel;
import entity.Entity;

import java.awt.*;

public class NPC_Cucco extends Entity {

    public static final String npcName = "Cucco";
    private final int animationPanicSpeed = 5;
    private final int panicSpeed = 2;
    private int squawkTimer = 0;
    private int squawkTimerMax = 120;
    private final int squawkTimerPanicMax = 30;
    private boolean hurt = false;
    private int hurtTimer = 0;

    public NPC_Cucco(GamePanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        worldXStart = this.worldX;
        worldYStart = this.worldY;

        capturable = true;
        collision = false;
        grabbable = true;

        type = type_npc;
        name = npcName;
        direction = "down";
        maxLife = 99;
        life = maxLife;
        knockbackPower = 1;
        defaultSpeed = 1;
        speed = defaultSpeed;
        defaultAnimationSpeed = 10;
        animationSpeed = defaultAnimationSpeed;

        hitbox = new Rectangle(8, 16, 32, 28);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitboxDefaultWidth = hitbox.width;
        hitboxDefaultHeight = hitbox.height;
    }

    public void getImage() {
        if (aggressive) {
            up1 = setup("/npc/cucco_up_1");
            up2 = setup("/npc/cucco_up_2");
            down1 = setup("/npc/cucco_attack_down_1");
            down2 = setup("/npc/cucco_attack_down_2");
            left1 = setup("/npc/cucco_attack_left_1");
            left2 = setup("/npc/cucco_attack_left_2");
            right1 = setup("/npc/cucco_attack_right_1");
            right2 = setup("/npc/cucco_attack_right_2");
        }
        else {
            up1 = setup("/npc/cucco_up_1");
            up2 = setup("/npc/cucco_up_2");
            down1 = setup("/npc/cucco_down_1");
            down2 = setup("/npc/cucco_down_2");
            left1 = setup("/npc/cucco_left_1");
            left2 = setup("/npc/cucco_left_2");
            right1 = setup("/npc/cucco_right_1");
            right2 = setup("/npc/cucco_right_2");
        }
    }

    public void update() {
        super.update();

        if (grabbed) {
            grabbed();
        }
        else if (thrown) {
            thrown();
        }
    }

    private void grabbed() {
        worldX = gp.player.worldX;
        worldY = gp.player.worldY - gp.tileSize + 10;
        collisionOn = false;
        xT = worldX;
        yT = worldY;
    }

    private void thrown() {
        speed = 0;
        if (tossEntity()) {

            gp.cChecker.checkHazard(this, false);

            thrown = false;
            hurt = true;
            throwCounter = 0;
            tTime = 0;

            gp.player.action = Action.IDLE;
            gp.player.grabbedObject = null;
            gp.player.throwCounter = 0;
            gp.player.throwNum = 1;
        }
    }

    public void setAction() {

        if (!thrown && !grabbed && !hurt && !aggressive) {
            speed = defaultSpeed;
            animationSpeed = defaultAnimationSpeed;

            getDirection(60);
        }
        else if (grabbed) {
            animationSpeed = animationPanicSpeed;
            squawkTimerMax = squawkTimerPanicMax;
            actionLockCounter = 0;
            hurt = false;

            direction = getPlayerDirection();
        }
        else if (aggressive) {
            attack = 2;

            speed = panicSpeed;
            animationSpeed = animationPanicSpeed;
            squawkTimerMax = squawkTimerPanicMax;
            grabbable = false;

            attackPlayer();

            hurtTimer++;
            if (hurtTimer >= 9999) {
                resetValues();
            }
        }
        else if (hurt) {

            speed = panicSpeed;
            animationSpeed = animationPanicSpeed;
            squawkTimerMax = squawkTimerPanicMax;
            grabbable = false;

            getDirection(15);

            hurtTimer++;
            if (hurtTimer >= 120) {
                grabbable = true;
                hurt = false;
                hurtTimer = 0;
            }
        }

        // PLAY SQUAWK IF IN FRAME
        if (inFrame()) {
            squawkTimer++;
            if (squawkTimer >= squawkTimerMax) {
                playSE();
                squawkTimer = 0;
                getSquawkTimerMax();
            }
        }

        if (0 >= life) {
            resetValues();
            aggressive = true;
            grabbable = false;
            getImage();
        }
    }

    // RANDOM SQUAWK INTERVALS (1, 2, 3, or 4 seconds)
    private void getSquawkTimerMax() {
        int squawk = 1 + (int) (Math.random() * 3);
        if (squawk == 1) {
            squawkTimerMax = 60;
        }
        else if (squawk == 2) {
            squawkTimerMax = 120;
        }
        else if (squawk == 3) {
            squawkTimerMax = 180;
        }
        else {
            squawkTimerMax = 240;
        }
    }

    private void attackPlayer() {

        isOffPath(gp.player, 6);
        if (onPath && playerWithinBounds()) {
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
        else {

            // SEARCH FOR PLAYER IF WITHIN BOUNDS
            if (playerWithinBounds()) {
                isOnPath(gp.player, 10);
            }
            else {
                onPath = false;
            }
        }
    }

    public void damageReaction() {
        playSE();
        grabbable = false;
        hurt = true;
        hurtTimer = 0;
    }

    public void attacking() {
        attacking = false;
    }

    public void resetValues() {

        // CANNOT DIE, RESET TO STARTING POINT
        if (thrown || grabbed || captured) {
            worldX = worldXStart;
            worldY = worldYStart;
        }

        alive = true;
        dying = false;
        thrown = false;
        hurt = false;
        aggressive = false;
        grabbable = true;
        grabbed = false;
        captured = false;

        life = maxLife;
        speed = defaultSpeed;
        attack = 0;
        hurtTimer = 0;
        actionLockCounter = 0;
        throwCounter = 0;
        tTime = 0;

        getImage();
    }

    public void playSE() {
        gp.playSE(8, 0);
    }

    public void playHurtSE() {
        gp.playSE(8, 0);
    }
}