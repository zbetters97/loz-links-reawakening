package application;

import tile.tile_interactive.*;

public class iTileGenerator {

    private final GamePanel gp;

    protected iTileGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public InteractiveTile getTile(String itemName) {

        InteractiveTile iTile = switch (itemName) {
            case IT_Block_Blue.itName -> new IT_Block_Blue(gp);
            case IT_Block_Red.itName -> new IT_Block_Red(gp);
            case IT_DigSpot.itName -> new IT_DigSpot(gp);
            case IT_Hole.itName -> new IT_Hole(gp);
            case IT_Plate_Metal.itName -> new IT_Plate_Metal(gp);
            case IT_Pot.itName -> new IT_Pot(gp);
            case IT_Switch.itName -> new IT_Switch(gp);
            case IT_Wall_01.itName -> new IT_Wall_01(gp);
            case IT_Wall.itName -> new IT_Wall(gp);
            default -> null;
        };

        return iTile;
    }
}