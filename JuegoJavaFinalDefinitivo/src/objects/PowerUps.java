package objects;

import main.Juego;

public class PowerUps extends JuegoObject {

    public PowerUps(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(14, 14);
        xDrawOffset = (int) (3 * Juego.SCALE);
        yDrawOffset = (int) (2 * Juego.SCALE);
    }

    public void update() {
        updateAnimationTick();
    }

}
