package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Juego;
import utilz.LoadSave;
import static utilz.constantes.UI.BotonURM.*;

public class LevelCompletadoOverlay {
    
    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;
    
    public LevelCompletadoOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }
    
    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * Juego.SCALE * 2);
        bgH = (int) (img.getHeight() * Juego.SCALE * 2);
        bgX = Juego.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Juego.SCALE);
    }
    
    private void initButtons() {
        int menuX = (int) (480 * Juego.SCALE);
        int nextX = (int) (320 * Juego.SCALE);
        int y = (int) (300 * Juego.SCALE);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }
    
    public void update() {
        next.update();
        menu.update();
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Juego.GAME_WIDTH, Juego.GAME_HEIGHT);
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }
    
    private boolean isIn(UrmButton b, MouseEvent me) {
        return b.getBounds().contains(me.getX(), me.getY());
    }
    
    public void mouseMoved(MouseEvent me) {
        next.setMouseOver(false);
        menu.setMouseOver(false);
        
        if (isIn(menu, me)) {
            menu.setMouseOver(true);
        } else if (isIn(next, me)) {
            next.setMouseOver(true);
        }
    }
    
    public void mouseReleased(MouseEvent me) {
        if (isIn(menu, me)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(Gamestate.MENU);
            }
        } else if (isIn(next, me)) {
            if (next.isMousePressed()) {
                playing.loadNextLevel();
                playing.getJuego().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
            }
        }
        menu.resetBools();
        next.resetBools();
    }
    
    public void mousePressed(MouseEvent me) {
        if (isIn(menu, me)) {
            menu.setMousePressed(true);
        } else if (isIn(next, me)) {
            next.setMousePressed(true);
        }
    }
    
}
