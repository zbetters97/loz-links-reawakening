package data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage implements Serializable {

    @Serial
    private static final long serialVersionUID = -5792031433632402979L;

    // FILE INFO
    String file_date;
    boolean gameCompleted;

    // DAY STATE
    int dayState, dayCounter, bloodMoonCounter;
    float filterAlpha;

    // PROGRESS
    boolean enemy_room_1_1, enemy_room_1_2, enemy_room_1_3, puzzle_1_1, bossDefeated_1_1, bossDefeated_1_2;

    // PLAYER DATA
    int cMap, cArea, pWorldX, pWorldY;
    String name, direction;
    int maxLife, life, attack;
    int rupees, maxArrows, arrows, maxBombs, bombs;
    boolean canSwim;
    String sword, shield, item;

    // PLAYER COLLECTABLES
    ArrayList<String> colNames = new ArrayList<>();
    ArrayList<Integer> colAmounts = new ArrayList<>();

    // PLAYER ITEMS
    ArrayList<String> itemNames = new ArrayList<>();

    // NPCs
    String[][] npcNames;
    int[][] npcWorldX, npcWorldY, npcDialogueSet, npcGoalCol, npcGoalRow;
    boolean[][] npcHasCutscene, npcOnPath, npcHasItem, npcDrawing;
    Map<String, List<String>> npcInventory = new HashMap<>();

    // ENEMIES
    int[][] enemyWorldX, enemyWorldY, enemyLife, enemyGoalCol, enemyGoalRow;
    boolean[][] enemyAlive, enemyOnPath, enemyAsleep;

    // MAP OBJECTS
    String[][] mapObjectNames, mapObjectDirections, mapObjectLootNames;
    int[][] mapObjectWorldX, mapObjectWorldY;
    boolean[][] mapObjectSwitchedOn, mapObjectOpened;

    // iTILES
    String[][] iTileNames, iTileDirections, iTileLootNames;
    int[][] iTileWorldX, iTileWorldY;
    boolean[][] iTileSwitchedOn;

    public String toString() {
        return "[" + name + "]  " + file_date;
    }
}