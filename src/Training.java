import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Training {

    private JPanel Training;
    // menu principal del juego, con opciones para jugar, ver el ranking y salir
    private JPanel menuPrincipal;

    private JLabel labelTime;

    private JPanel pantallaJuego;

    private JButton botonJugar;

    private JButton botonRanking;

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

    }

    public class TimerActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++;
            labelTime.setText(seconds + " segundos");
        }
    }

    private void showPanelTitle() {
        menuPrincipal = new JPanel();
        menuPrincipal.setLocation(0, 0);
        menuPrincipal.setSize(Training.getWidth(), 50);
        menuPrincipal.setBackground(Color.GRAY);

        Training.add(menuPrincipal);

        labelTime = new JLabel();
        labelTime.setText("0 segundos");
        menuPrincipal.add(labelTime);



    }

    private void showPanelCenter() {
        pantallaJuego = new JPanel();
        pantallaJuego.setLayout(null);
        pantallaJuego.setSize(Training.getWidth(), Training.getHeight() - menuPrincipal.getHeight());
        pantallaJuego.setBackground(Color.LIGHT_GRAY);

        Training.add(pantallaJuego);

        showJugador
    }

    private void showJugador() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Training");
        frame.setContentPane(new Training().Training);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

}


