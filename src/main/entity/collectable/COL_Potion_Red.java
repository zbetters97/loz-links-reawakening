package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Potion_Red extends Entity implements Cloneable {

    public static final String colName = "Red Potion";

    public COL_Potion_Red(GamePanel gp) {
        super(gp);

        type = type_consumable;
        name = colName;
        description = "[Red Potion]\nHeals three hearts.";
        value = 12;
        price = 20;
        stackable = true;

        setDialogue();
    }

    public void getImage() {
        image = setup("/collectables/potion_red");
        down1 = setup("/collectables/potion_red", 35, 35);
    }

    public void setDialogue() {
        dialogues[0][0] = "Ah... you feel regenerated a little bit!";
    }

    public boolean use(Entity user) {
        user.life += value;
        if (user.life > user.maxLife) {
            user.life = user.maxLife;
        }
        startDialogue(this, 0);
        return true;
    }
}