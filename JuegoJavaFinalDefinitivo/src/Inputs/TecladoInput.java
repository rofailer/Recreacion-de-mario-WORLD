package Inputs;

import gamestates.Gamestate;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.PanelJuego;

public class TecladoInput implements KeyListener {

    private PanelJuego panelJuego;

    public TecladoInput(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        switch (Gamestate.state) {
            case MENU:
                panelJuego.getJuego().getMenu().keyReleased(ke);
                break;
            case PLAYING:
                panelJuego.getJuego().getPlaying().keyReleased(ke);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (Gamestate.state) {
            case MENU:
                panelJuego.getJuego().getMenu().keyPressed(ke);
                break;
            case PLAYING:
                panelJuego.getJuego().getPlaying().keyPressed(ke);
                break;
            case OPTIONS:
                panelJuego.getJuego().getPlaying().keyPressed(ke);
                break;
            default:
                break;

        }

    }

}
