package objects;

import main.Juego;
import static utilz.constantes.ObjectConstants.*;

public class ContenedorJuego extends JuegoObject {

    public ContenedorJuego(int x, int y, int objType) {
        super(x, y, objType);
        //initHitbox(15, 15);
        createHitbox();
    }

    private void createHitbox() {
        if (objType == BLOCK1) {
            initHitbox(25, 18);

            xDrawOffset = (int) (7 * Juego.SCALE);
            yDrawOffset = (int) (12 * Juego.SCALE);

        } else {
            initHitbox(23, 25);
            xDrawOffset = (int) (8 * Juego.SCALE);
            yDrawOffset = (int) (5 * Juego.SCALE);
        }

        hitbox.y += yDrawOffset + (int) (Juego.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }

    public void update() {
        if (doAnimation) {
            updateAnimationTick();
        }
    }

}
