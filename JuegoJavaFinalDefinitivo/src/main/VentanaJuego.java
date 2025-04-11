package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

public class VentanaJuego extends JFrame {

    private JFrame jframe;

    public VentanaJuego(PanelJuego panelJuego) {

        jframe = new JFrame();

        jframe.setTitle("Super Mario World: VERSION DE JAVA");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(panelJuego);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent we) {
                panelJuego.getJuego().windowFocusLost();
            }

            @Override
            public void windowGainedFocus(WindowEvent we) {

            }

        });

    }

}
