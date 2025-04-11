package entidades;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.Juego;
import static utilz.constantes.EnemigoConstants.*;
import static utilz.MetodosDeAyuda.*;
import static utilz.constantes.Direcciones.*;
import static utilz.constantes.GRAVEDAD;

public abstract class Enemigo extends Entidad {

    protected int enemyType;
    protected int aniSpeed = 30;
    protected boolean primeraUpdate = true;
    protected float velocidadCaminar = 0.35f * Juego.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Juego.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;

    public Enemigo(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        vidaMax = GetMaxHealth(enemyType);
        vidaActual = vidaMax;
    }

    protected void moverse(int[][] lvlData) {
        float xSpeed = 0;

        if (walkDir == LEFT) {
            xSpeed = -velocidadCaminar;
        } else {
            xSpeed = velocidadCaminar;
        }

        if (puedeMoverseAca(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            if (EnElSuelo(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }
        cambiarWalkDir();
    }

    protected void updateEnAire(int[][] lvlData) {
        if (puedeMoverseAca(hitbox.x, hitbox.y + velocidadEnAire, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += velocidadEnAire;
            velocidadEnAire += GRAVEDAD;

        } else {
            enElAire = false;
            hitbox.y = GetEntidadYPosUnderRoofOrAboveFloor(hitbox, velocidadEnAire);
            tileY = (int) (hitbox.y / Juego.TILES_SIZE);
        }
    }

    protected void primerUpdateCheck(int[][] lvlData) {
        if (primeraUpdate) {
            if (!EntidadEnElSuelo(hitbox, lvlData)) {
                enElAire = true;
                primeraUpdate = false;
            }
        }
    }

    protected void irJugador(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    protected boolean puedoVerPlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Juego.TILES_SIZE);
        if (playerTileY == tileY) {
            if (playerEnRango(player)) {
                if (viendoPlayer(lvlData, hitbox, player.hitbox, tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean playerEnRango(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue < attackDistance * 5;
    }

    protected boolean playerEnRangoDeAtaque(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue < attackDistance;
    }

    protected void newState(int enemyState) {
        this.state = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void hurt(int amount) {
        vidaActual -= amount;
        if (vidaActual <= 0) {
            newState(DEATH);
        } else {
            newState(HIT);
        }
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox)) {
            player.cambiarVida(-GetDañoEnemigo(enemyType));
        }
        attackChecked = true;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                aniIndex = 0;

                switch (state) {
                    case SKID:
                        newState(WALK);
                        break;
                    case ATTACK:
                    case HIT:
                        state = IDLE;
                        break;
                    case DEATH:
                        active = false;
                        break;
                }

            }
        }
    }

    protected void cambiarWalkDir() {

        if (walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
        newState(SKID);
    }

    public void resetEnemigo() {
        hitbox.x = x;
        hitbox.y = y;
        primeraUpdate = true;
        vidaActual = vidaMax;
        newState(IDLE);
        active = true;
        velocidadEnAire = 0;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public boolean isActive() {
        return active;
    }

}
