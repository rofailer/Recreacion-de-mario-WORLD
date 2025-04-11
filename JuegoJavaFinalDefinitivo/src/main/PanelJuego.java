package main;

import Inputs.MouseInput;
import Inputs.TecladoInput;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static main.Juego.GAME_HEIGHT;
import static main.Juego.GAME_WIDTH;

public class PanelJuego extends JPanel {

    private MouseInput mouseInput;
    private Juego juego;

    public PanelJuego(Juego juego) {

        mouseInput = new MouseInput(this);
        this.juego = juego;

        setPanelSize();
        addKeyListener(new TecladoInput(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);

    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    public void updateJuego() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        juego.render(g);

    }

    public Juego getJuego() {
        return juego;
    }

}
