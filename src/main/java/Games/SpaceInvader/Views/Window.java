package Games.SpaceInvader.Views;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window {

    public Window() {
        JFrame frame = new JFrame("Space Invader");
        Game f = new Game();
        frame.add(f);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 420);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        try {
            URL url = getClass().getResource("/SpaceInvader/gsicon.png");
            BufferedImage image = ImageIO.read(url);
            frame.setIconImage(image);
        } catch (IOException e) {
            System.out.println("ImageError: " + e);
        }
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Set the System look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Window window = new Window();
        });

    }
}
