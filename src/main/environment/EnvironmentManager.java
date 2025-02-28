package environment;

import application.GamePanel;

import java.awt.*;

public class EnvironmentManager {

    private final GamePanel gp;
    public Lighting lighting;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }

    public void update() {
        lighting.update();
    }

    public void setup() {
        // LIGHTING CIRCLE
        lighting = new Lighting(gp);
    }

    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
}