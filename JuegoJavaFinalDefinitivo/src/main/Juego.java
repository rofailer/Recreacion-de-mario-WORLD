package main;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.JuegoOptions;
import gamestates.Menu;
import gamestates.Playing;
import java.awt.Graphics;
import ui.AudioOptions;
import utilz.LoadSave;

public class Juego implements Runnable {

    private VentanaJuego ventanaJuego;
    private PanelJuego panelJuego;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private JuegoOptions juegoOptions;
    private AudioOptions audioOptions;

    private AudioPlayer audioPlayer;
    //TAMAÑO PANTALLA Y TILES
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2.0f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Juego() {
        initClasses();

        panelJuego = new PanelJuego(this);
        ventanaJuego = new VentanaJuego(panelJuego);
        panelJuego.setFocusable(true);
        panelJuego.requestFocus();

        startGameLoop();
    }

    private void initClasses() {
        audioOptions = new AudioOptions(this);
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        juegoOptions = new JuegoOptions(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                juegoOptions.update();
                break;
            case QUIT:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case OPTIONS:
                juegoOptions.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        //GAMELOOP
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                panelJuego.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " / " + "UPS: " + updates);
                updates = 0;
                frames = 0;
            }

        }

    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }

    public JuegoOptions getJuegoOptions() {
        return juegoOptions;
    }

}
