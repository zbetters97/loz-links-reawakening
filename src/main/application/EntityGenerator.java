package application;

import entity.Entity;
import entity.collectable.*;
import entity.equipment.EQP_Flippers;
import entity.equipment.EQP_Shield;
import entity.equipment.EQP_Sword_Master;
import entity.equipment.EQP_Sword_Old;
import entity.item.*;
import entity.object.*;
import entity.object.object_interactive.OI_Block_Pushable;

public class EntityGenerator {

    private final GamePanel gp;

    protected EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getItem(String itemName) {

        Entity obj = switch (itemName) {
            case COL_Arrow.colName -> new COL_Arrow(gp);
            case COL_Bomb.colName -> new COL_Bomb(gp);
            case COL_Fairy.colName -> new COL_Fairy(gp);
            case COL_Heart.colName -> new COL_Heart(gp);
            case COL_Key.keyName -> new COL_Key(gp);
            case COL_Key_Boss.keyName -> new COL_Key_Boss(gp);
            case COL_Potion_Red.colName -> new COL_Potion_Red(gp);
            case COL_Rupee_Blue.colName -> new COL_Rupee_Blue(gp);
            case COL_Rupee_Green.colName -> new COL_Rupee_Green(gp);
            case COL_Rupee_Red.colName -> new COL_Rupee_Red(gp);
            case ITM_Bomb.itmName -> new ITM_Bomb(gp);
            case ITM_Boomerang.itmName -> new ITM_Boomerang(gp);
            case ITM_Boots.itmName -> new ITM_Boots(gp);
            case ITM_Bow.itmName -> new ITM_Bow(gp);
            case ITM_Cape.itmName -> new ITM_Cape(gp);
            case ITM_Feather.itmName -> new ITM_Feather(gp);
            case ITM_Harp.itmName -> new ITM_Harp(gp);
            case ITM_Hookshot.itmName -> new ITM_Hookshot(gp);
            case ITM_Rod.itmName -> new ITM_Rod(gp);
            case ITM_Shovel.itmName -> new ITM_Shovel(gp);
            case EQP_Shield.eqpName -> new EQP_Shield(gp);
            case EQP_Sword_Old.eqpName -> new EQP_Sword_Old(gp);
            case EQP_Sword_Master.eqpName -> new EQP_Sword_Master(gp);
            case EQP_Flippers.eqpName -> new EQP_Flippers(gp);
            default -> null;
        };

        return obj;
    }

    public Entity getObject(String itemName, int worldX, int worldY) {

        Entity obj = switch (itemName) {
            case OBJ_BlueHeart.objName -> new OBJ_BlueHeart(gp, worldX, worldY);
            case OBJ_Block_Locked.objName -> new OBJ_Block_Locked(gp, worldX, worldY);
            case OBJ_Chest.objName -> new OBJ_Chest(gp, worldX, worldY);
            case OBJ_Door_Boss.objName -> new OBJ_Door_Boss(gp, worldX, worldY);
            case OBJ_Door_Closed.objName -> new OBJ_Door_Closed(gp, worldX, worldY);
            case OBJ_Door_Locked.objName -> new OBJ_Door_Locked(gp, worldX, worldY);
            case OBJ_Door_Oneway.objName -> new OBJ_Door_Oneway(gp, worldX, worldY);
            case OBJ_Tent.objName -> new OBJ_Tent(gp, worldX, worldY);
            case OI_Block_Pushable.obj_iName -> new OI_Block_Pushable(gp, worldX, worldY);
            default -> null;
        };

        return obj;
    }
}