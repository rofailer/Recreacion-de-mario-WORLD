package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Juego;
import utilz.LoadSave;
import static utilz.constantes.UI.BotonesPausa.*;
import static utilz.constantes.UI.BotonURM.*;
import static utilz.constantes.UI.BotonVolumen.*;

public class PauseOverlay {

    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private AudioOptions audioOptions;
    private UrmButton menuB, replayB, unpauseB;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getJuego().getAudioOptions();

        createUrmButtons();

    }

    public void createUrmButtons() {
        int unpauseX = (int) (160 * Juego.SCALE * 2);
        int replayX = (int) (200 * Juego.SCALE * 2);
        int menuX = (int) (240 * Juego.SCALE * 2);
        int by = (int) (180 * Juego.SCALE * 2);
        bgW = (int) (176 * Juego.SCALE * 2);
        bgH = (int) (192 * Juego.SCALE * 2);
        bgX = Juego.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (15 * Juego.SCALE * 2);

        unpauseB = new UrmButton(unpauseX, by, (int) (URM_SIZE * Juego.SCALE), (int) (URM_SIZE * Juego.SCALE), 0);
        replayB = new UrmButton(replayX, by, (int) (URM_SIZE * Juego.SCALE), (int) (URM_SIZE * Juego.SCALE), 1);
        menuB = new UrmButton(menuX, by, (int) (URM_SIZE * Juego.SCALE), (int) (URM_SIZE * Juego.SCALE), 2);

    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);

    }

    public void update() {
        //URM BOTON
        unpauseB.update();
        replayB.update();
        menuB.update();

        audioOptions.update();

    }

    public void draw(Graphics g) {
        //FONDO 
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
        //URM BOTON
        unpauseB.draw(g);
        replayB.draw(g);
        menuB.draw(g);

        audioOptions.draw(g);

    }

    public void mouseDragged(MouseEvent me) {
        audioOptions.mouseDragged(me);
    }

    public void mousePressed(MouseEvent me) {
        if (isIn(me, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(me, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(me, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else {
            audioOptions.mousePressed(me);
        }
    }

    public void mouseReleased(MouseEvent me) {
        if (isIn(me, replayB)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
            }
        } else if (isIn(me, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.unpauseGame();
            }
        } else if (isIn(me, menuB)) {
            if (menuB.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(Gamestate.MENU);
                playing.unpauseGame();
            }
        } else {
            audioOptions.mouseReleased(me);
        }
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
    }

    public void mouseMoved(MouseEvent me) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if (isIn(me, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(me, unpauseB)) {
            unpauseB.setMouseOver(true);
        } else if (isIn(me, menuB)) {
            menuB.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(me);
        }

    }

    private boolean isIn(MouseEvent me, PauseButton pb) {
        return pb.getBounds().contains(me.getX(), me.getY());
    }

}
