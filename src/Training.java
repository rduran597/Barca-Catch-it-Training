import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Training {

    private JPanel Training;
    // menu principal del juego, con opciones para jugar, ver el ranking y salir
    private JPanel menuPrincipal;

    private JButton buttonPause;

    private JLabel labelTime;

    private JLabel labelPlayer;

    private JPanel pantallaJuego;

    private JButton botonJugar;

    private JButton botonRanking;

    private Icon iconDer;
    private Icon iconIzq;
    private boolean saltando = false;

    private JButton botonSalir;
    private int seconds = 0;

    public Training() {
        Training.setPreferredSize(new Dimension(800, 600));
        Training.setSize(new Dimension(800, 600));
        Training.setLayout(null);

        showPanelTitle();
        showPanelCenter();

        Timer timer = new Timer(1000, new TimerActionListener());

        timer.start();

        buttonPause.addMouseListener(new ButtonPauseListener(timer));


        Training.addKeyListener(new TrainingListener());
        Training.setFocusable(true);
        Training.requestFocusInWindow();
    }

    private class TrainingListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int x = labelPlayer.getX();

            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> {
                    x += 20;
                    labelPlayer.setIcon(iconDer);
                }
                case KeyEvent.VK_LEFT -> {
                    x -= 20;
                labelPlayer.setIcon(iconIzq);
                }
                case KeyEvent.VK_SPACE -> saltar();
                case KeyEvent.VK_UP -> saltar();
            }

            if (x>=0 && x <= pantallaJuego.getWidth() - labelPlayer.getWidth()) {
                labelPlayer.setLocation(x, labelPlayer.getY());
            }
        }
    }
    private class ButtonPauseListener extends MouseAdapter {

        Timer timer;

        public ButtonPauseListener(Timer timer) {
            this.timer = timer;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // super.mouseClicked(e);
            if (buttonPause.getText().equals("Pausar")) {
                timer.stop();
                buttonPause.setText("Reanudar");
            } else {
                timer.start();
                buttonPause.setText("Pausar");
            }

        }
    }

    public class TimerActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++;
            labelTime.setText(seconds + " segundos");
        }
    }

    private void showPanelTitle() {
        //panelTitle
        menuPrincipal = new JPanel();
        menuPrincipal.setLocation(0, 0);
        menuPrincipal.setSize(Training.getWidth(), 50);
        menuPrincipal.setBackground(Color.GRAY);

        // panelMain
        Training.add(menuPrincipal);

        labelTime = new JLabel();
        labelTime.setText("0 segundos");
        menuPrincipal.add(labelTime);

        buttonPause = new JButton();
        buttonPause.setText("Pausar");
        buttonPause.setFocusPainted(false);
        buttonPause.setBackground(new Color (25, 18, 50));
        buttonPause.setForeground(Color.WHITE);
        menuPrincipal.add(buttonPause);

    }

    private void showPanelCenter() {
        //panelCenter
        pantallaJuego = new JPanel();
        pantallaJuego.setLayout(null);
        pantallaJuego.setLocation(0, 50);
        pantallaJuego.setSize(800, 550);
        pantallaJuego.setBackground(Color.LIGHT_GRAY);

        showJugador();


        // añadir fondo al juego
        JLabel fondo = new JLabel();
        fondo.setSize(800, 550);
        ImageIcon imagenFondo = new ImageIcon("src/images/campNou.jpg");
        fondo.setIcon(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 550, Image.SCALE_SMOOTH)));

        pantallaJuego.add(fondo);
        pantallaJuego.setComponentZOrder(fondo, pantallaJuego.getComponentCount() - 1);


        Training.add(pantallaJuego);
    }

    private void showJugador() {
        labelPlayer = new JLabel();
        labelPlayer.setSize(90, 100);
        iconDer = new ImageIcon (new ImageIcon("src/images/pedri.png").getImage()
                .getScaledInstance(labelPlayer.getWidth(), labelPlayer.getHeight(), Image.SCALE_SMOOTH));
        labelPlayer.setIcon(iconDer);
        labelPlayer.setLocation(pantallaJuego.getWidth() / 2 - labelPlayer.getWidth() / 2, pantallaJuego.getHeight()  - labelPlayer.getHeight());

        iconIzq = new ImageIcon (new ImageIcon("src/images/pedriReversa.png").getImage().
                getScaledInstance(labelPlayer.getWidth(), labelPlayer.getHeight(), Image.SCALE_SMOOTH));

        labelPlayer.setIcon(iconDer);
        labelPlayer.setLocation(pantallaJuego.getWidth() / 2 - 45, pantallaJuego.getHeight() -100);
        pantallaJuego.add(labelPlayer);
    }

    private void saltar() {
        if (saltando) return;
        saltando = true;

        Timer Aire = new Timer(10, new ActionListener() {
            int contador = 0;
            int velocidadY = -10;


            @Override
            public void actionPerformed(ActionEvent e) {
                labelPlayer.setLocation(labelPlayer.getX(), labelPlayer.getY() + velocidadY);
                contador++;


                // Cuando llega a la mitad (puntos altos), empezamos a bajar
                if (contador == 15) {
                    velocidadY = 10; // Cambiamos a positivo para que baje
                }

                // Cuando vuelve a su posición original (aprox 30 pasos), paramos
                if (contador == 30) {
                    ((Timer) e.getSource()).stop(); // Detenemos el Timer
                    saltando = false; // Ya puede volver a saltar

                }
            }
            });

        Aire.start();
    }


    private static class FrameWindowsListener extends WindowAdapter {

        JFrame frame;

        public FrameWindowsListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);

            int confirmado = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que quieres salir?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmado == JOptionPane.YES_OPTION) {
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } else {
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Training");
        frame.setContentPane(new Training().Training);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setLocation(350, 100);
        frame.setLayout(null);

        //cambiar el icono de la ventana
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image icon = toolkit.getImage("src/images/icono.png");
        frame.setIconImage(icon);

        frame.addWindowListener(new FrameWindowsListener(frame));



    }

}


