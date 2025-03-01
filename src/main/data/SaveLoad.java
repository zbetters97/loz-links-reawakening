package data;

import application.GamePanel;
import entity.Entity;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SaveLoad {

    public String[] saveFiles = {
            File.separator + "save_1.dat",
            File.separator + "save_2.dat",
            File.separator + "save_3.dat",
            File.separator + "save_auto.dat"
    };
    public boolean ready = true;
    private final GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save(int saveSlot) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(gp.saveDir + saveFiles[saveSlot]));

            // SAVE DATA TO DS
            DataStorage ds = new DataStorage();

            // CURRENT DATE/TIME
            ds.file_date = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));

            // DAY DATA
            ds.dayState = gp.eManager.lighting.dayState;
            ds.dayCounter = gp.eManager.lighting.dayCounter;
            ds.filterAlpha = gp.eManager.lighting.filterAlpha;
            ds.bloodMoonCounter = gp.eManager.lighting.bloodMoonCounter;

            // PROGRESS DATA
            ds.enemy_room_1_1 = Progress.enemy_room_1_1;
            ds.enemy_room_1_2 = Progress.enemy_room_1_2;
            ds.enemy_room_1_3 = Progress.enemy_room_1_3;
            ds.puzzle_1_1 = Progress.puzzle_1_1;
            ds.bossDefeated_1_1 = Progress.bossDefeated_1_1;
            ds.bossDefeated_1_2 = Progress.bossDefeated_1_2;
            ds.gameCompleted = Progress.gameCompleted;

            // PLAYER DATA
            ds.cMap = gp.currentMap;
            ds.pWorldX = gp.player.worldX;
            ds.pWorldY = gp.player.worldY;
            ds.cArea = gp.currentArea;

            ds.name = gp.player.name;
            ds.direction = gp.player.direction;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.attack = gp.player.attack;

            ds.rupees = gp.ui.rupeeCount;
            ds.maxArrows = gp.player.maxArrows;
            ds.arrows = gp.player.arrows;
            ds.maxBombs = gp.player.maxBombs;
            ds.bombs = gp.player.bombs;

            // PLAYER COLLECTABLES
            for (Entity collectable : gp.player.inventory) {
                ds.colNames.add(collectable.name);
                ds.colAmounts.add(collectable.amount);
            }

            // PLAYER ITEMS
            for (Entity item : gp.player.inventory_item) {
                ds.itemNames.add(item.name);
            }

            // PLAYER EQUIPMENT
            ds.canSwim = gp.player.canSwim;
            if (gp.player.currentWeapon != null) {
                ds.sword = gp.player.currentWeapon.name;
            }
            if (gp.player.currentShield != null) {
                ds.shield = gp.player.currentShield.name;
            }
            if (gp.player.currentItem != null) {
                ds.item = gp.player.currentItem.name;
            }

            // NPCs
            ds.npcNames = new String[gp.maxMap][gp.npc[1].length];
            ds.npcWorldX = new int[gp.maxMap][gp.npc[1].length];
            ds.npcWorldY = new int[gp.maxMap][gp.npc[1].length];
            ds.npcGoalCol = new int[gp.maxMap][gp.npc[1].length];
            ds.npcGoalRow = new int[gp.maxMap][gp.npc[1].length];
            ds.npcDialogueSet = new int[gp.maxMap][gp.npc[1].length];
            ds.npcHasCutscene = new boolean[gp.maxMap][gp.npc[1].length];
            ds.npcOnPath = new boolean[gp.maxMap][gp.npc[1].length];
            ds.npcHasItem = new boolean[gp.maxMap][gp.npc[1].length];
            ds.npcDrawing = new boolean[gp.maxMap][gp.npc[1].length];

            // ENEMIES
            ds.enemyWorldX = new int[gp.maxMap][gp.enemy[1].length];
            ds.enemyWorldY = new int[gp.maxMap][gp.enemy[1].length];
            ds.enemyGoalCol = new int[gp.maxMap][gp.enemy[1].length];
            ds.enemyGoalRow = new int[gp.maxMap][gp.enemy[1].length];
            ds.enemyLife = new int[gp.maxMap][gp.enemy[1].length];
            ds.enemyAlive = new boolean[gp.maxMap][gp.enemy[1].length];
            ds.enemyOnPath = new boolean[gp.maxMap][gp.enemy[1].length];
            ds.enemyAsleep = new boolean[gp.maxMap][gp.enemy[1].length];

            // MAP OBJECTS
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectDirections = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectSwitchedOn = new boolean[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            // MAP iTILES
            ds.iTileNames = new String[gp.maxMap][gp.iTile[1].length];
            ds.iTileWorldX = new int[gp.maxMap][gp.iTile[1].length];
            ds.iTileWorldY = new int[gp.maxMap][gp.iTile[1].length];
            ds.iTileDirections = new String[gp.maxMap][gp.iTile[1].length];
            ds.iTileSwitchedOn = new boolean[gp.maxMap][gp.iTile[1].length];
            ds.iTileLootNames = new String[gp.maxMap][gp.iTile[1].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                // NPCs
                for (int i = 0; i < gp.npc[1].length; i++) {

                    if (gp.npc[mapNum][i] == null) {
                        ds.npcNames[mapNum][i] = "NULL";
                    }
                    else {
                        ds.npcNames[mapNum][i] = gp.npc[mapNum][i].name;
                        ds.npcWorldX[mapNum][i] = gp.npc[mapNum][i].worldX;
                        ds.npcWorldY[mapNum][i] = gp.npc[mapNum][i].worldY;
                        ds.npcGoalCol[mapNum][i] = gp.npc[mapNum][i].goalCol;
                        ds.npcGoalRow[mapNum][i] = gp.npc[mapNum][i].goalRow;
                        ds.npcDialogueSet[mapNum][i] = gp.npc[mapNum][i].dialogueSet;
                        ds.npcHasCutscene[mapNum][i] = gp.npc[mapNum][i].hasCutscene;
                        ds.npcOnPath[mapNum][i] = gp.npc[mapNum][i].onPath;
                        ds.npcHasItem[mapNum][i] = gp.npc[mapNum][i].hasItemToGive;
                        ds.npcDrawing[mapNum][i] = gp.npc[mapNum][i].drawing;

                        // IF NPC HAS ITEMS
                        if (!gp.npc[mapNum][i].inventory.isEmpty()) {

                            List<String> items = new ArrayList<>();

                            // LOOP THROUGH INVENTORY
                            for (int c = 0; c < gp.npc[mapNum][i].inventory.size(); c++) {
                                if (gp.npc[mapNum][i].inventory.get(c) != null) {
                                    items.add(gp.npc[mapNum][i].inventory.get(c).name);
                                }
                            }

                            // KEY: NPC NAME, VALUE: ITEMS LIST
                            ds.npcInventory.put(gp.npc[mapNum][i].name, items);
                        }
                    }
                }

                // ENEMIES
                for (int i = 0; i < gp.enemy[1].length; i++) {
                    if (gp.enemy[mapNum][i] == null) {
                        ds.enemyAlive[mapNum][i] = false;
                    }
                    else {
                        ds.enemyWorldX[mapNum][i] = gp.enemy[mapNum][i].worldX;
                        ds.enemyWorldY[mapNum][i] = gp.enemy[mapNum][i].worldY;
                        ds.enemyGoalCol[mapNum][i] = gp.enemy[mapNum][i].goalCol;
                        ds.enemyGoalRow[mapNum][i] = gp.enemy[mapNum][i].goalRow;
                        ds.enemyLife[mapNum][i] = gp.enemy[mapNum][i].life;
                        ds.enemyAlive[mapNum][i] = true;
                        ds.enemyOnPath[mapNum][i] = gp.enemy[mapNum][i].onPath;
                        ds.enemyAsleep[mapNum][i] = gp.enemy[mapNum][i].sleep;
                    }
                }

                // MAP OBJECTS
                for (int i = 0; i < gp.obj[1].length; i++) {

                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "NULL";
                    }
                    else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        ds.mapObjectDirections[mapNum][i] = gp.obj[mapNum][i].direction;
                        ds.mapObjectSwitchedOn[mapNum][i] = gp.obj[mapNum][i].switchedOn;

                        if (gp.obj[mapNum][i].loot != null) {
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }

                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }

                // MAP iTILES
                for (int i = 0; i < gp.iTile[1].length; i++) {

                    if (gp.iTile[mapNum][i] == null) {
                        ds.iTileNames[mapNum][i] = "NULL";
                    }
                    else {
                        ds.iTileNames[mapNum][i] = gp.iTile[mapNum][i].name;
                        ds.iTileWorldX[mapNum][i] = gp.iTile[mapNum][i].worldX;
                        ds.iTileWorldY[mapNum][i] = gp.iTile[mapNum][i].worldY;
                        ds.iTileDirections[mapNum][i] = gp.iTile[mapNum][i].direction;
                        ds.iTileSwitchedOn[mapNum][i] = gp.iTile[mapNum][i].switchedOn;

                        if (gp.iTile[mapNum][i].loot != null) {
                            ds.iTileLootNames[mapNum][i] = gp.iTile[mapNum][i].loot.name;
                        }
                    }
                }
            }

            // WRITE THE DS OBJECT
            oos.writeObject(ds);
            oos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(int saveSlot) {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(gp.saveDir + saveFiles[saveSlot]));

            // LOAD DATA FROM DS
            DataStorage ds = (DataStorage) ois.readObject();

            gp.eHandler.canTouchEvent = false;

            // FILE DATA
            Progress.gameCompleted = ds.gameCompleted;

            // DAY DATA
            gp.eManager.lighting.dayState = ds.dayState;
            gp.eManager.lighting.dayCounter = ds.dayCounter;
            gp.eManager.lighting.filterAlpha = ds.filterAlpha;
            gp.eManager.lighting.bloodMoonCounter = ds.bloodMoonCounter;

            // PROGRESS DATA
            Progress.enemy_room_1_1 = ds.enemy_room_1_1;
            Progress.enemy_room_1_2 = ds.enemy_room_1_2;
            Progress.enemy_room_1_3 = ds.enemy_room_1_3;
            Progress.puzzle_1_1 = ds.puzzle_1_1;
            Progress.bossDefeated_1_1 = ds.bossDefeated_1_1;
            Progress.bossDefeated_1_2 = ds.bossDefeated_1_2;

            // PLAYER DATA
            gp.currentMap = ds.cMap;
            gp.currentArea = ds.cArea;
            gp.player.worldX = ds.pWorldX;
            gp.player.worldY = ds.pWorldY;

            gp.player.name = ds.name;
            gp.player.direction = ds.direction;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.attack = ds.attack;

            gp.player.rupees = ds.rupees;
            gp.ui.rupeeCount = gp.player.rupees;
            gp.player.maxArrows = ds.maxArrows;
            gp.player.arrows = ds.arrows;
            gp.player.maxBombs = ds.maxBombs;
            gp.player.bombs = ds.bombs;

            // PLAYER COLLECTABLES
            gp.player.inventory.clear();
            for (int i = 0; i < ds.colNames.size(); i++) {
                gp.player.inventory.add(gp.eGenerator.getItem(ds.colNames.get(i)));
                gp.player.inventory.get(i).amount = ds.colAmounts.get(i);
            }

            // PLAYER ITEMS
            gp.player.inventory_item.clear();
            for (int i = 0; i < ds.itemNames.size(); i++) {
                gp.player.inventory_item.add(gp.eGenerator.getItem(ds.itemNames.get(i)));
            }

            // FIND AND ASSIGN CURRENT ITEM IN INVENTORY
            if (ds.item != null) {
                for (int i = 0; i < gp.player.inventory_item.size(); i++) {
                    if (gp.player.inventory_item.get(i).name.equals(ds.item)) {
                        gp.player.currentItem = gp.player.inventory_item.get(i);
                    }
                }
            }

            // PLAYER EQUIPMENT
            gp.player.currentWeapon = gp.eGenerator.getItem(ds.sword);
            gp.player.currentShield = gp.eGenerator.getItem(ds.shield);

            gp.player.getAttack();
            gp.player.getAttackImage();
            gp.player.getSpinImage();
            gp.player.canSwim = ds.canSwim;

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                // NPCs
                for (int i = 0; i < gp.npc[1].length; i++) {

                    if (ds.npcNames[mapNum][i].equals("NULL")) {
                        gp.npc[mapNum][i] = null;
                    }
                    else if (gp.npc[mapNum][i] != null) {
                        gp.npc[mapNum][i].worldX = ds.npcWorldX[mapNum][i];
                        gp.npc[mapNum][i].worldY = ds.npcWorldY[mapNum][i];
                        gp.npc[mapNum][i].goalCol = ds.npcGoalCol[mapNum][i];
                        gp.npc[mapNum][i].goalRow = ds.npcGoalRow[mapNum][i];
                        gp.npc[mapNum][i].dialogueSet = ds.npcDialogueSet[mapNum][i];
                        gp.npc[mapNum][i].hasCutscene = ds.npcHasCutscene[mapNum][i];
                        gp.npc[mapNum][i].onPath = ds.npcOnPath[mapNum][i];
                        gp.npc[mapNum][i].hasItemToGive = ds.npcHasItem[mapNum][i];
                        gp.npc[mapNum][i].drawing = ds.npcDrawing[mapNum][i];

                        // RESET INVENTORY
                        gp.npc[mapNum][i].inventory.clear();

                        // LOOP THROUGH NPC NAMES IN NPC INVENTORY
                        for (String name : ds.npcInventory.keySet()) {

                            // IF NAME FOUND IN KEY-VALUE PAIR
                            if (gp.npc[mapNum][i].name.equals(name)) {

                                // GET ITEMS FROM KEY-VALUE AND ADD TO INVENTORY
                                List<String> items = ds.npcInventory.get(name);
                                for (String item : items) {
                                    gp.npc[mapNum][i].inventory.add(gp.eGenerator.getItem(item));
                                }
                            }
                        }
                    }
                }

                // ENEMIES
                for (int i = 0; i < gp.enemy[1].length; i++) {
                    if (!ds.enemyAlive[mapNum][i]) {
                        gp.enemy[mapNum][i] = null;
                    }
                    else if (gp.enemy[mapNum][i] != null) {
                        gp.enemy[mapNum][i].worldX = ds.enemyWorldX[mapNum][i];
                        gp.enemy[mapNum][i].worldY = ds.enemyWorldY[mapNum][i];
                        gp.enemy[mapNum][i].goalCol = ds.enemyGoalCol[mapNum][i];
                        gp.enemy[mapNum][i].goalRow = ds.enemyGoalRow[mapNum][i];
                        gp.enemy[mapNum][i].life = ds.enemyLife[mapNum][i];
                        gp.enemy[mapNum][i].sleep = ds.enemyAsleep[mapNum][i];
                        gp.enemy[mapNum][i].onPath = ds.enemyOnPath[mapNum][i];
                        gp.enemy[mapNum][i].attacking = false;

                        if (gp.enemy[mapNum][i].type == gp.enemy[mapNum][i].type_boss) {
                            gp.enemy[mapNum][i].resetValues();
                        }
                    }
                }

                // MAP OBJECTS
                for (int i = 0; i < gp.obj[1].length; i++) {

                    if (ds.mapObjectNames[mapNum][i].equals("NULL")) {
                        gp.obj[mapNum][i] = null;
                    }
                    else if (gp.obj[mapNum][i] != null) {

                        // IF MAP OBJECT IS COLLECTABLE / EQUIPMENT / ITEM
                        if (gp.eGenerator.getObject(
                                ds.mapObjectNames[mapNum][i],
                                ds.mapObjectWorldX[mapNum][i] * gp.tileSize,
                                ds.mapObjectWorldY[mapNum][i] * gp.tileSize
                        ) == null) {
                            gp.obj[mapNum][i] = gp.eGenerator.getItem(ds.mapObjectNames[mapNum][i]);
                            gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                            gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        }
                        // MAP OBJECT IS REGULAR OBJECT
                        else {
                            gp.obj[mapNum][i] = gp.eGenerator.getObject(
                                    ds.mapObjectNames[mapNum][i],
                                    ds.mapObjectWorldX[mapNum][i] / gp.tileSize,
                                    ds.mapObjectWorldY[mapNum][i] / gp.tileSize
                            );
                        }

                        gp.obj[mapNum][i].direction = ds.mapObjectDirections[mapNum][i];
                        gp.obj[mapNum][i].switchedOn = ds.mapObjectSwitchedOn[mapNum][i];

                        if (ds.mapObjectLootNames[mapNum][i] != null) {
                            gp.obj[mapNum][i].setLoot(gp.eGenerator.getItem(ds.mapObjectLootNames[mapNum][i]));
                        }

                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if (gp.obj[mapNum][i].opened) {
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }

                    }
                }

                // MAP iTILES
                for (int i = 0; i < gp.iTile[1].length; i++) {

                    if (ds.iTileNames[mapNum][i].equals("NULL")) {
                        gp.iTile[mapNum][i] = null;
                    }
                    else if (gp.iTile[mapNum][i] != null) {
                        gp.iTile[mapNum][i] = gp.iGenerator.getTile(ds.iTileNames[mapNum][i]);
                        gp.iTile[mapNum][i].worldX = ds.iTileWorldX[mapNum][i];
                        gp.iTile[mapNum][i].worldY = ds.iTileWorldY[mapNum][i];
                        gp.iTile[mapNum][i].direction = ds.iTileDirections[mapNum][i];
                        gp.iTile[mapNum][i].switchedOn = ds.iTileSwitchedOn[mapNum][i];

                        if (ds.iTileLootNames[mapNum][i] != null) {
                            gp.iTile[mapNum][i].setLoot(gp.eGenerator.getItem(ds.iTileLootNames[mapNum][i]));
                        }
                    }
                }
            }

            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int saveSlot) {

        try {
            File f = new File(saveFiles[saveSlot]);

            if (f.exists()) {
                f.delete();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String loadFileData(int saveSlot) {

        try {
            File saveFile = new File(gp.saveDir + saveFiles[saveSlot]);

            if (saveFile.exists()) {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile));

                DataStorage ds = (DataStorage) ois.readObject();
                ois.close();

                return ds.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}