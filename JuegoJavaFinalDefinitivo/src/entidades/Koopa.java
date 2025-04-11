package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.Juego;
import static utilz.MetodosDeAyuda.EnElSuelo;
import static utilz.MetodosDeAyuda.puedeMoverseAca;
import static utilz.constantes.Direcciones.*;
import static utilz.constantes.EnemigoConstants.*;

public class Koopa extends Enemigo {

    private int attackBoxOffsetX;

    public Koopa(float x, float y) {
        super(x, y, KOOPA_WIDTH, KOOPA_HEIGHT, KOOPA);
        initHitbox(x, y, (int) (26 * Juego.SCALE), (int) (30 * Juego.SCALE));
        initAttackBox();

    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (65 * Juego.SCALE), (int) (30 * Juego.SCALE));
        attackBoxOffsetX = (int) (20 * Juego.SCALE);
    }

    public void update(int[][] lvlData, Player player) {
        updateComportamiento(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    private void updateComportamiento(int[][] lvlData, Player player) {

        if (primeraUpdate) {
            primerUpdateCheck(lvlData);
        }
        if (enElAire) {
            updateEnAire(lvlData);
        } else {
            switch (state) {
                case IDLE:
                    newState(WALK);
                    break;
                case WALK:
                    if (puedoVerPlayer(lvlData, player)) {
                        irJugador(player);
                        if (playerEnRangoDeAtaque(player)) {
                            newState(ATTACK);
                        }
                    }
                    moverse(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0) {
                        attackChecked = false;
                    }
                    if (aniIndex == 4 && !attackChecked) {
                        checkPlayerHit(attackBox, player);
                    }
                    break;
                case HIT:
                    break;
            }
        }

    }

    public void drawAttackBox(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y - yLvlOffset, (int) attackBox.width, (int) attackBox.height);
    }

    public int flipx() {
        if (walkDir == RIGHT) {
            return width + 68;
        } else {
            return 0;
        }
    }

    public int flipW() {
        if (walkDir == RIGHT) {
            return -1;
        } else {
            return 1;
        }
    }

}
