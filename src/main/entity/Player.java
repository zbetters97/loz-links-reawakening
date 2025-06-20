package entity;

/**
 * IMPORTS
 **/

import application.GamePanel;
import data.Progress;
import entity.collectable.COL_Fairy;
import entity.enemy.EMY_Beetle;
import entity.enemy.EMY_Goblin_Combat_Shield;
import entity.equipment.EQP_Flippers;
import entity.equipment.EQP_Shield;
import entity.equipment.EQP_Sword_Master;
import entity.equipment.EQP_Sword_Old;
import entity.item.*;
import entity.npc.NPC_Cucco;
import entity.projectile.PRJ_Bomb;
import entity.projectile.PRJ_Boomerang;
import entity.projectile.Projectile;
import tile.tile_interactive.IT_Switch;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

/**
 * PLAYER CLASS
 **/
public class Player extends Entity {

    /**
     * PLAYER VARIABLES
     **/

    // POSITIONING
    public int screenX;
    public int screenY;
    private int tempScreenX;
    private int tempScreenY;
    public int defaultWorldX;
    public int defaultWorldY;

    // INVENTORY
    public ArrayList<Entity> inventory_item = new ArrayList<>();
    public int itemIndex = 0;
    public int walletSize;
    public int keys = 0;
    public int boss_key = 0;

    // MISC
    private int previousGrunt = 0;
    private int previousHurt = 0;
    private int previousSwing = 2;
    public boolean playing = false;
    public ArrayList<String> music_notes = new ArrayList<>();

    // COUNTERS
    public int damageNum = 1, guardNum = 1, aimNum = 1, rollNum = 1, pullNum = 1, pushNum = 1,
            throwNum = 1, digNum = 1, jumpNum = 1, playNum = 1, soarNum = 1, rodNum = 1;

    public int damageCounter = 0, lowHPCounter = 0, guardCounter = 0, rollCounter = 0,
            playCounter = 0, pullCounter = 0, pushCounter = 0, throwCounter = 0, digCounter = 0,
            jumpCounter = 0, soarCounter = 0, rodCounter = 0, diveCounter = 0;

    // IMAGES
    public BufferedImage
            swimUp1, swimUp2, swimDown1, swimDown2,
            swimLeft1, swimLeft2, swimRight1, swimRight2,

    aimUp1, aimUp2, aimDown1, aimDown2, aimLeft1, aimLeft2, aimRight1, aimRight2,
            spinUp1, spinUp2, spinDown1, spinDown2, spinLeft1, spinLeft2, spinRight1, spinRight2,

    rollUp1, rollUp2, rollUp3, rollUp4, rollDown1, rollDown2, rollDown3, rollDown4,
            rollLeft1, rollLeft2, rollLeft3, rollLeft4, rollRight1, rollRight2, rollRight3, rollRight4,

    grabUp1, grabUp2, grabUp3, grabDown1, grabDown2, grabDown3,
            grabLeft1, grabLeft2, grabLeft3, grabRight1, grabRight2, grabRight3,
            carryUp1, carryUp2, carryDown1, carryDown2, carryLeft1, carryLeft2, carryRight1, carryRight2,
            throwUp1, throwUp2, throwDown1, throwDown2, throwLeft1, throwLeft2, throwRight1, throwRight2,

    digUp1, digUp2, digDown1, digDown2, digLeft1, digLeft2, digRight1, digRight2,

    hookUp1, hookDown1, hookLeft1, hookRight1,

    jumpUp1, jumpUp2, jumpUp3, jumpDown1, jumpDown2, jumpDown3,
            jumpLeft1, jumpLeft2, jumpLeft3, jumpRight1, jumpRight2, jumpRight3,
            soarUp1, soarDown1, soarLeft1, soarRight1,

    rodUp1, rodUp2, rodUp3, rodDown1, rodDown2, rodDown3,
            rodLeft1, rodLeft2, rodLeft3, rodRight1, rodRight2, rodRight3,

    sit, sing, itemGet_1, itemGet_2, play1, play2, play3, play4,
            drown, fall1, fall2, fall3, die1, die2, die3, die4, die5;

/** END PLAYER VARIABLES **/


    /**
     * PLAYER CONSTRUCTOR
     **/

    public Player(GamePanel gp) {
        super(gp);

        // PLAYER POSITION LOCKED TO CENTER
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // HITBOX (x, y, width, height)
        hitbox = new Rectangle(8, 12, 32, 34);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitboxDefaultWidth = hitbox.width;
        hitboxDefaultHeight = hitbox.height;

        attackbox.width = 32;
        attackbox.height = 32;

        name = "LINK";
    }

/** END PLAYER CONSTRUCTOR **/

    /**
     * DEFAULT HANDLERS
     **/

    // DEFAULT VALUES
    public void setDefaultValues() {

        action = Action.IDLE;

        onGround = true;
        canSwim = false;
        grabbedObject = null;
        capturedTarget = null;

        speed = 3;
        defaultSpeed = speed;
        animationSpeed = 10;

        // PLAYER ATTRIBUTES
        maxLife = 20;
        life = maxLife;
        walletSize = 99;
        rupees = 0;

        maxArrows = 10;
        arrows = maxArrows;
        maxBombs = 10;
        bombs = maxBombs;
        shotAvailableCounter = 30;


        currentWeapon = null;
        currentShield = new EQP_Shield(gp);
        /*
        currentWeapon = new EQP_Sword_Old(gp);
        attack = getAttack();

        inventory_item.add(new ITM_Harp(gp));
        inventory_item.add(new ITM_Shovel(gp));
        inventory_item.add(new ITM_Boomerang(gp));
        inventory_item.add(new ITM_Bomb(gp));
        inventory_item.add(new ITM_Feather(gp));
        inventory_item.add(new ITM_Bow(gp));
        inventory_item.add(new ITM_Hookshot(gp));
        inventory_item.add(new ITM_Cape(gp));
        inventory_item.add(new ITM_Rod(gp));

         */

        setDefaultPosition();
        setDialogue();

        getAttackImage();
        getSpinImage();
        getGuardImage();
        getAimImage();
        getRollImage();
        getSwimImage();
        getGrabImage();
        getCarryImage();
        getThrowImage();
        getDigImage();
        getHookImage();
        getJumpImage();
        getSoarImage();
        getRodImage();
        getMiscImage();
    }

    public void setDefaultPosition() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        defaultWorldX = worldX;
        defaultWorldY = worldY;
        safeWorldX = defaultWorldX;
        safeWorldY = defaultWorldY;

