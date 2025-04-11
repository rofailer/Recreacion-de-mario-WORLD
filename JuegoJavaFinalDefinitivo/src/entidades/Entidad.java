package entidades;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public abstract class Entidad {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int state;
    protected float velocidadEnAire;
    protected boolean enElAire = false;
    protected int vidaMax;
    protected int vidaActual;
    //AttackBox 
    protected Rectangle2D.Float attackBox;

    public Entidad(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;

    }

    protected void drawHitbox(Graphics g, int xLvlOffset, int yLvlOffset) {
        //Ver la hitbox
        g.setColor(Color.MAGENTA);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y - yLvlOffset, (int) hitbox.width, (int) hitbox.height);

    }

    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getState() {
        return state;
    }

    public int getVidaActual() {
        return vidaActual;
    }

}
