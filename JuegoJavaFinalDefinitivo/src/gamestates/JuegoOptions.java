package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Juego;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import utilz.LoadSave;
import static utilz.constantes.UI.BotonURM.*;

public class JuegoOptions extends State implements Statemethods {

    private AudioOptions audioOptions;
    private BufferedImage backgroundImg, optionsBackgroundImg, backgroundImgBack;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;
    private int menuX, menuY, menuWidth, menuHeight;

    public JuegoOptions(Juego juego) {
        super(juego);
        loadImgs();
        loadButtons();
        backgroundImgBack = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        audioOptions = juego.getAudioOptions();
    }

    public void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);

        bgW = (int) (176 * Juego.SCALE * 2);
        bgH = (int) (192 * Juego.SCALE * 2);
        bgX = Juego.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (15 * Juego.SCALE * 2);

        menuWidth = (int) (backgroundImg.getWidth() * Juego.SCALE * 1.9);
        menuHeight = (int) (backgroundImg.getHeight() * Juego.SCALE * 1.9);
        menuX = Juego.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (0 * Juego.SCALE);
    }

    public void loadButtons() {
        int menuX = (int) (400 * Juego.SCALE);
        int menuY = (int) (365 * Juego.SCALE);
        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgBack, -250, -900, menuWidth + 500, menuHeight + 1000, null);

        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

        menuB.draw(g);
        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent me) {
        audioOptions.mouseDragged(me);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (isIn(me, menuB)) {
            menuB.setMousePressed(true);
        } else {
            audioOptions.mousePressed(me);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (isIn(me, menuB)) {
            menuB.setMousePressed(true);
        } else {
            audioOptions.mousePressed(me);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (isIn(me, menuB)) {
            if (menuB.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
            }
        } else {
            audioOptions.mouseReleased(me);
        }
        menuB.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        menuB.setMouseOver(false);
        if (isIn(me, menuB)) {
            menuB.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(me);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.state = Gamestate.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    private boolean isIn(MouseEvent me, PauseButton pb) {
        return pb.getBounds().contains(me.getX(), me.getY());
    }

}
