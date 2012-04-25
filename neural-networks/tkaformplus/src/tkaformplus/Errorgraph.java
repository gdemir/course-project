/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author gdemir
 */
public class Errorgraph {
    public static JFrame init() throws InterruptedException {
        JFrame frame = new JFrame();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setTitle("kordinatlar");
        frame.setSize(620, 640);
        frame.setLocation(Math.abs((dimension.width-frame.getSize().width)/2),0);
        update(frame, null, 1);
        frame.setVisible(true);
        return frame;
    }
    public static void update(JFrame frame, List<Double> errors, int delaytime) throws InterruptedException {
        JFreeChart chart = Chart.plot(errors);
        final BufferedImage image = chart.createBufferedImage(600, 600, BufferedImage.TYPE_INT_RGB, null);
        JPanel jp = new JPanel() {
                   @Override
                   public void paintComponent( Graphics g ) {
                       super.paintComponent(g);
                       g.drawImage(image, 0, 0, null);
                   }
               };
        jp.repaint();
        jp.setVisible(true);
        Thread.sleep(delaytime);
        frame.add(jp);
        frame.setVisible( true );
    }
}