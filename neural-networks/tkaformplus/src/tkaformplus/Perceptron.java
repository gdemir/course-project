/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author gdemir
 */
public class Perceptron {
 
    /**
     * @param args the command line arguments
     */
    public boolean errorgraph = true;

    // error kordinat çizimleri için
    JFrame frame;
    JPanel jp;
    BufferedImage image;
    
    private static List<Double> weight = new ArrayList<Double>();
    private static double learningrate = 0.5;
    private static double bias = -1;
    private static int delaytime = 100;

    public Perceptron(int dtime, boolean egraph) throws InterruptedException {
        delaytime = dtime;
        // Graph init begin
        if (egraph) {
            frame = Errorgraph.init();
        }
        // Graph end
        errorgraph = egraph;
        weight = new ArrayList<Double>();
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {    
        try {
            List<List> io_elements = Matrix.fileread("train_perceptron.txt", 2); // train : 3 column; test : 2 column
            List<List> input_elements = io_elements.get(0);
            List<Double> output_elements = io_elements.get(1);

            Perceptron perceptron = new Perceptron(100, true);
            perceptron.train(io_elements);
                io_elements = Matrix.fileread("test_perceptron.txt", 2); // train : 3 column; test : 2 column
                input_elements = io_elements.get(0);
                output_elements = io_elements.get(1);
            perceptron.test(input_elements);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Adaline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Adaline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Adaline.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void initweight(int column) {
        for (int i = 0; i < column; i++)
            weight.add(1 + Math.random());
        //weight.set(0, 1.0);
        //weight.set(1, 2.0);
    }
    public List<Double> getweight() {
        return weight;
    }
    public boolean train(List<List> io_elements) throws InterruptedException {
        // Graph init begin
        if (errorgraph) {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            frame.getContentPane().setBackground(Color.WHITE);
            frame.setTitle("kordinatlar");
            frame.setSize(620, 640);
            frame.setLocation(Math.abs((dimension.width-frame.getSize().width)/2),0);
            frame.setVisible(true);
        }
        // Graph end
        System.out.println("Perceptron#train");

        List<List> input_elements = io_elements.get(0);
        List<Double> output_elements = io_elements.get(1);
        int row = input_elements.size();
        int column = input_elements.get(0).size();

        initweight(column);

        double target;
        int i = 0, iteration = 0, epoch = 0;
        boolean error_state = false;

        do {
            error_state = false;
            epoch++;
            for (i = 0; i < row; i++) {
                iteration++;
                target = process(input_elements.get(i), weight);
                System.out.println(iteration + ". iterasyon: Target: " + target + "Beklenen: " + output_elements.get(i) + " weight: " + weight);
                if (target != output_elements.get(i)) {
                    error_state = true;
                    weight_repair((target > output_elements.get(i)) ? false : true, input_elements.get(i));
                    // Graph update begin
                    if (errorgraph) {
                        Errorgraph.update(frame, null, delaytime);
                    }
                    // Graph end
                }
            }
        } while(error_state);
        System.out.println("epoch: " + epoch);
        System.out.println("iteration: " + iteration);

        return error_state;
    }
    public void test(List<List> input_elements) {
        System.out.println("Perceptron#test");
        double target;

        for (int i = 0; i < input_elements.size(); i++) {
            target = process(input_elements.get(i), weight);
            System.out.println("Inputs : " + input_elements.get(i)  + " Target: " + target);
        }
    }
    private static double process(List<Double> input_elements, List<Double> w) {
        double net = 0;
        for (int i = 0; i < input_elements.size(); i++)
            net += input_elements.get(i) * w.get(i);
        return (net > bias) ? 1 : 0;
   }
   private void weight_repair(boolean choice, List<Double> input_elements) {
        for (int i = 0; i < weight.size(); i++)
            if (choice)
                weight.set(i, ((double)weight.get(i) + learningrate * input_elements.get(i)));
            else
                weight.set(i, ((double)weight.get(i) - learningrate * input_elements.get(i)));
    }
}