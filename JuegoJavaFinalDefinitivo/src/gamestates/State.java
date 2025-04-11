package gamestates;

import audio.AudioPlayer;
import java.awt.event.MouseEvent;
import main.Juego;
import ui.MenuButton;

public class State {

    protected Juego juego;

    public State(Juego juego) {
        this.juego = juego;

    }

    public boolean isIn(MouseEvent me, MenuButton mb) {
        return mb.getBounds().contains(me.getX(), me.getY());
    }

    public Juego getJuego() {
        return juego;
    }

    public void setGamestate(Gamestate state) {
        switch (state) {
            case MENU:
                juego.getAudioPlayer().playSong(AudioPlayer.MENU_1);
                break;
            case PLAYING:
                juego.getAudioPlayer().setLevelSong(juego.getPlaying().getLevelManager().getLevelIndex());
                break;
        }

        Gamestate.state = state;
    }

}
