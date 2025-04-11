package ui;

import gamestates.Gamestate;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import main.Juego;
import static utilz.constantes.UI.BotonVolumen.*;
import static utilz.constantes.UI.BotonesPausa.SOUND_SIZE;

public class AudioOptions {

    private VolumeButton volumeButton;
    private SoundButton musicButton, sfxButton;

    private Juego juego;

    public AudioOptions(Juego juego) {
        this.juego = juego;
        createSoundButtons();
        createVolumeButton();
    }

    public void createVolumeButton() {
        int vX = (int) (153 * Juego.SCALE * 2);
        int vY = (int) (150 * Juego.SCALE * 2);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);

    }

    private void createSoundButtons() {
        int soundX = (int) (240 * Juego.SCALE * 2);
        int musicY = (int) (63 * Juego.SCALE * 2);
        int sfxY = (int) (95 * Juego.SCALE * 2);
        musicButton = new SoundButton(soundX, musicY, (int) (SOUND_SIZE * Juego.SCALE), (int) (SOUND_SIZE * Juego.SCALE));
        sfxButton = new SoundButton(soundX, sfxY, (int) (SOUND_SIZE * Juego.SCALE), (int) (SOUND_SIZE * Juego.SCALE));
    }

    public void update() {
        //SONIDO BOTON
        musicButton.update();
        sfxButton.update();
        //VOLUMEN BOTON
        volumeButton.update();
    }

    public void draw(Graphics g) {
        musicButton.draw(g);
        sfxButton.draw(g);
        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent me) {
        if (volumeButton.isMousePressed()) {
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(me.getX());
            float valueAfter = volumeButton.getFloatValue();
            if (valueBefore != valueAfter) {
                juego.getAudioPlayer().setVolume(valueAfter);
            }
        }
    }

    public void mousePressed(MouseEvent me) {
        if (isIn(me, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(me, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(me, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent me) {
        if (isIn(me, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
                juego.getAudioPlayer().toggleSongMute();
            }

        } else if (isIn(me, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                juego.getAudioPlayer().toggleEffectMute();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();

        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent me) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        volumeButton.setMouseOver(false);

        if (isIn(me, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(me, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(me, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    private boolean isIn(MouseEvent me, PauseButton pb) {
        return pb.getBounds().contains(me.getX(), me.getY());
    }
}
