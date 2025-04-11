package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Juego;
import utilz.LoadSave;
import static utilz.constantes.UI.BotonURM.URM_SIZE;

public class GameOverOverlay {
    
    private Playing playing;
    private BufferedImage img;
    private int imgX, imgY, imgW, imgH;
    private UrmButton menu, play;
    
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImg();
        createButtons();
    }
    
    private void createButtons() {
        int menuX = (int) (480 * Juego.SCALE);
        int playX = (int) (320 * Juego.SCALE);
        int y = (int) (300 * Juego.SCALE);
        play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }
    
    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgW = (int) (img.getWidth() * Juego.SCALE * 3);
        imgH = (int) (img.getHeight() * Juego.SCALE * 3);
        imgX = Juego.GAME_WIDTH / 2 - imgW / 2;
        imgY = (int) (0 * Juego.SCALE);
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Juego.GAME_WIDTH, Juego.GAME_HEIGHT);
        
        g.drawImage(img, imgX, imgY, imgW, imgH, null);
        
        menu.draw(g);
        play.draw(g);
    }
    
    public void update() {
        menu.update();
        play.update();
    }
    
    public void keyPressed(KeyEvent ke) {
        
    }
    
    private boolean isIn(UrmButton b, MouseEvent me) {
        return b.getBounds().contains(me.getX(), me.getY());
    }
    
    public void mouseMoved(MouseEvent me) {
        play.setMouseOver(false);
        menu.setMouseOver(false);
        
        if (isIn(menu, me)) {
            menu.setMouseOver(true);
        } else if (isIn(play, me)) {
            play.setMouseOver(true);
        }
    }
    
    public void mouseReleased(MouseEvent me) {
        if (isIn(menu, me)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate(Gamestate.MENU);
            }
        } else if (isIn(play, me)) {
            if (play.isMousePressed()) {
                playing.resetAll();
                playing.getJuego().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
            }
        }
        menu.resetBools();
        play.resetBools();
    }
    
    public void mousePressed(MouseEvent me) {
        if (isIn(menu, me)) {
            menu.setMousePressed(true);
        } else if (isIn(play, me)) {
            play.setMousePressed(true);
        }
    }
    
}
