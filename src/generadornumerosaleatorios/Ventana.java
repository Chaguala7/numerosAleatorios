package generadornumerosaleatorios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ACER
 */
public class Ventana extends JFrame {

    private JPanel[] panel;
    private JLabel[] etiqueta;
    private JTextField txfDesde, txfHasta;
    private JButton btnIniciar, btnPausar, btnDetener, btnPremiar;
    private Proceso proceso;
    private ScrollPane scrol;
    private JTextArea area;
    
    public Ventana() {
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Numeros Aleatorios");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Img/numero-1.png")));

        panel = new JPanel[11];
        for (int i = 0; i < 11; i++) {
            panel[i] = new JPanel();
        }

        etiqueta = new JLabel[10];
        for (int i = 0; i < 10; i++) {
            etiqueta[i] = new JLabel();
        }

        etiqueta[0].setText("Generador de numeros aleatorios");
        etiqueta[0].setFont(new Font("Arial", 1, 30));

        panel[0].add(etiqueta[0]);

        panel[1].setLayout(new GridLayout(1, 2));
        panel[1].add(panel[9]);
        panel[1].add(panel[6]);

        panel[9].setLayout(new GridLayout(2, 1));
        panel[9].add(panel[5]);
        panel[9].add(panel[10]);

        panel[5].setLayout(new GridLayout(1, 2));
        panel[5].add(panel[7]);
        panel[5].add(panel[8]);

        panel[7].setLayout(new BoxLayout(panel[7], BoxLayout.Y_AXIS));
        panel[8].setLayout(null);

        etiqueta[1].setText("Rango desde: ");
        etiqueta[2].setText("Hasta: ");

        panel[7].add(Box.createRigidArea(new Dimension(30, 30)));
        panel[7].add(etiqueta[1]);
        panel[7].add(Box.createRigidArea(new Dimension(30, 30)));
        panel[7].add(etiqueta[2]);

        txfDesde = new JTextField();
        txfDesde.setBounds(10, 30, 70, 30);
        txfDesde.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        txfHasta = new JTextField();
        txfHasta.setBounds(10, 70, 70, 30);
        txfHasta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        panel[8].add(txfDesde);
        panel[8].add(txfHasta);

        btnIniciar = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Img/boton-de-play_1.png"))));
        btnPausar = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Img/boton-de-pausa_1.png"))));
        btnPausar.setEnabled(false);
        btnDetener = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Img/boton-detener_1.png"))));
        btnDetener.setEnabled(false);
        btnPremiar = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Img/insignia.png"))));
        btnPremiar.setEnabled(false);

        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txfDesde.getText().trim().isEmpty() && !txfHasta.getText().trim().isEmpty()) {
                    if (proceso == null || !proceso.isAlive()) {
                        proceso = new Proceso(btnIniciar, etiqueta[3], Integer.parseInt(txfDesde.getText()), Integer.parseInt(txfHasta.getText()));
                        proceso.start();
                        btnPausar.setEnabled(true);
                        btnDetener.setEnabled(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, complete los campos de rango");
                }
            }
        });

        // Pausar el proceso
        btnPausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (proceso != null) {
                    proceso.pausar();
                    btnPremiar.setEnabled(true);
                }
            }
        });

        // Detener el proceso
        btnDetener.addActionListener((ActionEvent e) -> {
            if (proceso != null) {
                proceso.detener();
                proceso = null; // Permitir crear un nuevo hilo más adelante
                btnPremiar.setEnabled(false);
                btnPausar.setEnabled(false);
                btnDetener.setEnabled(false);
                area.setText("");
            }
        });
        
        btnPremiar.addActionListener((ActionEvent e) -> {
            area.append(etiqueta[3].getText() + "\n");
        });
        
        panel[10].add(btnIniciar);
        panel[10].add(btnPausar);
        panel[10].add(btnDetener);
        panel[10].add(btnPremiar);

        panel[6].setLayout(new BoxLayout(panel[6], BoxLayout.Y_AXIS));
        etiqueta[3].setFont(new Font("Arial", 1, 50));
        etiqueta[3].setText("0");
        etiqueta[3].setPreferredSize(new Dimension(250,300));
        panel[6].add(Box.createRigidArea(new Dimension(30, 30)));
        panel[6].add(etiqueta[3]);
        
        area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        scrol = new ScrollPane();
        scrol.add(area);

        add(panel[0], BorderLayout.NORTH);
        add(panel[1], BorderLayout.CENTER);
        add(panel[2], BorderLayout.SOUTH);
        add(panel[3], BorderLayout.WEST);
        add(scrol, BorderLayout.EAST);

    }

}
