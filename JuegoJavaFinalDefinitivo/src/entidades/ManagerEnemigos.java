package entidades;

import audio.AudioPlayer;
import gamestates.Playing;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.Level;
import main.Juego;
import utilz.LoadSave;
import static utilz.constantes.EnemigoConstants.*;

public class ManagerEnemigos {

    private Playing playing;
    private BufferedImage[][] koopaArr;
    private ArrayList<Koopa> koopas = new ArrayList<>();

    public ManagerEnemigos(Playing playing) {
        this.playing = playing;
        loadEnemigosImg();
    }

    public void loadEnemigos(Level level) {
        koopas = level.getEnemigos();
        System.out.println("Numero de enemigos: " + koopas.size());
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (Koopa kp : koopas) {
            if (kp.isActive()) {
                kp.update(lvlData, player);
                isAnyActive = true;
            }
        }
        if (!isAnyActive) {
            playing.setLevelCompletado(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        drawEnemigos(g, xLvlOffset, yLvlOffset);
    }

    private void drawEnemigos(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Koopa kp : koopas) {
            if (kp.isActive()) {
                g.drawImage(koopaArr[kp.getState()][kp.getAniIndex()], (int) (kp.getHitbox().x - KOOPA_DRAWOFFSET_X) - xLvlOffset + kp.flipx(), (int) (kp.getHitbox().y - KOOPA_DRAWOFFSET_Y) - yLvlOffset, (int) (KOOPA_WIDTH * Juego.SCALE) * kp.flipW(), (int) (KOOPA_HEIGHT * Juego.SCALE), null);
                //kp.drawHitbox(g, xLvlOffset, yLvlOffset);
                // kp.drawAttackBox(g, xLvlOffset, yLvlOffset);
            }
        }
    }

    public void hitEnemigo(Rectangle2D.Float attackBox) {
        for (Koopa kp : koopas) {
            if (kp.isActive()) {
                if (kp.getVidaActual() > 0) {
                    if (attackBox.intersects(kp.getHitbox())) {
                        playing.getJuego().getAudioPlayer().playEffect(AudioPlayer.KOOPA);
                        kp.hurt(10);
                        return;
                    }
                }
            }
        }
    }

    private void loadEnemigosImg() {
        koopaArr = new BufferedImage[6][6];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_KOOPA);
        for (int j = 0; j < koopaArr.length; j++) {
            for (int i = 0; i < koopaArr[j].length; i++) {
                koopaArr[j][i] = temp.getSubimage(i * KOOPA_WIDTH_DEFAULT, j * KOOPA_HEIGHT_DEFAULT, KOOPA_WIDTH_DEFAULT, KOOPA_HEIGHT_DEFAULT);
            }
        }
    }

    public void resetTodosEnemigos() {
        for (Koopa kp : koopas) {
            kp.resetEnemigo();
        }
    }

}
