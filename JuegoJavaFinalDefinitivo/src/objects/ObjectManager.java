package objects;

import gamestates.Playing;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utilz.LoadSave;
import static utilz.constantes.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] powerUpImgs, blockImgs;
    private ArrayList<PowerUps> powerUp;
    private ArrayList<ContenedorJuego> contenedor;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();

        powerUp = new ArrayList<>();
        powerUp.add(new PowerUps(300, 300, HONGO));
        powerUp.add(new PowerUps(400, 300, ESTRELLA));
        contenedor = new ArrayList<>();
        contenedor.add(new ContenedorJuego(500, 300, BLOCK1));
        contenedor.add(new ContenedorJuego(600, 300, BLOCK2));
    }

    private void loadImgs() {

        BufferedImage powerUpSprite = LoadSave.GetSpriteAtlas(LoadSave.POWERUPS);
        powerUpImgs = new BufferedImage[0][0];

        for (int j = 0; j < powerUpImgs.length; j++) {
            for (int i = 0; i < powerUpImgs[j].length; i++) {
                powerUpImgs[j][i] = powerUpSprite.getSubimage(i * 16, j * 16, 16, 16);
            }
        }

        BufferedImage contenedorSprite = LoadSave.GetSpriteAtlas(LoadSave.BLOCKS_SPRITES);
        blockImgs = new BufferedImage[2][4];

        for (int j = 0; j < blockImgs.length; j++) {
            for (int i = 0; i < blockImgs[j].length; i++) {
                blockImgs[j][i] = contenedorSprite.getSubimage(i * 16, j * 16, 16, 16);
            }
        }
    }

    public void update() {
        for (PowerUps pu : powerUp) {
            if (pu.isActive()) {
                pu.update();
            }
        }
        for (ContenedorJuego c : contenedor) {
            if (c.isActive()) {
                c.update();
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        drawPowerUps(g, xLvlOffset, yLvlOffset);
        drawContenedor(g, xLvlOffset, yLvlOffset);
    }

    private void drawContenedor(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (ContenedorJuego c : contenedor) {
            if (c.isActive()) {
                int type = 0;
                if (c.getObjType() == BLOCK1) {
                    type = 1;
                }
                g.drawImage(blockImgs[type][c.getAniIndex()], (int) (c.getHitbox().x - c.getxDrawOffset() - xLvlOffset), (int) (c.getHitbox().y - c.getyDrawOffset() - yLvlOffset), CONTAINER_WIDTH, CONTAINER_HEIGHT, null);
            }
        }
    }

    private void drawPowerUps(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (PowerUps pu : powerUp) {
            if (pu.isActive()) {
                int type = 0;
                if (pu.getObjType() == HONGO) {
                    type = 1;
                }
                g.drawImage(powerUpImgs[type][pu.getAniIndex()], (int) (pu.getHitbox().x - pu.getxDrawOffset() - xLvlOffset), (int) (pu.getHitbox().y - pu.getyDrawOffset() - yLvlOffset), POWERUP_WIDTH, POWERUP_HEIGHT, null);
            }
        }
    }

}
