package Inputs;

import gamestates.Gamestate;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.PanelJuego;

public class MouseInput implements MouseListener, MouseMotionListener {

    private PanelJuego panelJuego;

    public MouseInput(PanelJuego panelJuego) {

        this.panelJuego = panelJuego;

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        switch (Gamestate.state) {
            case PLAYING:
                panelJuego.getJuego().getPlaying().mouseClicked(me);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        switch (Gamestate.state) {
            case MENU:
                panelJuego.getJuego().getMenu().mousePressed(me);
                break;
            case PLAYING:
                panelJuego.getJuego().getPlaying().mousePressed(me);
                break;
            case OPTIONS:
                panelJuego.getJuego().getJuegoOptions().mousePressed(me);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        switch (Gamestate.state) {
            case MENU:
                panelJuego.getJuego().getMenu().mouseReleased(me);
                break;
            case PLAYING:
                panelJuego.getJuego().getPlaying().mouseReleased(me);
                break;
            case OPTIONS:
                panelJuego.getJuego().getJuegoOptions().mouseReleased(me);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {
        switch (Gamestate.state) {
            case PLAYING:
                panelJuego.getJuego().getPlaying().mouseDragged(me);
                break;
            case OPTIONS:
                panelJuego.getJuego().getJuegoOptions().mouseDragged(me);
                break;
            default:
                break;
        }

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        switch (Gamestate.state) {
            case MENU:
                panelJuego.getJuego().getMenu().mouseMoved(me);
                break;
            case PLAYING:
                panelJuego.getJuego().getPlaying().mouseMoved(me);
                break;
            case OPTIONS:
                panelJuego.getJuego().getJuegoOptions().mouseMoved(me);
                break;
            default:
                break;
        }
    }

}
