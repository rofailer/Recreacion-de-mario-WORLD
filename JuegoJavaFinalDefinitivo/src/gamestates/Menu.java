package gamestates;

import entidades.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Juego;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {
    
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg, backgroundImgBack;
    private int menuX, menuY, menuWidth, menuHeight;
    
    public Menu(Juego juego) {
        super(juego);
        loadButtons();
        loadBackground();
        backgroundImgBack = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }
    
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Juego.SCALE * 1.9);
        menuHeight = (int) (backgroundImg.getHeight() * Juego.SCALE * 1.9);
        menuX = Juego.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (0 * Juego.SCALE);
    }
    
    private void loadButtons() {
        buttons[0] = new MenuButton((Juego.GAME_WIDTH / 2) - 290, (int) (220 * Juego.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton((Juego.GAME_WIDTH / 2) - 290, (int) (270 * Juego.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton((Juego.GAME_WIDTH / 2) - 290, (int) (320 * Juego.SCALE), 2, Gamestate.QUIT);
    }
    
    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgBack, -250, -900, menuWidth + 500, menuHeight + 1000, null);
        
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        
        for (MenuButton mb : buttons) {
            mb.draw(g);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        for (MenuButton mb : buttons) {
            if (isIn(me, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent me) {
        for (MenuButton mb : buttons) {
            if (isIn(me, mb)) {
                if (mb.isMousePressed()) {
                    mb.aplicarGamestate();
                    if (mb.getState() == Gamestate.PLAYING) {
                        juego.getAudioPlayer().setLevelSong(juego.getPlaying().getLevelManager().getLevelIndex());
                    }
                    break;
                }
            }
        }
        resetButtons();
    }
    
    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBools();
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent me) {
        for (MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }
        for (MenuButton mb : buttons) {
            if (isIn(me, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAYING;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
}