        gp.currentMap = 0;
        gp.currentArea = gp.outside;
    /*
		worldX = gp.tileSize * 40;
		worldY = gp.tileSize * 92;		
		gp.currentArea = gp.dungeon;		
		gp.currentMap = 2;		
		direction = "up";	
	*/
    }

    public void restoreStatus() {
        alive = true;
        canMove = true;
        currentItem = null;
        life = maxLife;
        speed = defaultSpeed;
        Progress.canSave = true;

        resetValues();
    }

    public void resetValues() {

        action = Action.IDLE;
        gp.keyH.aPressed = false;
        gp.keyH.bPressed = false;
        gp.keyH.rPressed = false;
        onGround = true;
        knockback = false;
        invincible = false;
        transparent = false;
        attacking = false;
        spinning = false;
        lockon = false;
        playing = false;

        attackNum = 1;
        attackCounter = 0;
        actionLockCounter = 0;
        charge = 0;
        spinCharge = 0;
        spinNum = 0;
        invincibleCounter = 0;
        damageNum = 1;
        damageCounter = 0;
        lowHPCounter = 0;
        guardNum = 1;
        guardCounter = 0;
        aimNum = 1;
        rollNum = 1;
        rollCounter = 0;
        playNum = 1;
        playCounter = 0;
        pullNum = 1;
        pullCounter = 0;
        pushNum = 1;
        pushCounter = 0;
        throwNum = 1;
        throwCounter = 0;
        digNum = 1;
        digCounter = 0;
        jumpNum = 1;
        jumpCounter = 0;
        soarNum = 1;
        soarCounter = 0;
        rodNum = 1;
        rodCounter = 0;

        if (grabbedObject != null) {
            grabbedObject.resetValues();
            grabbedObject = null;
        }

        if (capturedTarget != null) {
            capturedTarget.captured = false;
            capturedTarget = null;
        }
    }

    // DIALOGUE
    public void setDialogue() {
        dialogues[0][0] = "\"I need to find a sword! But where?...\"";
        dialogues[1][0] = "\"I need to find an item! But where?...\"";
        dialogues[2][0] = "\"I should equip an item first...\"";
    }

    // ATTACK
    public int getAttack() {
        if (currentWeapon == null) {
            return 1;
        }
        else {
            attackbox = currentWeapon.attackbox;
            swingSpeed1 = currentWeapon.swingSpeed1;
            swingSpeed2 = currentWeapon.swingSpeed2;
            swingSpeed3 = currentWeapon.swingSpeed3;
            return currentWeapon.attackValue;
        }
    }

    // PLAYER IMAGES
    public void getImage() {
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");
    }

    public void getAttackImage() {
        if (currentWeapon != null && currentWeapon.name.equals(EQP_Sword_Old.eqpName)) {
            attackUp1 = setup("/player/boy_attack_kokiri_up_1", gp.tileSize * 2, gp.tileSize);
            attackUp2 = setup("/player/boy_attack_kokiri_up_2", gp.tileSize * 2, gp.tileSize * 2);
            attackUp3 = setup("/player/boy_attack_kokiri_up_3", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_kokiri_down_1", gp.tileSize * 2, gp.tileSize);
            attackDown2 = setup("/player/boy_attack_kokiri_down_2", gp.tileSize * 2, gp.tileSize * 2);
            attackDown3 = setup("/player/boy_attack_kokiri_down_3", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_kokiri_left_1", gp.tileSize, gp.tileSize * 2);
            attackLeft2 = setup("/player/boy_attack_kokiri_left_2", gp.tileSize * 2, gp.tileSize * 2);
            attackLeft3 = setup("/player/boy_attack_kokiri_left_3", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_kokiri_right_1", gp.tileSize, gp.tileSize * 2);
            attackRight2 = setup("/player/boy_attack_kokiri_right_2", gp.tileSize * 2, gp.tileSize * 2);
            attackRight3 = setup("/player/boy_attack_kokiri_right_3", gp.tileSize * 2, gp.tileSize);
        }
        else {
            attackUp1 = setup("/player/boy_attack_master_up_1", gp.tileSize * 2, gp.tileSize);
            attackUp2 = setup("/player/boy_attack_master_up_2", gp.tileSize * 2, gp.tileSize * 2);
            attackUp3 = setup("/player/boy_attack_master_up_3", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_master_down_1", gp.tileSize * 2, gp.tileSize);
            attackDown2 = setup("/player/boy_attack_master_down_2", gp.tileSize * 2, gp.tileSize * 2);
            attackDown3 = setup("/player/boy_attack_master_down_3", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_master_left_1", gp.tileSize, gp.tileSize * 2);
            attackLeft2 = setup("/player/boy_attack_master_left_2", gp.tileSize * 2, gp.tileSize * 2);
            attackLeft3 = setup("/player/boy_attack_master_left_3", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_master_right_1", gp.tileSize, gp.tileSize * 2);
            attackRight2 = setup("/player/boy_attack_master_right_2", gp.tileSize * 2, gp.tileSize * 2);
            attackRight3 = setup("/player/boy_attack_master_right_3", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void getSpinImage() {
        if (currentWeapon != null && currentWeapon.name.equals(EQP_Sword_Old.eqpName)) {
            spinUp1 = setup("/player/boy_spin_old_up_1", gp.tileSize * 2, gp.tileSize * 2);
            spinUp2 = setup("/player/boy_spin_old_up_2", gp.tileSize, gp.tileSize * 2);
            spinDown1 = setup("/player/boy_spin_old_down_1", gp.tileSize * 2, gp.tileSize * 2);
            spinDown2 = setup("/player/boy_spin_old_down_2", gp.tileSize, gp.tileSize * 2);
            spinLeft1 = setup("/player/boy_spin_old_left_1", gp.tileSize * 2, gp.tileSize * 2);
            spinLeft2 = setup("/player/boy_spin_old_left_2", gp.tileSize * 2, gp.tileSize);
            spinRight1 = setup("/player/boy_spin_old_right_1", gp.tileSize * 2, gp.tileSize * 2);
            spinRight2 = setup("/player/boy_spin_old_right_2", gp.tileSize * 2, gp.tileSize);
        }
        else {
            spinUp1 = setup("/player/boy_spin_up_1", gp.tileSize * 2, gp.tileSize * 2);
            spinUp2 = setup("/player/boy_spin_up_2", gp.tileSize, gp.tileSize * 2);
            spinDown1 = setup("/player/boy_spin_down_1", gp.tileSize * 2, gp.tileSize * 2);
            spinDown2 = setup("/player/boy_spin_down_2", gp.tileSize, gp.tileSize * 2);
            spinLeft1 = setup("/player/boy_spin_left_1", gp.tileSize * 2, gp.tileSize * 2);
            spinLeft2 = setup("/player/boy_spin_left_2", gp.tileSize * 2, gp.tileSize);
            spinRight1 = setup("/player/boy_spin_right_1", gp.tileSize * 2, gp.tileSize * 2);
            spinRight2 = setup("/player/boy_spin_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void getGuardImage() {
        guardUp1 = setup("/player/boy_guard_up_1");
        guardUp2 = setup("/player/boy_guard_up_2");
        guardDown1 = setup("/player/boy_guard_down_1");
        guardDown2 = setup("/player/boy_guard_down_2");
        guardLeft1 = setup("/player/boy_guard_left_1");
        guardLeft2 = setup("/player/boy_guard_left_2");
        guardRight1 = setup("/player/boy_guard_right_1");
        guardRight2 = setup("/player/boy_guard_right_2");
    }

    public void getAimImage() {
        aimUp1 = setup("/player/boy_aim_up_1");
        aimUp2 = setup("/player/boy_aim_up_2");
        aimDown1 = setup("/player/boy_aim_down_1");
        aimDown2 = setup("/player/boy_aim_down_2");
        aimLeft1 = setup("/player/boy_aim_left_1");
        aimLeft2 = setup("/player/boy_aim_left_2");
        aimRight1 = setup("/player/boy_aim_right_1");
        aimRight2 = setup("/player/boy_aim_right_2");
    }

    public void getRollImage() {
        rollUp1 = setup("/player/boy_roll_up_1");
        rollUp2 = setup("/player/boy_roll_up_2");
        rollUp3 = setup("/player/boy_roll_up_3");
        rollUp4 = setup("/player/boy_roll_up_4");
        rollDown1 = setup("/player/boy_roll_down_1");
        rollDown2 = setup("/player/boy_roll_down_2");
        rollDown3 = setup("/player/boy_roll_down_3");
        rollDown4 = setup("/player/boy_roll_down_4");
        rollLeft1 = setup("/player/boy_roll_left_1");
        rollLeft2 = setup("/player/boy_roll_left_2");
        rollLeft3 = setup("/player/boy_roll_left_3");
        rollLeft4 = setup("/player/boy_roll_left_4");
        rollRight1 = setup("/player/boy_roll_right_1");
        rollRight2 = setup("/player/boy_roll_right_2");
        rollRight3 = setup("/player/boy_roll_right_3");
        rollRight4 = setup("/player/boy_roll_right_4");
    }

    public void getSwimImage() {
        swimUp1 = setup("/player/boy_swim_up_1");
        swimUp2 = setup("/player/boy_swim_up_2");
        swimDown1 = setup("/player/boy_swim_down_1");
        swimDown2 = setup("/player/boy_swim_down_2");
        swimLeft1 = setup("/player/boy_swim_left_1");
        swimLeft2 = setup("/player/boy_swim_left_2");
        swimRight1 = setup("/player/boy_swim_right_1");
        swimRight2 = setup("/player/boy_swim_right_2");
    }

    public void getGrabImage() {
        grabUp1 = setup("/player/boy_grab_up_1");
        grabUp2 = setup("/player/boy_grab_up_2");
        grabUp3 = setup("/player/boy_grab_up_3");
        grabDown1 = setup("/player/boy_grab_down_1");
        grabDown2 = setup("/player/boy_grab_down_2");
        grabDown3 = setup("/player/boy_grab_down_3");
        grabLeft1 = setup("/player/boy_grab_left_1");
        grabLeft2 = setup("/player/boy_grab_left_2");
        grabLeft3 = setup("/player/boy_grab_left_3");
        grabRight1 = setup("/player/boy_grab_right_1");
        grabRight2 = setup("/player/boy_grab_right_2");
        grabRight3 = setup("/player/boy_grab_right_3");
    }

    public void getCarryImage() {
        carryUp1 = setup("/player/boy_carry_up_1");
        carryUp2 = setup("/player/boy_carry_up_2");
        carryDown1 = setup("/player/boy_carry_down_1");
        carryDown2 = setup("/player/boy_carry_down_2");
        carryLeft1 = setup("/player/boy_carry_left_1");
        carryLeft2 = setup("/player/boy_carry_left_2");
        carryRight1 = setup("/player/boy_carry_right_1");
        carryRight2 = setup("/player/boy_carry_right_2");
    }

    public void getThrowImage() {
        throwUp1 = setup("/player/boy_throw_up_1");
        throwUp2 = setup("/player/boy_throw_up_2");
        throwDown1 = setup("/player/boy_throw_down_1");
        throwDown2 = setup("/player/boy_throw_down_2");
        throwLeft1 = setup("/player/boy_throw_left_1");
        throwLeft2 = setup("/player/boy_throw_left_2");
        throwRight1 = setup("/player/boy_throw_right_1");
        throwRight2 = setup("/player/boy_throw_right_2");
    }

    public void getDigImage() {
        digUp1 = setup("/player/boy_dig_up_1");
        digUp2 = setup("/player/boy_dig_up_2");
        digDown1 = setup("/player/boy_dig_down_1");
        digDown2 = setup("/player/boy_dig_down_2");
        digLeft1 = setup("/player/boy_dig_left_1");
        digLeft2 = setup("/player/boy_dig_left_2");
        digRight1 = setup("/player/boy_dig_right_1");
        digRight2 = setup("/player/boy_dig_right_2");
    }

    public void getHookImage() {
        hookUp1 = setup("/player/boy_hook_up_1", gp.tileSize, gp.tileSize + 8);
        hookDown1 = setup("/player/boy_hook_down_1", gp.tileSize, gp.tileSize + 8);
        hookLeft1 = setup("/player/boy_hook_left_1", gp.tileSize + 8, gp.tileSize);
        hookRight1 = setup("/player/boy_hook_right_1", gp.tileSize + 8, gp.tileSize);
    }

    public void getJumpImage() {
        jumpUp1 = setup("/player/boy_jump_up_1");
        jumpUp2 = setup("/player/boy_jump_up_2");
        jumpUp3 = setup("/player/boy_jump_up_3");
        jumpDown1 = setup("/player/boy_jump_down_1");
        jumpDown2 = setup("/player/boy_jump_down_2");
        jumpDown3 = setup("/player/boy_jump_down_3");
        jumpLeft1 = setup("/player/boy_jump_left_1");
        jumpLeft2 = setup("/player/boy_jump_left_2");
        jumpLeft3 = setup("/player/boy_jump_left_3");
        jumpRight1 = setup("/player/boy_jump_right_1");
        jumpRight2 = setup("/player/boy_jump_right_2");
        jumpRight3 = setup("/player/boy_jump_right_3");
    }

    public void getSoarImage() {
        soarUp1 = setup("/player/boy_soar_up_1");
        soarDown1 = setup("/player/boy_soar_down_1");
        soarLeft1 = setup("/player/boy_soar_left_1");
        soarRight1 = setup("/player/boy_soar_right_1");
    }

    public void getRodImage() {
        rodUp1 = setup("/player/boy_rod_up_1", gp.tileSize * 2, gp.tileSize);
        rodUp2 = setup("/player/boy_rod_up_2", gp.tileSize * 2, gp.tileSize * 2);
        rodUp3 = setup("/player/boy_rod_up_3", gp.tileSize, gp.tileSize * 2);
        rodDown1 = setup("/player/boy_rod_down_1", gp.tileSize * 2, gp.tileSize);
        rodDown2 = setup("/player/boy_rod_down_2", gp.tileSize * 2, gp.tileSize * 2);
        rodDown3 = setup("/player/boy_rod_down_3", gp.tileSize, gp.tileSize * 2);
        rodLeft1 = setup("/player/boy_rod_left_1", gp.tileSize, gp.tileSize * 2);
        rodLeft2 = setup("/player/boy_rod_left_2", gp.tileSize * 2, gp.tileSize * 2);
        rodLeft3 = setup("/player/boy_rod_left_3", gp.tileSize * 2, gp.tileSize);
        rodRight1 = setup("/player/boy_rod_right_1", gp.tileSize, gp.tileSize * 2);
        rodRight2 = setup("/player/boy_rod_right_2", gp.tileSize * 2, gp.tileSize * 2);
        rodRight3 = setup("/player/boy_rod_right_3", gp.tileSize * 2, gp.tileSize);
    }

    public void getMiscImage() {
        sit = setup("/player/boy_sit");
        sing = setup("/npc/girl_sing_1");
        play1 = setup("/player/boy_play_1");
        play2 = setup("/player/boy_play_2");
        play3 = setup("/player/boy_play_3");
        play4 = setup("/player/boy_play_4");
        itemGet_1 = setup("/player/boy_item_get_1");
        itemGet_2 = setup("/player/boy_item_get_2");
        drown = setup("/player/boy_drown");
        fall1 = setup("/player/boy_fall_1");
        fall2 = setup("/player/boy_fall_2");
        fall3 = setup("/player/boy_fall_3");
        die1 = setup("/player/boy_die_1");
        die2 = setup("/player/boy_die_2");
        die3 = setup("/player/boy_die_3");
        die4 = setup("/player/boy_die_4");
        die5 = setup("/player/boy_die_5");
    }
/** END DEFAULT HANDLERS **/


    /**
     * UPDATER
     **/

    public void update() {

        if (action == Action.DROWNING) {
            takingDamage();
            return;
        }
        else if (action == Action.FALLING) {
            takingDamage();
            return;
        }

        checkCollision();

        if (action == Action.IDLE) {
            onGround = true;
        }

        if (knockback) {
            knockbackPlayer();
            manageValues();
            checkDeath();
            return;
        }

        if (gp.keyH.aPressed && !attacking) {
            action();
        }

        if (gp.keyH.lPressed) {
            zTarget();
            gp.keyH.lPressed = false;
        }
        if (lockon) {
            lockedOn();
        }
        else if (lockedTarget != null) {
            lockedTarget.locked = false;
            lockedTarget = null;
        }

        // DISABLED BUTTONS DURING SPECIFIC ACTIONS
        if (!disabled_actions.contains(action)) {
            if (gp.keyH.bPressed) {
                attack();
            }
            if (spinning) {
                spinAttacking();
                manageValues();
                checkDeath();
                return;
            }
            if (attacking) {
                attacking();
                manageValues();
                checkDeath();
                return;
            }
            if (gp.keyH.xPressed) {
                useItem();
            }
            if (gp.keyH.rPressed) {
                action = Action.GUARDING;
            }
            if (gp.keyH.zPressed) {
                cycleItems();
            }
        }
        // CAN THROW BOMB WITH ITEM BUTTON
        else if (isHoldingBomb() && gp.keyH.xPressed) {
            throwEntity();
            manageValues();
            checkDeath();
            gp.keyH.xPressed = false;
            return;
        }

        if (getAction()) {
            manageValues();
            checkDeath();
            return;
        }

        if ((gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed) &&
                action != Action.AIMING && action != Action.GRABBING) {
            walking();
        }
        else {
            moving = false;
        }

        manageValues();
        checkDeath();
    }

    public boolean getAction() {

        boolean stop = false;

        if (action == Action.AIMING) {
            aiming();
        }
        else if (action == Action.CHARGING) {
            chargeSpin();
        }
        else if (action == Action.DIGGING) {
            digging();
            stop = true;
        }
        else if (action == Action.GRABBING) {
            pulling();
            stop = true;
        }
        else if (action == Action.GUARDING) {
            guarding();
        }
        else if (action == Action.JUMPING) {
            jumping();
        }
        else if (action == Action.PLAYING) {
            if (playing) {
                playing();
            }
            stop = true;
        }
        else if (action == Action.PUSHING) {
            pushing();
        }
        else if (action == Action.ROLLING) {
            rolling();
        }
        else if (action == Action.SOARING) {
            jumping();
        }
        else if (action == Action.SWIMMING) {
            swimming();
        }
        else if (action == Action.SWINGING) {
            swinging();
            stop = true;
        }
        else if (action == Action.THROWING || action == Action.TOSSING || action == Action.HOOKSHOT) {
            throwing();
            stop = true;
        }

        return stop;
    }

/** END UPDATER **/


    /**
     * PLAYER METHODS
     **/

    // MOVEMENT
    public void walking() {

        if (action == Action.GUARDING) {
            getDirection();
            moving = false;
            return;
        }

        // DON'T CHANGE DIRECTIONS WHILE ROLLING AND NOT LOCKON
        if (action != Action.ROLLING || lockon) {
            getDirection();
        }

        if (!gp.keyH.debug) {
            checkCollision();
        }
        if (!collisionOn) {

            moving = true;

            if (lockon) {
                move(lockonDirection);
            }
            else {
                move(direction);
            }

            cycleSprites();
        }
        else {
            moving = false;
        }
    }

    public void getDirection() {

        String tempDirection = "";

        if (gp.keyH.upPressed) {
            tempDirection = "up";
        }
        if (gp.keyH.downPressed) {
            tempDirection = "down";
        }
        if (gp.keyH.leftPressed) {
            tempDirection = "left";
        }
        if (gp.keyH.rightPressed) {
            tempDirection = "right";
        }

        if (gp.keyH.upPressed && gp.keyH.leftPressed) {
            tempDirection = "upleft";
        }
        if (gp.keyH.upPressed && gp.keyH.rightPressed) {
            tempDirection = "upright";
        }
        if (gp.keyH.downPressed && gp.keyH.leftPressed) {
            tempDirection = "downleft";
        }
        if (gp.keyH.downPressed && gp.keyH.rightPressed) {
            tempDirection = "downright";
        }

        if (lockon) {
            lockonDirection = tempDirection;
        }
        else {
            direction = tempDirection;
        }
    }

    public void move(String direction) {

        if (canMove) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "upleft":
                    worldY -= (int) (speed - 0.5);
                    worldX -= (int) (speed - 0.5);
                    break;
                case "upright":
                    worldY -= (int) (speed - 0.5);
                    worldX += (int) (speed - 0.5);
                    break;

                case "down":
                    worldY += speed;
                    break;
                case "downleft":
                    worldY += (int) (speed - 0.5);
                    worldX -= (int) (speed - 0.5);
                    break;
                case "downright":
                    worldY += speed;
                    worldX += (int) (speed - 0.5);
                    break;

                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
    }

    public void cycleSprites() {

        spriteCounter++;
        if (spriteCounter > animationSpeed) {

            // CYCLE WALKING/SWIMMING SPRITES
            if (spriteNum == 1) {
                if (action == Action.SWIMMING && !diving) {
                    playSwimSE();
                }
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }

            // RUNNING ANIMATION
            if (action == Action.RUNNING) {
                currentItem.playSE();
                speed = 6;
                animationSpeed = 6;
            }
            else {
                speed = defaultSpeed;
                animationSpeed = 10;
            }

            spriteCounter = 0;
        }
    }

    // ACTION
    public void action() {
        switch (action) {
            case IDLE:
                if (moving) {
                    playGrunt();
                    action = Action.ROLLING;
                    gp.keyH.aPressed = false;
                }
                else {
                    int npcIndex = gp.cChecker.checkNPC();
                    int objIndex = gp.cChecker.checkObject(this, true);

                    if (npcIndex != -1) {
                        interactNPC(npcIndex);
                    }
                    else if (objIndex != -1) {
                        interactObject(objIndex);
                    }
                    else {
                        grabbing();
                    }
                }
                break;
            case CARRYING:
                throwEntity();
                break;
            case SWIMMING:
                diving = true;
                guarded = true;
                break;
            default:
                break;
        }
    }

    public void interactNPC(int i) {
        if (i != -1 && !disabled_actions.contains(action)) {
            if (gp.npc[gp.currentMap][i].name.equals(NPC_Cucco.npcName)) {
                grabbing();
            }
            else {
                resetValues();
                gp.npc[gp.currentMap][i].speak();
            }

        }
    }

    public void interactObject(int i) {
        if (i != -1 && gp.obj[gp.currentMap][i].type == type_obstacle_i) {
            gp.obj[gp.currentMap][i].interact();
        }
    }

    // COLLISION
    public void checkCollision() {

        collisionOn = false;

        if (gp.keyH.debug) {
            return;
        }

        // CHECK EVENTS
        gp.eHandler.checkEvent();

        // CHECK TILE COLLISION
        gp.cChecker.checkTile(this);

        // CHECK INTERACTIVE TILE COLLISION
        gp.cChecker.checkEntity(this, gp.iTile);

        // CHECK NPC COLLISION
        gp.cChecker.checkEntity(this, gp.npc);

        // DON'T CHECK PITS WHEN JUMPING
        if (action != Action.JUMPING && action != Action.SOARING) {
            gp.cChecker.checkHazard(this, true);
        }

        if (diving) {
            if (collisionOn) {
                diving = false;
            }
            else {
                return;
            }
        }

        // CHECK ENEMY COLLISION
        contactEnemy(getEnemy(this));

        // CHECK OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        // CHECK INTERACTIVE OBJECTS COLLISION
        int objTIndex = gp.cChecker.checkObject_I(this, true);
        interactIObject(objTIndex);

        // CHECK PROJECTILE COLLISION
        int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
        pickUpProjectile(projectileIndex);
    }

    public void interactIObject(int i) {

        // OBJECT INTERACTION
        if (i != -1 && action == Action.IDLE) {

            if (gp.obj_i[gp.currentMap][i].type == type_obstacle_i) {
                if (!gp.obj_i[gp.currentMap][i].moving) {
                    gp.obj_i[gp.currentMap][i].move(direction);
                }
            }
        }
    }

    public void pickUpObject(int i) {

        // OBJECT INTERACTION
        if (i != -1) {

            // OBSTACLE ITEMS
            if (gp.obj[gp.currentMap][i].type == type_obstacle) {
                gp.obj[gp.currentMap][i].interact();
            }
            // PICKUP ONLY ITEMS
            else if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            // REGULAR ITEMS
            else if (canObtainItem(gp.obj[gp.currentMap][i])) {
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }

    public void pickUpProjectile(int i) {
        if (i != -1) {
            Projectile projectile = (Projectile) gp.projectile[gp.currentMap][i];
            projectile.interact();
        }
    }

    public void getObject(Entity item) {

        if (item.type == type_collectable) {
            item.use(this);
            return;
        }
        else if (item.type == type_item) {
            inventory_item.add(item);

            gp.ui.newItem = item;
            gp.ui.currentDialogue = "You got the " + item.name + "!\n" + item.getDescription;
        }
        else if (item.type == type_equipment) {
            if (item.name.equals(EQP_Sword_Old.eqpName) ||
                    item.name.equals(EQP_Sword_Master.eqpName)) {
                currentWeapon = item;
                attack = getAttack();
                getAttackImage();
                getSpinImage();
            }
            else if (item.name.equals(EQP_Flippers.eqpName)) {
                canSwim = true;
            }

            gp.ui.newItem = item;
            gp.ui.currentDialogue = "You got the " + item.name + "!";
        }
        else if (item.type == type_key) {
            playGetItemSE();
            keys++;

            gp.ui.newItem = item;
            gp.ui.currentDialogue = "You found a " + item.name + "!";
        }
        else if (item.type == type_boss_key) {
            playGetItemSE();
            boss_key++;

            gp.ui.newItem = item;
            gp.ui.currentDialogue = "You found the " + item.name + "!";
        }
        else {
            inventory.add(item);
        }

        playGetItemSE();
        gp.ui.subState = 0;
        gp.gameState = gp.achievementState;
    }

    // SWORD
    public void attack() {
        swingSword();
    }

    public void swingSword() {
        if (currentWeapon == null) {
            gp.keyH.bPressed = false;
            gp.gameState = gp.dialogueState;
            startDialogue(this, 0);
        }
        // SWING SWORD IF NOT ALREADY
        else if (!attacking) {
            playGrunt();
            currentWeapon.playSE();

            attacking = true;
            spriteCounter = 0;
        }
    }

    public void attacking() {

        // HOLD ACTION BUTTON TO CHARGE SPIN ATTACK
        if (gp.keyH.bPressed) {
            spinCharge++;
        }
        else {
            spinCharge = 0;
        }

        attackCounter++;

        // ATTACK IMAGE 1
        if (swingSpeed1 >= attackCounter) {
            attackNum = 1;
            if (swingSpeed1 == attackCounter) {
                playAttackSE();
            }
        }
        // ATTACK IMAGE 2
        else if (swingSpeed2 >= attackCounter) {
            attackNum = 2;
        }
        // ATTACK IMAGE 3
        else if (swingSpeed3 >= attackCounter) {

            attackNum = 3;

            // SAVE X/Y
            int currentWorldX = worldX;
            int currentWorldY = worldY;

            // ADJUST X/Y
            switch (direction) {
                case "up":
                case "upleft":
                case "upright":
                    worldY -= attackbox.height + hitbox.y;
                    hitbox.height = attackbox.height;
                    break;
                case "down":
                case "downleft":
                case "downright":
                    worldY += attackbox.height - hitbox.y;
                    hitbox.height = attackbox.height;
                    break;
                case "left":
                    worldX -= attackbox.width;
                    hitbox.width = attackbox.width;
                    break;
                case "right":
                    worldX += attackbox.width - hitbox.y;
                    hitbox.width = attackbox.width;
                    break;
            }

            // CHECK IF ATTACK LANDS ON ENEMY
            Entity enemy = getEnemy(this);
            if (enemy != null) {
                if (currentWeapon == null) {
                    gp.player.damageEnemy(enemy, this, attack, 0);
                }
                else {
                    gp.player.damageEnemy(enemy, this, attack, currentWeapon.knockbackPower);
                }
            }

            // CHECK INTERACTIVE TILE
            int iTileIndex = gp.cChecker.checkEntity(gp.player, gp.iTile);
            gp.player.damageInteractiveTile(iTileIndex, this);

            // CHECK IF ATTACK LANDS ON PROJECTILE
            int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            gp.player.damageProjectile(projectileIndex);


            // RESTORE HITBOX
            worldX = currentWorldX;
            worldY = currentWorldY;
            hitbox.width = hitboxDefaultWidth;
            hitbox.height = hitboxDefaultHeight;
        }
        else {

            // CHARGE SPIN ATTACK
            if (spinCharge > swingSpeed3 && gp.keyH.bPressed) {
                currentWeapon.playChargeSE();
                action = Action.CHARGING;
                attackCounter = 0;
                spinCharge = 0;
            }
            // RESET IMAGE/VALUES
            else {
                attackNum = 1;
                attackCounter = 0;
                spinCharge = 0;
                attacking = false;
                gp.keyH.bPressed = false;
            }
        }
    }

    public void chargeSpin() {

        // CHARGE SWORD WHILE ACTION PRESSED
        if (gp.keyH.bPressed) {

            if (charge < 119) {
                charge += 2;
            }

            lockon = true;
            lockonDirection = direction;
            speed = 2;
        }
        // CHARGE RELEASED, MAX POWER
        else if (charge >= 120) {

            // RELEASE SPIN ATTACK
            playSpin();
            currentWeapon.playSpinSE();

            // ROTATE COUNTER CLOCKWISE
            switch (direction) {
                case "up":
                case "upleft":
                case "upright":
                    direction = "left";
                    break;
                case "right":
                    direction = "up";
                    break;
                case "down":
                case "downleft":
                case "downright":
                    direction = "right";
                    break;
                case "left":
                    direction = "down";
                    break;
            }

            charge = 0;
            action = Action.IDLE;
            spinning = true;
            gp.keyH.bPressed = false;

            if (lockedTarget == null) {
                lockon = false;
            }

            speed = defaultSpeed;
        }
        // CHARGE RELEASED BEFORE MAX POWER
        else {
            charge = 0;
            attackNum = 1;
            attackCounter = 0;
            attacking = false;
            action = Action.IDLE;

            if (lockedTarget == null) {
                lockon = false;
            }
        }
    }

    public void spinAttacking() {

        attackCounter++;

        // ATTACK IMAGE 1
        if (2 >= attackCounter) {
            attackNum = 1;
        }
        // ATTACK IMAGE 2
        else if (5 >= attackCounter) {
            attackNum = 2;

            // SAVE HITBOX
            int currentWorldX = worldX;
            int currentWorldY = worldY;

            // ADJUST X/Y AND HITBOX
            // SWING DETECTION AS FOLLOWS (P = PLAYER):
            /* L U U
             * L P R
             * D D R */
            switch (direction) {
                case "up":
                case "upleft":
                case "upright":
                    worldY -= attackbox.height + hitbox.y;
                    hitbox.height = attackbox.height;
                    hitbox.width *= 2;
                    break;
                case "down":
                case "downleft":
                case "downright":
                    worldX -= attackbox.width;
                    worldY += attackbox.height - hitbox.y;
                    hitbox.height = attackbox.height;
                    hitbox.width *= 2;
                    break;
                case "left":
                    worldX -= attackbox.width;
                    worldY -= attackbox.height;
                    hitbox.width = attackbox.width;
                    hitbox.height *= 2;
                    break;
                case "right":
                    worldX += attackbox.width - hitbox.y;
                    hitbox.width = attackbox.width;
                    hitbox.height *= 2;
                    break;
            }

            // CHECK IF ATTACK LANDS ON ENEMY
            Entity enemy = getEnemy(this);
            if (enemy != null) {
                int damage = attack + 1;
                gp.player.damageEnemy(enemy, this, damage, currentWeapon.knockbackPower);
            }

            // CHECK INTERACTIVE TILE
            int iTileIndex = gp.cChecker.checkEntity(gp.player, gp.iTile);
            gp.player.damageInteractiveTile(iTileIndex, this);

            // CHECK IF ATTACK LANDS ON PROJECTILE
            int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            gp.player.damageProjectile(projectileIndex);

            // RESET X/Y AND HITBOX
            worldX = currentWorldX;
            worldY = currentWorldY;
            hitbox.width = hitboxDefaultWidth;
            hitbox.height = hitboxDefaultHeight;
        }
        else {

            attackNum = 1;
            attackCounter = 0;

            // ROTATE PLAYER CLOCKWISE
            if (spinNum != 3) {
                switch (direction) {
                    case "up":
                    case "upleft":
                    case "upright":
                        direction = "left";
                        break;
                    case "right":
                        direction = "up";
                        break;
                    case "down":
                    case "downleft":
                    case "downright":
                        direction = "right";
                        break;
                    case "left":
                        direction = "down";
                        break;
                }
            }

            // REPEAT 4 TIMES
            spinNum++;
            if (spinNum == 4) {
                spinNum = 0;
                spinning = false;
                attacking = false;
            }
        }
    }

    // Z-TARGETING
    public void zTarget() {

        // FIND NEW TARGET
        Entity newTarget = findTarget();

        // NEW TARGET FOUND
        if (newTarget != null) {

            // NO CURRENTLY LOCKED TARGET
            if (lockedTarget == null) {
                playZTargetSE();

                lockedTarget = newTarget;
                lockedTarget.locked = true;
                lockon = true;
            }
            // TARGET ALREADY LOCKED
            else {

                // MOVING BACKWARDS, TURN OFF LOCKON
                if (Objects.equals(direction, getOppositeDirection(lockonDirection))) {
                    lockedTarget.locked = false;
                    lockedTarget = null;
                    lockon = false;
                }
                // SWITCH TARGETS
                else {
                    playZTargetSE();

                    lockedTarget.locked = false;
                    lockedTarget = newTarget;
                    lockon = true;
                }
            }
        }
        // NEW TARGET NOT FOUND
        else if (lockedTarget != null) {
            lockedTarget.locked = false;
            lockedTarget = null;
            lockon = false;
        }
    }

    public Entity findTarget() {

        Entity target = null;
        int currentDistance = 6;

        for (Entity e : gp.enemy[gp.currentMap]) {

            if (e != null && e != lockedTarget && e.canTarget && !e.dying && !e.captured) {

                // ENEMY WITHIN 6 TILES
                int enemyDistance = getTileDistance(e);
                if (enemyDistance < currentDistance) {
                    currentDistance = enemyDistance;
                    target = e;
                }
            }
        }

        return target;
    }

    public void lockedOn() {

        // DOES NOT APPLY DURING SPECIFIC ACTIONS
        if (spinning || action == Action.AIMING || action == Action.CHARGING) {
            return;
        }

        // TARGET FOUND WITHIN 8 TILES
        if (lockedTarget != null && getTileDistance(lockedTarget) <= 8) {

            // TARGET ALIVE
            if (lockedTarget.alive) {
                lockedTarget.locked = true;
                direction = findTargetDirection(lockedTarget);
                lockonDirection = direction;
            }
            // TARGET DEFEATED
            else {
                lockedTarget.locked = false;
                lockedTarget = null;

                // FIND NEW TARGET
                zTarget();
            }
        }
        // NO TARGET FOUND WITHIN 8 TILES, TURN OFF LOCKON
        else {
            lockon = false;
        }
    }

    public String findTargetDirection(Entity target) {

        // FIND DIRECTION RELATIVE TO TARGET
        String zDirection = direction;

        // PLAYER X/Y
        int px = (worldX + (gp.tileSize / 2));
        int py = (worldY + (gp.tileSize / 2));

        // TARGET X/Y
        int ex = (target.worldX + (gp.tileSize / 2));
        int ey = (target.worldY + (gp.tileSize / 2));

        if (py == ey && px == ex) // SAME TILE
        {
            zDirection = direction;
        }
        else if (py > ey && py - ey >= Math.abs(px - ex)) // SOUTH
        {
            zDirection = "up";
        }
        else if (py >= ey && px > ex) // EAST / SOUTHEAST
        {
            zDirection = "left";
        }
        else if (py >= ey && ex > px) // WEST / SOUTHWEST
        {
            zDirection = "right";
        }
        else if (ey > py && ey - py >= Math.abs(px - ex)) // NORTH
        {
            zDirection = "down";
        }
        else if (ey > py && px > ex) // EAST / NORTHEAST
        {
            zDirection = "left";
        }
        else if (ey > py && ex > px) // WEST / NORTHWEST
        {
            zDirection = "right";
        }

        return zDirection;
    }

    // ENEMY DAMAGE
    public void contactEnemy(Entity enemy) {

        // PLAYER HURT BY ENEMY
        if (enemy != null && !invincible) {

            if (enemy.collision && !enemy.dying && !enemy.captured || enemy.aggressive) {

                if (enemy.knockbackPower > 0) {
                    setKnockback(this, enemy, enemy.knockbackPower);
                }

                String guardDirection = getOppositeDirection(enemy.direction);
                if (action == Action.GUARDING && direction.equals(guardDirection)) {
                    playBlockSE();

                    if (enemy.name.equals(EMY_Beetle.emyName)) {
                        enemy.attacking = true;
                        setKnockback(enemy, this, 1);
                    }

                    if (enemy.knockbackPower == 0) {
                        setKnockback(enemy, this, 1);
                    }
                }
                else {
                    playHurt();

                    int damage = enemy.attack;
                    if (damage < 0) {
                        damage = 0;
                    }
                    this.life -= damage;

                    invincible = true;
                    transparent = true;
                }
            }
        }
    }

    public void knockbackPlayer() {

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.enemy);
        gp.cChecker.checkEntity(this, gp.obj);
        gp.cChecker.checkEntity(this, gp.obj_i);
        gp.cChecker.checkEntity(this, gp.iTile);
        gp.eHandler.checkEvent();

        if (collisionOn) {
            knockbackCounter = 0;
            knockback = false;
            speed = defaultSpeed;
        }
        else {
            switch (knockbackDirection) {
                case "up":
                case "upleft":
                case "upright":
                    worldY -= speed;
                    break;
                case "down":
                case "downleft":
                case "downright":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        knockbackCounter++;
        if (knockbackCounter == 10) {
            knockbackCounter = 0;
            knockback = false;
            speed = defaultSpeed;
        }
    }

    // ITEM HANDLING
    public void selectCollectable() {

        int inventoryIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if (inventoryIndex < inventory.size()) {
            gp.keyH.playSelectSE();

            if (action != Action.SWIMMING) {
                action = Action.IDLE;
            }

            itemIndex = inventoryIndex;

            Entity selectedItem = inventory.get(inventoryIndex);
            if (selectedItem.type == type_consumable) {
                if (selectedItem.use(this)) {

                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    }
                    else {
                        inventory.remove(selectedItem);
                    }
                }
            }
        }
    }

    public void selectItem() {

        int inventoryIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if (inventoryIndex < inventory_item.size()) {

            if (action != Action.IDLE && action != Action.SWIMMING) {
                return;
            }

            gp.keyH.playSelectSE();
            itemIndex = inventoryIndex;

            Entity selectedItem = inventory_item.get(inventoryIndex);

            if (selectedItem == currentItem) {
                currentItem = null;
            }
            else {
                currentItem = selectedItem;
            }
        }
    }

    public void useItem() {

        // HAS ITEM EQUIPPED
        if (currentItem != null) {
            switch (currentItem.name) {
                case ITM_Bomb.itmName:
                case ITM_Boots.itmName:
                case ITM_Cape.itmName:
                case ITM_Feather.itmName:
                case ITM_Harp.itmName:
                case ITM_Rod.itmName:
                case ITM_Shovel.itmName:
                    gp.keyH.xPressed = false;
                    gp.keyH.bPressed = false;
                    currentItem.use(this);
                    break;
                case ITM_Bow.itmName:

                    if (currentItem.setCharge(this)) {
                        currentItem.playChargeSE();
                        lockon = true;
                        lockonDirection = direction;
                    }
                    break;
                case ITM_Boomerang.itmName:
                case ITM_Hookshot.itmName:

                    // STOP MOVEMENT
                    gp.keyH.xPressed = false;
                    gp.keyH.upPressed = false;
                    gp.keyH.downPressed = false;
                    gp.keyH.leftPressed = false;
                    gp.keyH.rightPressed = false;
                    currentItem.use(this);
                    break;
            }
        }
        // NO ITEM EQUIPPED
        else {
            gp.keyH.xPressed = false;
            startDialogue(this, 2);
            gp.gameState = gp.dialogueState;
        }
    }

    public void cycleItems() {

        if (currentItem != null) {
            gp.keyH.playCursorSE();
            gp.keyH.zPressed = false;

            itemIndex++;
            if (itemIndex >= inventory_item.size()) {
                itemIndex = 0;
            }

            currentItem = inventory_item.get(itemIndex);
        }
    }

    // ANIMATIONS
    public void aiming() {

        if (6 >= charge) {
            aimNum = 1;
        }
        else {
            aimNum = 2;
        }

        switch (direction) {
            case "up":
            case "upleft":
            case "upright":
            case "down":
            case "downleft":
            case "downright":
                gp.keyH.upPressed = false;
                gp.keyH.downPressed = false;
                if ((gp.keyH.leftPressed || gp.keyH.rightPressed)) {
                    walking();
                }
                break;
            case "left":
            case "right":
                gp.keyH.leftPressed = false;
                gp.keyH.rightPressed = false;
                if ((gp.keyH.upPressed || gp.keyH.downPressed)) {
                    walking();
                }
                break;
        }

        if (gp.keyH.xPressed) {
            currentItem.setCharge(this);
        }
        else {
            currentItem.use(this);
            if (lockedTarget == null) {
                lockon = false;
            }
        }
    }

    public void digging() {

        digCounter++;

        if (12 >= digCounter) {
            digNum = 1;
        }
        else if (24 > digCounter) {
            digNum = 2;
        }
        else if (digCounter > 24) {

            // CHECK INTERACTIVE TILE
            int iTileIndex = gp.cChecker.checkDigging();
            damageInteractiveTile(iTileIndex, currentItem);

            digNum = 1;
            digCounter = 0;
            action = Action.IDLE;
        }
    }

    public void grabbing() {

        grabbedObject = null;

        int i = gp.cChecker.checkEntity(this, gp.iTile);
        if (i != -1) {
            if (gp.iTile[gp.currentMap][i].grabbable) {
                action = Action.GRABBING;
                grabbedObject = gp.iTile[gp.currentMap][i];
            }
        }
        else {
            i = gp.cChecker.checkEntity(this, gp.projectile);
            if (i != -1 &&
                    gp.projectile[gp.currentMap][i].grabbable &&
                    !gp.projectile[gp.currentMap][i].captured) {
                action = Action.GRABBING;
                grabbedObject = gp.projectile[gp.currentMap][i];
            }
            else {
                i = gp.cChecker.checkNPC();
                if (i != -1 &&
                        gp.npc[gp.currentMap][i].grabbable &&
                        !gp.npc[gp.currentMap][i].captured) {
                    action = Action.GRABBING;
                    grabbedObject = gp.npc[gp.currentMap][i];
                }
            }
        }
    }

    public void guarding() {

        if (guardCounter < 15) {
            guardCounter++;
        }

        if (6 >= guardCounter) {
            if (guardCounter == 1) {
                playGuardSE();
            }
            guardNum = 1;
        }
        else {
            guardNum = 2;
        }
    }

    public void jumping() {

        jumpCounter++;

        if (6 >= jumpCounter) {
            jumpNum = 1;
        }
        else if (18 > jumpCounter && 12 >= jumpCounter) {
            jumpNum = 2;
        }
        else if (27 > jumpCounter) {
            jumpNum = 3;
        }
        else if (jumpCounter >= 28) {

            if (action == Action.SOARING) {

                if (jumpCounter == 30) {
                    currentItem.playSE();
                }

                jumpNum = 4;
                soaring();
            }
            else {
                jumpNum = 1;
                jumpCounter = 0;
                action = Action.IDLE;
            }
        }
    }

    public void playing() {
        playCounter++;
        if (6 > playCounter) {
            playNum = 1;
        }
        else if (12 > playCounter) {
            playNum = 2;
        }
        else if (18 > playCounter) {
            playNum = 3;
        }
        else {
            playNum = 1;
            playCounter = 0;
            playing = false;
        }
    }

    public void pulling() {
        pullCounter++;

        if (12 >= pullCounter) {
            pullNum = 1;
        }
        else if (18 >= pullCounter) {
            pullNum = 2;
        }
        else if (27 >= pullCounter) {
            pullNum = 3;
        }
        else {

            if (grabbedObject != null) {
                playPullSE();
                pullNum = 1;
                pullCounter = 0;
                action = Action.CARRYING;
                grabbedObject.grabbed = true;
            }
            else {
                pullNum = 1;
                pullCounter = 0;
                action = Action.IDLE;
            }

            gp.keyH.aPressed = false;
        }
    }

    public void pushing() {
        pushCounter++;

        if (6 >= pushCounter) {
            pushNum = 1;
        }
        else if (18 >= pushCounter) {
            pushNum = 2;
        }
        else {
            playPushSE();
            pushNum = 1;
            pushCounter = 0;
            action = Action.IDLE;
        }
    }

    public void rolling() {

        speed = 5;

        rollCounter++;
        if (5 >= rollCounter) {
            rollNum = 1;
        }
        else if (10 > rollCounter) {
            rollNum = 2;
        }
        else if (15 > rollCounter) {
            rollNum = 3;
        }
        else if (20 > rollCounter) {
            rollNum = 4;
        }
        else {
            rollNum = 1;
            rollCounter = 0;
            action = Action.IDLE;
            speed = defaultSpeed;
        }
    }

    public void throwing() {
        throwCounter++;
        if (6 >= throwCounter) {
            throwNum = 1;
        }
        else {
            throwNum = 2;
        }

        if (throwCounter > 28 && gp.gameState == gp.playState) {
            action = Action.IDLE;
            throwNum = 1;
            throwCounter = 0;
        }
    }

    public void throwEntity() {

        // THROW FORWARD
        if (moving) {
            playThrowSE();
            playGruntSE_1();

            action = Action.TOSSING;
            grabbedObject.thrown = true;
            grabbedObject.grabbed = false;
            grabbedObject.tWorldY = worldY;

            switch (direction) {
                case "up":
                case "upleft":
                case "upright":
                    grabbedObject.direction = "up";
                    break;
                case "down":
                case "downleft":
                case "downright":
                    grabbedObject.direction = "down";
                    break;
                default:
                    grabbedObject.direction = direction;
                    break;
            }
        }
        // PLACE DOWN
        else {
            action = Action.TOSSING;
            grabbedObject.grabbed = false;
            grabbedObject.worldX = worldX;
            grabbedObject.worldY = worldY;
            grabbedObject.breakTile();
        }
    }

    public boolean isHoldingBomb() {
        return grabbedObject != null &&
                grabbedObject.grabbed &&
                grabbedObject.name.equals(PRJ_Bomb.prjName);
    }

    public void soaring() {

        if (60 > jumpCounter) {

            if (!collisionOn) {
                if (lockon) {
                    switch (lockonDirection) {
                        case "up":
                            worldY--;
                            break;
                        case "down":
                            worldY++;
                            break;
                        case "left":
                            worldX--;
                            break;
                        case "right":
                            worldX++;
                            break;
                    }
                }
                else {
                    switch (direction) {
                        case "up":
                            worldY--;
                            break;
                        case "down":
                            worldY++;
                            break;
                        case "left":
                            worldX--;
                            break;
                        case "right":
                            worldX++;
                            break;
                    }
                }
            }
        }
        else if (jumpCounter >= 70) {
            jumpNum = 1;
            jumpCounter = 0;
            action = Action.IDLE;
            gp.gameState = gp.playState;
        }
    }

    public void swimming() {

    }

    public void swinging() {

        rodCounter++;

        // ATTACK IMAGE 1
        if (currentItem.swingSpeed1 >= rodCounter) {
            rodNum = 1;
        }
        // ATTACK IMAGE 2
        else if (currentItem.swingSpeed2 >= rodCounter) {
            rodNum = 2;
        }
        // ATTACK IMAGE 3
        else if (currentItem.swingSpeed3 >= rodCounter) {
            rodNum = 3;
        }
        // RESET IMAGE
        else {
            rodNum = 1;
            rodCounter = 0;
            action = Action.IDLE;
        }
    }

    // DAMAGE
    public void takingDamage() {

        damageCounter++;

        if (6 >= damageCounter) {
            damageNum = 1;
        }
        else if (18 > damageCounter && 12 >= damageCounter) {
            damageNum = 2;
        }
        else if (24 > damageCounter) {
            damageNum = 3;
        }
        else if (60 > damageCounter) {
            damageNum = 4;
        }
        else if (damageCounter >= 80) {

            life -= 2;
            damageNum = 1;
            damageCounter = 0;
            transparent = true;
            canMove = true;

            if (action == Action.CARRYING) {
                grabbedObject.alive = false;
            }

            action = Action.IDLE;

            // MOVE PLAYER TO SAFE SPOT
            worldX = safeWorldX;
            worldY = safeWorldY;

            safeWorldX = defaultWorldX;
            safeWorldY = defaultWorldY;

            gp.gameState = gp.playState;
        }
    }

    public void damageEnemy(Entity target, Entity attacker, int attack, int knockbackPower) {

        if (target != null) {

            if (!target.guarded) {

                // BUZZING ENEMY
                if (target.buzzing && attacker == this) {
                    if (!attacker.invincible) {
                        target.playShockSE();
                    }
                    damagePlayer(target.attack);
                }
                else if (!target.invincible && !target.captured) {

                    // SHIELD GOBLIN BLOCKS ATTACK
                    if (target.name.equals(EMY_Goblin_Combat_Shield.emyName) &&
                            Objects.equals(direction, getOppositeDirection(target.direction)) &&
                            !target.stunned) {
                        playBlockSE();

                        setKnockback(attacker, target, target.knockbackPower);
                    }
                    else {
                        target.playHurtSE();

                        // HIT BY STUN WEAPON
                        if (attacker.canStun && !target.stunned) {
                            target.stunned = true;
                        }
                        else {
                            if (knockbackPower > 0 && target.type != type_boss) {
                                setKnockback(target, attacker, knockbackPower);
                            }
                        }

                        int damage = attack;
                        if (damage < 0) {
                            damage = 1;
                        }

                        target.life -= damage;
                        target.transparent = true;
                    }

                    target.invincible = true;
                    target.damageReaction();

                    // ENEMY DEFEATED
                    if (target.life <= 0) {
                        target.playDeathSE();
                        target.dying = true;
                    }
                }
            }
            // BEETLE ENEMY PROJECTILE DAMAGE
            else if (target.name.equals(EMY_Beetle.emyName) &&
                    (attacker.name.equals(PRJ_Boomerang.prjName) ||
                            attacker.name.equals(PRJ_Bomb.prjName))) {
                setKnockback(target, attacker, 1);
                target.attacking = true;
            }
        }
    }

    public void damageProjectile(int i) {

        if (i != -1) {
            Entity projectile = gp.projectile[gp.currentMap][i];

            if (projectile.name.equals(PRJ_Bomb.prjName)) {
                projectile.explode();
                projectile.animationSpeed = 30;
                projectile.active = false;
                projectile.alive = false;
            }
            else {
                gp.projectile[gp.currentMap][i].playSE();
                projectile.alive = false;
                generateParticle(projectile);
            }
        }
    }

    public void damageInteractiveTile(int i, Entity entity) {

        if (i != -1) {

            if (gp.iTile[gp.currentMap][i].name.equals(IT_Switch.itName) && !gp.iTile[gp.currentMap][i].invincible) {
                gp.iTile[gp.currentMap][i].invincible = true;
                gp.iTile[gp.currentMap][i].interact();
            }
            else if (!gp.iTile[gp.currentMap][i].invincible && gp.iTile[gp.currentMap][i].correctItem(entity)) {

                gp.iTile[gp.currentMap][i].playSE();

                gp.iTile[gp.currentMap][i].life--;
                gp.iTile[gp.currentMap][i].invincible = true;

                generateParticle(gp.iTile[gp.currentMap][i]);

                if (gp.iTile[gp.currentMap][i].life == 0) {
                    gp.iTile[gp.currentMap][i].checkDrop();
                    gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
                }
            }
        }
    }

    // CHECKERS
    public void manageValues() {

        // ALWAYS MAX LIFE IN DEBUG
        if (gp.keyH.debug) {
            life = maxLife;
        }

        // MANAGE LOW LIFE SE
        if (life <= 4) {
            lowHPCounter++;
            if (lowHPCounter == 90) {
                playLowHPSE();
                lowHPCounter = 0;
            }
        }
        else {
            lowHPCounter = 0;
        }

        // RESET ANIMATIONS
        if (action == Action.IDLE) {
            guardNum = 1;
            guardCounter = 0;
        }

        // KEEP ARROWS WITHIN MAX
        if (arrows > maxArrows) {
            arrows = maxArrows;
        }

        // KEEP BOMBS WITHIN MAX
        if (bombs > maxBombs) {
            bombs = maxBombs;
        }

        // KEEP HEARTS WITHIN MAX
        if (life > maxLife) {
            life = maxLife;
        }

        // PLAYER SHIELD AFTER DAMAGE
        if (invincible) {
            invincibleCounter++;

            // 1 SECOND REFRESH TIME
            if (invincibleCounter > 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }

        // DIVE FOR 1.5 SECONDS
        if (diving) {
            diveCounter++;
            if (diveCounter >= 90) {

                diving = false;
                diveCounter = 0;
            }
        }
    }

    public void checkDeath() {

        if (life <= 0 && alive) {

            for (Entity e : inventory) {
                if (e.name.equals(COL_Fairy.colName)) {
                    e.use(this);
                    return;
                }
            }

            gp.stopMusic();
            playDeathSE();
            alive = false;
            attacking = false;
            lockon = false;
            lockedTarget = null;
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = 1;
        }
    }

    // SOUND EFFECTS
    public void playGrunt() {

        // CHOOSE NEW GRUNT SE
        int grunt;
        do {
            grunt = 1 + (int) (Math.random() * 4);
        }
        while (grunt == previousGrunt);

        if (grunt == 1) {
            playGruntSE_1();
        }
        else if (grunt == 2) {
            playGruntSE_2();
        }
        else if (grunt == 3) {
            playGruntSE_3();
        }
        else if (grunt == 4) {
            playGruntSE_4();
        }

        // STORE PLAYED SE
        previousGrunt = grunt;
    }

    public void playHurt() {

        // CHOOSE NEW HURT SE
        int hurt;
        do {
            hurt = 1 + (int) (Math.random() * 3);
        }
        while (hurt == previousHurt);

        if (hurt == 1) {
            playHurtSE_1();
        }
        else if (hurt == 2) {
            playHurtSE_2();
        }
        else if (hurt == 3) {
            playHurtSE_3();
        }

        // STORE PLAYED SE
        previousHurt = hurt;
    }

    public void playSpin() {
        if (previousSwing == 2) {
            playSpinSE_1();
            previousSwing = 1;
        }
        else {
            playSpinSE_2();
            previousSwing = 2;
        }
    }

    public void playGuardSE() {
        gp.playSE(2, 1);
    }

    public void playZTargetSE() {
        gp.playSE(2, 1);
    }

    public void playGruntSE_1() {
        gp.playSE(2, 2);
    }

    public void playGruntSE_2() {
        gp.playSE(2, 3);
    }

    public void playGruntSE_3() {
        gp.playSE(2, 4);
    }

    public void playGruntSE_4() {
        gp.playSE(2, 5);
    }

    public void playHurtSE_1() {
        gp.playSE(2, 6);
    }

    public void playHurtSE_2() {
        gp.playSE(2, 7);
    }

    public void playHurtSE_3() {
        gp.playSE(2, 8);
    }

    public void playSpinSE_2() {
        gp.playSE(2, 9);
    }

    public void playSpinSE_1() {
        gp.playSE(2, 10);
    }

    public void playPullSE() {
        gp.playSE(2, 11);
    }

    public void playPushSE() {
        gp.playSE(2, 12);
    }

    public void playSwimSE() {
        gp.playSE(2, 13);
    }

    public void playFallSE() {
        gp.playSE(2, 15);
    }

    public void playLowHPSE() {
        gp.playSE(2, 16);
    }

    public void playDeathSE() {
        gp.playSE(2, 17);
    }

    public void playBlockSE() {
        gp.playSE(4, 4);
    }

    public void playThrowSE() {
        gp.playSE(4, 7);
    }

    public void playGetItemSE() {
        gp.playSE(5, 0);
    }

    // IMAGE MANAGER
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void offCenter() {

        tempScreenX = screenX;
        tempScreenY = screenY;

        if (worldX < screenX) {
            tempScreenX = worldX;
        }
        if (worldY < screenY) {
            tempScreenY = worldY;
        }

        // FROM PLAYER TO RIGHT-EDGE OF SCREEN
        int rightOffset = gp.screenWidth - screenX;

        // FROM PLAYER TO RIGHT-EDGE OF WORLD
        if (rightOffset > gp.worldWidth - worldX) {
            tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
        }

        // FROM PLAYER TO BOTTOM-EDGE OF SCREEN
        int bottomOffSet = gp.screenHeight - screenY;

        // FROM PLAYER TO BOTTOM-EDGE OF WORLD
        if (bottomOffSet > gp.worldHeight - worldY) {
            tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
        }
    }

    // DRAW HANDLER
    public void draw(Graphics2D g2) {

        if (!drawing) {
            return;
        }

        offCenter();

        if (alive) {
            switch (direction) {
                case "up":
                case "upleft":
                case "upright":
                    if (attacking && !spinning) {
                        if (attackNum == 1) {
                            image = attackUp1;
                        }
                        else if (attackNum == 2) {
                            tempScreenY -= gp.tileSize;
                            image = attackUp2;
                        }
                        else if (attackNum == 3) {
                            tempScreenY -= gp.tileSize;
                            image = attackUp3;
                        }
                    }
                    else if (spinning) {
                        tempScreenY -= gp.tileSize;
                        if (attackNum == 1) {
                            image = spinUp1;
                        }
                        else if (attackNum == 2) {
                            image = spinUp2;
                        }
                    }
                    else {
                        switch (action) {
                            case AIMING:
                                if (aimNum == 1) {
                                    image = aimUp1;
                                }
                                else if (aimNum == 2) {
                                    image = aimUp2;
                                }
                                break;
                            case CARRYING:
                                if (spriteNum == 1) {
                                    image = carryUp1;
                                }
                                else if (spriteNum == 2) {
                                    image = carryUp2;
                                }
                                break;
                            case GUARDING:
                                if (guardNum == 1) {
                                    image = guardUp1;
                                }
                                else if (guardNum == 2) {
                                    image = guardUp2;
                                }
                                break;
                            case GRABBING:
                                if (pullNum == 1) {
                                    image = grabUp1;
                                }
                                else if (pullNum == 2) {
                                    image = grabUp2;
                                }
                                else if (pullNum == 3) {
                                    image = grabUp3;
                                }
                                break;
                            case DIGGING:
                                if (digNum == 1) {
                                    image = digUp1;
                                }
                                else if (digNum == 2) {
                                    image = digUp2;
                                }
                                break;
                            case HOOKSHOT:
                                tempScreenY -= 8;
                                image = hookUp1;
                                break;
                            case JUMPING:
                            case SOARING:
                                tempScreenY -= 30;
                                if (jumpNum == 1) {
                                    image = jumpUp1;
                                }
                                else if (jumpNum == 2) {
                                    image = jumpUp2;
                                }
                                else if (jumpNum == 3) {
                                    image = jumpUp3;
                                }
                                else if (jumpNum == 4) {
                                    image = soarUp1;
                                }

                                g2.setColor(Color.BLACK);
                                g2.fillOval(tempScreenX + 10, tempScreenY + 70, 30, 10);
                                break;
                            case PUSHING:
                                if (pushNum == 1) {
                                    image = grabUp1;
                                }
                                else if (pushNum == 2) {
                                    image = grabUp2;
                                }
                                break;
                            case ROLLING:
                                if (rollNum == 1) {
                                    image = rollUp1;
                                }
                                else if (rollNum == 2) {
                                    image = rollUp2;
                                }
                                else if (rollNum == 3) {
                                    image = rollUp3;
                                }
                                else if (rollNum == 4) {
                                    image = rollUp4;
                                }
                                break;
                            case SWINGING:
                                if (rodNum == 1) {
                                    image = rodUp1;
                                }
                                else if (rodNum == 2) {
                                    tempScreenY -= gp.tileSize;
                                    image = rodUp2;
                                }
                                else if (rodNum == 3) {
                                    tempScreenY -= gp.tileSize;
                                    image = rodUp3;
                                }
                                break;
                            case SWIMMING:
                                if (spriteNum == 1) {
                                    image = swimUp1;
                                }
                                else if (spriteNum == 2) {
                                    image = swimUp2;
                                }
                                break;
                            case TOSSING:
                                image = throwUp2;
                                break;
                            case THROWING:
                                if (throwNum == 1) {
                                    image = throwUp1;
                                }
                                else if (throwNum == 2) {
                                    image = throwUp2;
                                }
                                break;
                            default:
                                if (spriteNum == 1) {
                                    image = up1;
                                }
                                else if (spriteNum == 2) {
                                    image = up2;
                                }
                                break;
                        }
                    }
                    break;
                case "down":
                case "downleft":
                case "downright":
                    if (attacking && !spinning) {
                        if (attackNum == 1) {
                            tempScreenX -= gp.tileSize;
                            image = attackDown1;
                        }
                        else if (attackNum == 2) {
                            tempScreenX -= gp.tileSize;
                            image = attackDown2;
                        }
                        else if (attackNum == 3) {
                            image = attackDown3;
                        }
                    }
                    else if (spinning) {
                        if (attackNum == 1) {
                            tempScreenX -= gp.tileSize;
                            image = spinDown1;
                        }
                        else if (attackNum == 2) {
                            image = spinDown2;
                        }
                    }
                    else {
                        switch (action) {
                            case AIMING:
                                if (aimNum == 1) {
                                    image = aimDown1;
                                }
                                else if (aimNum == 2) {
                                    image = aimDown2;
                                }
                                break;
                            case CARRYING:
                                if (spriteNum == 1) {
                                    image = carryDown1;
                                }
                                else if (spriteNum == 2) {
                                    image = carryDown2;
                                }
                                break;
                            case GUARDING:
                                if (guardNum == 1) {
                                    image = guardDown1;
                                }
                                else if (guardNum == 2) {
                                    image = guardDown2;
                                }
                                break;
                            case GRABBING:
                                if (pullNum == 1) {
                                    image = grabDown1;
                                }
                                else if (pullNum == 2) {
                                    image = grabDown2;
                                }
                                else if (pullNum == 3) {
                                    image = grabDown3;
                                }
                                break;
                            case DIGGING:
                                if (digNum == 1) {
                                    image = digDown1;
                                }
                                else if (digNum == 2) {
                                    image = digDown2;
                                }
                                break;
                            case HOOKSHOT:
                                image = hookDown1;
                                break;
                            case JUMPING:
                            case SOARING:
                                tempScreenY -= 30;
                                if (jumpNum == 1) {
                                    image = jumpDown1;
                                }
                                else if (jumpNum == 2) {
                                    image = jumpDown2;
                                }
                                else if (jumpNum == 3) {
                                    image = jumpDown3;
                                }
                                else if (jumpNum == 4) {
                                    image = soarDown1;
                                }

                                g2.setColor(Color.BLACK);
                                g2.fillOval(tempScreenX + 10, tempScreenY + 70, 30, 10);
                                break;
                            case PUSHING:
                                if (pushNum == 1) {
                                    image = grabDown1;
                                }
                                else if (pushNum == 2) {
                                    image = grabDown2;
                                }
                                break;
                            case ROLLING:
                                if (rollNum == 1) {
                                    image = rollDown1;
                                }
                                else if (rollNum == 2) {
                                    image = rollDown2;
                                }
                                else if (rollNum == 3) {
                                    image = rollDown3;
                                }
                                else if (rollNum == 4) {
                                    image = rollDown4;
                                }
                                break;
                            case SWINGING:
                                if (rodNum == 1) {
                                    tempScreenX -= gp.tileSize;
                                    image = rodDown1;
                                }
                                else if (rodNum == 2) {
                                    tempScreenX -= gp.tileSize;
                                    image = rodDown2;
                                }
                                else if (rodNum == 3) {
                                    image = rodDown3;
                                }
                                break;
                            case SWIMMING:
                                if (spriteNum == 1) {
                                    image = swimDown1;
                                }
                                else if (spriteNum == 2) {
                                    image = swimDown2;
                                }
                                break;
                            case TOSSING:
                                image = throwDown2;
                                break;
                            case THROWING:
                                if (throwNum == 1) {
                                    image = throwDown1;
                                }
                                else if (throwNum == 2) {
                                    image = throwDown2;
                                }
                                break;
                            default:
                                if (spriteNum == 1) {
                                    image = down1;
                                }
                                else if (spriteNum == 2) {
                                    image = down2;
                                }
                                break;
                        }
                    }
                    break;
                case "left":
                    if (attacking && !spinning) {
                        if (attackNum == 1) {
                            tempScreenY -= gp.tileSize;
                            image = attackLeft1;
                        }
                        else if (attackNum == 2) {
                            tempScreenX -= gp.tileSize;
                            tempScreenY -= gp.tileSize;
                            image = attackLeft2;
                        }
                        else if (attackNum == 3) {
                            tempScreenX -= gp.tileSize;
                            image = attackLeft3;
                        }
                    }
                    else if (spinning) {
                        tempScreenX -= gp.tileSize;
                        if (attackNum == 1) {
                            tempScreenY -= gp.tileSize;
                            image = spinLeft1;
                        }
                        else if (attackNum == 2) {
                            image = spinLeft2;
                        }
                    }
                    else {
                        switch (action) {
                            case AIMING:
                                if (aimNum == 1) {
                                    image = aimLeft1;
                                }
                                else if (aimNum == 2) {
                                    image = aimLeft2;
                                }
                                break;
                            case CARRYING:
                                if (spriteNum == 1) {
                                    image = carryLeft1;
                                }
                                else if (spriteNum == 2) {
                                    image = carryLeft2;
                                }
                                break;
                            case GUARDING:
                                if (guardNum == 1) {
                                    image = guardLeft1;
                                }
                                else if (guardNum == 2) {
                                    image = guardLeft2;
                                }
                                break;
                            case GRABBING:
                                if (pullNum == 1) {
                                    image = grabLeft1;
                                }
                                else if (pullNum == 2) {
                                    image = grabLeft2;
                                }
                                else if (pullNum == 3) {
                                    image = grabLeft3;
                                }
                                break;
                            case DIGGING:
                                if (digNum == 1) {
                                    image = digLeft1;
                                }
                                else if (digNum == 2) {
                                    image = digLeft2;
                                }
                                break;
                            case HOOKSHOT:
                                tempScreenX -= 8;
                                image = hookLeft1;
                                break;
                            case JUMPING:
                            case SOARING:
                                tempScreenY -= 30;
                                if (jumpNum == 1) {
                                    image = jumpLeft1;
                                }
                                else if (jumpNum == 2) {
                                    image = jumpLeft2;
                                }
                                else if (jumpNum == 3) {
                                    image = jumpLeft3;
                                }
                                else if (jumpNum == 4) {
                                    image = soarLeft1;
                                }

                                g2.setColor(Color.BLACK);
                                g2.fillOval(tempScreenX + 10, tempScreenY + 70, 30, 10);
                                break;
                            case PUSHING:
                                if (pushNum == 1) {
                                    image = grabLeft1;
                                }
                                else if (pushNum == 2) {
                                    image = grabLeft2;
                                }
                                break;
                            case ROLLING:
                                if (rollNum == 1) {
                                    image = rollLeft1;
                                }
                                else if (rollNum == 2) {
                                    image = rollLeft2;
                                }
                                else if (rollNum == 3) {
                                    image = rollLeft3;
                                }
                                else if (rollNum == 4) {
                                    image = rollLeft4;
                                }
                                break;
                            case SWINGING:
                                if (rodNum == 1) {
                                    tempScreenY -= gp.tileSize;
                                    image = rodLeft1;
                                }
                                else if (rodNum == 2) {
                                    tempScreenX -= gp.tileSize;
                                    tempScreenY -= gp.tileSize;
                                    image = rodLeft2;
                                }
                                else if (rodNum == 3) {
                                    tempScreenX -= gp.tileSize;
                                    image = rodLeft3;
                                }
                                break;
                            case SWIMMING:
                                if (spriteNum == 1) {
                                    image = swimLeft1;
                                }
                                else if (spriteNum == 2) {
                                    image = swimLeft2;
                                }
                                break;
                            case TOSSING:
                                image = throwLeft2;
                                break;
                            case THROWING:
                                if (throwNum == 1) {
                                    image = throwLeft1;
                                }
                                else if (throwNum == 2) {
                                    image = throwLeft2;
                                }
                                break;
                            default:
                                if (spriteNum == 1) {
                                    image = left1;
                                }
                                else if (spriteNum == 2) {
                                    image = left2;
                                }
                                break;
                        }
                    }
                    break;
                case "right":
                    if (attacking && !spinning) {
                        if (attackNum == 1) {
                            tempScreenY -= gp.tileSize;
                            image = attackRight1;
                        }
                        else if (attackNum == 2) {
                            tempScreenY -= gp.tileSize;
                            image = attackRight2;
                        }
                        else if (attackNum == 3) {
                            image = attackRight3;
                        }
                    }
                    else if (spinning) {
                        if (attackNum == 1) {
                            image = spinRight1;
                        }
                        else if (attackNum == 2) {
                            image = spinRight2;
                        }
                    }
                    else {
                        switch (action) {
                            case AIMING:
                                if (aimNum == 1) {
                                    image = aimRight1;
                                }
                                else if (aimNum == 2) {
                                    image = aimRight2;
                                }
                                break;
                            case CARRYING:
                                if (spriteNum == 1) {
                                    image = carryRight1;
                                }
                                else if (spriteNum == 2) {
                                    image = carryRight2;
                                }
                                break;
                            case GUARDING:
                                if (guardNum == 1) {
                                    image = guardRight1;
                                }
                                else if (guardNum == 2) {
                                    image = guardRight2;
                                }
                                break;
                            case GRABBING:
                                if (pullNum == 1) {
                                    image = grabRight1;
                                }
                                else if (pullNum == 2) {
                                    image = grabRight2;
                                }
                                else if (pullNum == 3) {
                                    image = grabRight3;
                                }
                                break;
                            case DIGGING:
                                if (digNum == 1) {
                                    image = digRight1;
                                }
                                else if (digNum == 2) {
                                    image = digRight2;
                                }
                                break;
                            case HOOKSHOT:
                                image = hookRight1;
                                break;
                            case JUMPING:
                            case SOARING:
                                tempScreenY -= 30;
                                if (jumpNum == 1) {
                                    image = jumpRight1;
                                }
                                else if (jumpNum == 2) {
                                    image = jumpRight2;
                                }
                                else if (jumpNum == 3) {
                                    image = jumpRight3;
                                }
                                else if (jumpNum == 4) {
                                    image = soarRight1;
                                }

                                g2.setColor(Color.BLACK);
                                g2.fillOval(tempScreenX + 10, tempScreenY + 70, 30, 10);
                                break;
                            case PUSHING:
                                if (pushNum == 1) {
                                    image = grabRight1;
                                }
                                else if (pushNum == 2) {
                                    image = grabRight2;
                                }
                                break;
                            case ROLLING:
                                if (rollNum == 1) {
                                    image = rollRight1;
                                }
                                else if (rollNum == 2) {
                                    image = rollRight2;
                                }
                                else if (rollNum == 3) {
                                    image = rollRight3;
                                }
                                else if (rollNum == 4) {
                                    image = rollRight4;
                                }
                                break;
                            case SWINGING:
                                if (rodNum == 1) {
                                    tempScreenY -= gp.tileSize;
                                    image = rodRight1;
                                }
                                else if (rodNum == 2) {
                                    tempScreenY -= gp.tileSize;
                                    image = rodRight2;
                                }
                                else if (rodNum == 3) {
                                    image = rodRight3;
                                }
                                break;
                            case SWIMMING:
                                if (spriteNum == 1) {
                                    image = swimRight1;
                                }
                                else if (spriteNum == 2) {
                                    image = swimRight2;
                                }
                                break;
                            case TOSSING:
                                image = throwRight2;
                                break;
                            case THROWING:
                                if (throwNum == 1) {
                                    image = throwRight1;
                                }
                                else if (throwNum == 2) {
                                    image = throwRight2;
                                }
                                break;
                            default:
                                if (spriteNum == 1) {
                                    image = right1;
                                }
                                else if (spriteNum == 2) {
                                    image = right2;
                                }
                                break;
                        }
                    }
                    break;
            }


            // PLAYER HIT, FLASH OPACITY
            if (transparent) {
                if (invincibleCounter % 5 == 0) {
                    changeAlpha(g2, 0.2f);
                }
            }
        }
        if (action == Action.PLAYING) {

            if (playNum == 1) {
                image = play1;
            }
            else if (playNum == 2) {
                image = play2;
            }
            else if (playNum == 3) {
                image = play3;
            }

            offCenter();
        }
        else if (action == Action.DROWNING || diving) {
            image = drown;
            offCenter();
        }
        else if (action == Action.FALLING) {
            if (damageNum == 1) {
                image = fall1;
            }
            else if (damageNum == 2) {
                image = fall2;
            }
            else if (damageNum == 3) {
                image = fall3;
            }
            else if (damageNum == 4) {
                image = null;
            }

            tempScreenX = screenX;
            tempScreenY = screenY;
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET OPACITY
        changeAlpha(g2, 1f);
    }
}
/**
 * END PLAYER METHODS
 **/

/**
 * END PLAYER CLASS
 **/