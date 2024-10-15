package generadornumerosaleatorios;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Proceso extends Thread {

    private JButton boton;
    private JLabel etiqueta;
    private int desde, hasta;
    private volatile boolean paused = false;
    private volatile boolean running = true;

    public Proceso(JButton boton, JLabel etiqueta, int desde, int hasta) {
        this.boton = boton;
        this.etiqueta = etiqueta;
        this.desde = desde;
        this.hasta = hasta - desde;
    }

    @Override
    public void run() {
        boton.setEnabled(false);
        while (running) {
            synchronized (this) {
                if (!paused) { // Si no está pausado, generar número aleatorio
                    int x = (int) (Math.random() * hasta) + desde;
                    etiqueta.setText("" + x);
                    try {
                        Thread.sleep(100); // Simula trabajo
                    } catch (InterruptedException ex) {
                        //Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        boton.setEnabled(true); 
        etiqueta.setText("0");
    }

    public void pausar() {
        paused = !paused;
    }

    public synchronized void reanudar() {
        paused = false;
    }

    public void detener() {
        running = false;
        this.interrupt(); 
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }
}
