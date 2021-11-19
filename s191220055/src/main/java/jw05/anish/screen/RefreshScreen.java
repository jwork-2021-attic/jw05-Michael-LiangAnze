package jw05.anish.screen;

import javax.swing.JFrame;

public class RefreshScreen implements Runnable {
    JFrame mainWindow;

    public RefreshScreen(JFrame mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
            mainWindow.repaint();
        }
    }
}
