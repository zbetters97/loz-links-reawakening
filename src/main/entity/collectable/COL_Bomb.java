package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Bomb extends Entity {

    public static final String colName = "Bomb Collectable";

    public COL_Bomb(GamePanel gp) {
        super(gp);

        collision = false;

        type = type_collectable;
        name = colName;
        value = 1;
        lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
    }

    public void getImage() {
        image = down1 = setup("/collectables/bomb");
    }

    public boolean use(Entity user) {
        playSE();
        user.bombs += value;
        return true;
    }

    public void playSE() {
        gp.playSE(6, 2);
    }
}