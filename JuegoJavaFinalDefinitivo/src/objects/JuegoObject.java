package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.Juego;
import static utilz.constantes.ObjectConstants.*;

public class JuegoObject {

    protected int x, y, objType, aniSpeed = 30;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public JuegoObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(objType)) {
                aniIndex = 0;
                if (objType == BLOCK1 || objType == BLOCK2) {
                    doAnimation = false;
                    active = false;
                }
            }
        }
    }

    public void reset() {
        aniIndex = 0;
        aniTick = 0;
        active = true;
        if (objType == BLOCK1 || objType == BLOCK2) {
            doAnimation = false;
        } else {
            doAnimation = true;
        }
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Juego.SCALE), (int) (height * Juego.SCALE));
    }

    public void drawHitbox(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.setColor(Color.MAGENTA);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y - yLvlOffset, (int) hitbox.width, (int) hitbox.height);

    }

    public int getObjType() {
        return objType;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAniIndex() {
        return aniIndex;
    }

}
