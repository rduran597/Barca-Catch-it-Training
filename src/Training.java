import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Training {
    //region VARIABLES UI
    private JPanel Training;
    private JPanel pantallaJuego;

    private JButton buttonPause;

    private JLabel labelTime;
    private JLabel labelPlayer;
    private JLabel labelvidas;
    private JLabel labelPuntos;

    private Icon iconDer;
    private Icon iconIzq;
    //endregion
    //region VARIABLES JUEGO
    private final boolean juegoActivo = true;
    private boolean saltando = false;

    private int seconds = 0;
    private int puntuacion = 0;
    private int vidas = 3;

    private final int META_PUNTOS = 1000; // Puntos necesarios para ganar
    //endregion
    //region TIMERS
    private final Timer generador;
    private Timer animacionCaida;
    //endregion
    //region DATOS JUGADOR
    private final String nombreActual;
    //endregion

    //region INICIALIZACION DEL JUEGO
    public Training(String nombre) {
        this.nombreActual = nombre;

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

        generador = new Timer(2000, e -> {
            int azar = (int) (Math.random() * 4);
            switch (azar) {
                case 0 -> caerObjeto("src/images/balon.png", 30);
                case 1 -> caerObjeto("src/images/madrid.png", -30);
                case 2 -> caerObjeto("src/images/roja.png", -50);
                case 3 -> caerObjeto("src/images/barca.png", 30);
            }
        });
        generador.start();
    }
    //endregion

    //region INPUT TECLADO
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
                case KeyEvent.VK_SPACE, KeyEvent.VK_UP -> saltar();
            }

            if (x >= 0 && x <= pantallaJuego.getWidth() - labelPlayer.getWidth()) {
                labelPlayer.setLocation(x, labelPlayer.getY());
            }
        }
    }
    //endregion

    //region PAUSA
    private class ButtonPauseListener extends MouseAdapter {

        private final Timer timer;

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
    //endregion

    //region TIMER TIEMPO
    public class TimerActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++;
            labelTime.setText(seconds + " segundos");
        }
    }
    //endregion

    //region PANEL SUPERIOR
    private void showPanelTitle() {
        //panelTitle
        // menu principal del juego, con opciones para jugar, ver el ranking y salir
        JPanel menuPrincipal = new JPanel();
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
        buttonPause.setBackground(new Color(25, 18, 50));
        buttonPause.setForeground(Color.WHITE);
        menuPrincipal.add(buttonPause);

        labelPuntos = new JLabel("Puntos: 0");
        labelPuntos.setForeground(Color.WHITE);
        menuPrincipal.add(labelPuntos);

        labelvidas = new JLabel("Vidas: " + vidas);
        labelvidas.setForeground(Color.RED);
        labelvidas.setFont(new Font("Arial", Font.BOLD, 14));
        menuPrincipal.add(labelvidas);
    }
    //endregion

    //region PANEL DEL JUEGO
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
        ImageIcon imagenFondo = new ImageIcon("src/images/campoFondo.png");
        fondo.setIcon(new ImageIcon(imagenFondo.getImage().getScaledInstance(800, 550, Image.SCALE_SMOOTH)));

        pantallaJuego.add(fondo);
        pantallaJuego.setComponentZOrder(fondo, pantallaJuego.getComponentCount() - 1);


        Training.add(pantallaJuego);
    }
    //endregion

    //region JUGADOR
    private void showJugador() {
        labelPlayer = new JLabel();
        labelPlayer.setSize(120, 150);
        iconDer = new ImageIcon(new ImageIcon("src/images/pedri.png").getImage()
                .getScaledInstance(labelPlayer.getWidth(), labelPlayer.getHeight(), Image.SCALE_SMOOTH));
        labelPlayer.setIcon(iconDer);
        labelPlayer.setLocation(pantallaJuego.getWidth() / 2 - labelPlayer.getWidth() / 2, pantallaJuego.getHeight() - labelPlayer.getHeight());

        iconIzq = new ImageIcon(new ImageIcon("src/images/pedriReversa.png").getImage().
                getScaledInstance(labelPlayer.getWidth(), labelPlayer.getHeight(), Image.SCALE_SMOOTH));

        labelPlayer.setIcon(iconDer);
        labelPlayer.setLocation(pantallaJuego.getWidth() / 2 - 45, pantallaJuego.getHeight() - 180);
        pantallaJuego.add(labelPlayer);
    }
    //endregion

    //region OBJETOS QUE CAEN
    private void caerObjeto(String rutaImagen, int valorPuntos) {
        JLabel objeto = new JLabel();
        objeto.setSize(50, 50);


        ImageIcon img = new ImageIcon(new ImageIcon(rutaImagen).getImage()
                .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        objeto.setIcon(img);


        int xAleatoria = (int) (Math.random() * (pantallaJuego.getWidth() - 50));
        objeto.setLocation(xAleatoria, 0);

        pantallaJuego.add(objeto);
        pantallaJuego.setComponentZOrder(objeto, 0);


        animacionCaida = new Timer(20, e -> {

            if (!juegoActivo) {
                ((Timer) e.getSource()).stop();
                return;
            }
            objeto.setLocation(objeto.getX(), objeto.getY() + 5);


            if (objeto.getBounds().intersects(labelPlayer.getBounds())) {

                if (valorPuntos > 0) {
                    puntuacion += valorPuntos;
                } else {
                    puntuacion += valorPuntos; // Resta puntos si es negativo
                    vidas--; // Pierde una vida
                }

                labelPuntos.setText("Puntos: " + puntuacion);
                labelvidas.setText("Vidas: " + vidas);

                ((Timer) e.getSource()).stop();
                pantallaJuego.remove(objeto);
                pantallaJuego.repaint();

                if (vidas <= 0) {
                    finalizarJuego("¡Has perdido! Has agotado todas tus vidas.");
                    animacionCaida.stop();
                    generador.stop();
                } else if (puntuacion >= META_PUNTOS) {
                    finalizarJuego("¡Felicidades! Has alcanzado la meta de puntos.");
                    generador.stop();
                    animacionCaida.stop();
                }
            }

            if (objeto.getY() > pantallaJuego.getHeight()) {
                ((Timer) e.getSource()).stop();
                pantallaJuego.remove(objeto);
                pantallaJuego.repaint();
            }
        });
        animacionCaida.start();
    }
    //endregion

    //region SALTO
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
    //endregion

    //region FIN DEL JUEGO

    private void finalizarJuego(String mensaje) {
        generador.stop();

        BBDD guardarDatos = new BBDD();
        guardarDatos.guardarPuntuacion(nombreActual, puntuacion, seconds, vidas);

        String[] opciones = {"Ver Ranking", "Salir"};

        int eleccion = JOptionPane.showOptionDialog(
                null,
                mensaje + "\nPuntuación: " + puntuacion,
                "Fin del juego",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (eleccion == 0) {
            guardarDatos.mostrarRanking();
        } else {
            System.exit(0);
        }
    }
    //endregion

    //region CONTROL DE CIERRE DE VENTANA
    private static class FrameWindowsListener extends WindowAdapter {

        final JFrame frame;

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
    //endregion

    //region BASE DE DATOS
    public class BBDD  {
        private Connection conectar() throws SQLException {
            // datos de conexion
            String url = "jdbc:mysql://localhost:3306/barca_catch_it";

            String user = "root";
            String pass = "1234";

            return DriverManager.getConnection(url, user, pass);
        }

        private void guardarPuntuacion(String nombre, int puntos, int segundos, int vidasRestantes) {


            // consulta SQL
            String sql = "INSERT INTO ranking (nombre, puntuacion, tiempo_segundos, vidas_finales) VALUES (?, ?, ?, ?)";

            try (Connection con = conectar();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                // rellenar los "?" con los datos reales
                pst.setString(1, nombre);
                pst.setInt(2, puntos);
                pst.setInt(3, segundos);
                pst.setInt(4, vidasRestantes);

                // ejecutar
                pst.executeUpdate();
                System.out.println("Puntuación guardada en la DB.");

            } catch (SQLException e) {
                System.err.println("Error al guardar: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
            }
        }

        private void mostrarRanking() {

            JFrame frameRanking = new JFrame("Ranking");
            frameRanking.setSize(450, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JTextArea areaRanking = new JTextArea();
            areaRanking.setEditable(false);

            areaRanking.setFont(new Font("Monospaced", Font.PLAIN, 13));
            JScrollPane scrollRanking = new JScrollPane(areaRanking);

            panel.add(scrollRanking, BorderLayout.CENTER);

            frameRanking.add(panel);
            frameRanking.setLocationRelativeTo(null);
            frameRanking.setVisible(true);

            String sql = "SELECT nombre, puntuacion, tiempo_segundos, vidas_finales FROM ranking ORDER BY puntuacion DESC LIMIT 10";

            try (Connection con = conectar();
                 PreparedStatement pst = con.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                areaRanking.append("===== TOP 10 =====\n");

                int posicion = 1;

                while (rs.next()) {

                    areaRanking.append(
                            posicion + ". " + rs.getString("nombre") + " - " +
                            rs.getInt("puntuacion") + " puntos, " +
                            rs.getInt("tiempo_segundos") + "s, " +
                            rs.getInt("vidas_finales") + " vidas restantes\n"
                    );
                    posicion++;
                }

            } catch (SQLException e) {

                System.err.println("Error al mostrar ranking: " + e.getMessage());
            }
        }
    }
    //endregion

    //region MAIN

    public static void main(String[] args) {
        String nombreUsuario = JOptionPane.showInputDialog("Introduce tu nombre de usuario:");
        if (nombreUsuario == null) {
            nombreUsuario = "Invitado";
        }

        JFrame frame = new JFrame("Training - Jugador: " + nombreUsuario);

        frame.setContentPane(new Training(nombreUsuario).Training);

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
    //endregion
}