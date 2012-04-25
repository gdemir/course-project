/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

import java.applet.Applet;
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
public class Adaline extends Applet {

    /**
     * @param args the command line arguments
     */
    public boolean errorgraph = true;
    
    // error kordinat çizimleri için
    JFrame frame;
    JPanel jp;
    BufferedImage image;
    
    private static List<Double> weight = new ArrayList<Double>();
    private static List<Double> errors = new ArrayList<Double>();
    private static double learningrate = 0.5;
    private static double bias = -1;
    private static int delaytime = 100;

    public Adaline(int dtime, boolean egraph) throws InterruptedException {
        delaytime = dtime;
        // Graph init begin
        if (egraph) {
            frame = Errorgraph.init();
        }
        // Graph end
        errorgraph = egraph;

        weight = new ArrayList<Double>();
        errors = new ArrayList<Double>();
    }
    public static void main(String[] args) throws InterruptedException {    
        try {
            List<List> io_elements = Matrix.fileread("train_adaline.txt", 2); // train : 3 column; test : 2 column
            List<List> input_elements = io_elements.get(0);
            List<Double> output_elements = io_elements.get(1);

            Adaline adaline = new Adaline(100, true);
            adaline.train(io_elements);
                io_elements = Matrix.fileread("test_adaline.txt", 2); // train : 3 column; test : 2 column
                input_elements = io_elements.get(0);
                output_elements = io_elements.get(1);

            adaline.test(input_elements);
            
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
    }

    public boolean train(List<List> io_elements) throws InterruptedException {
        System.out.println("Adaline#train");

        List<List> input_elements = io_elements.get(0);
        List<Double> output_elements = io_elements.get(1);
        int row = input_elements.size();
        int column = input_elements.get(0).size();

        initweight(column);

        double target, error, sum_error = 0;
        int i = 0, iteration = 0, epoch = 0;
        boolean error_state = false;

        do {
            error_state = false;
            epoch++;
            for (i = 0; i < row; i++){
                iteration++;
                target = process(input_elements.get(i), weight);
                System.out.println(iteration + ". iterasyon: Target: " + target + " weight: " + weight);
                if (target != output_elements.get(i)) {
                    error_state = true;
                    error = (output_elements.get(i) - target);
                    sum_error += error * error;

                    weight_repair(input_elements.get(i), error);
                    bias_repair(error);

                    error = Math.sqrt(sum_error / iteration);
                    errors.add(error);
                    // Graph update begin
                    if (errorgraph) {
                        Errorgraph.update(frame, errors, delaytime);
                    }
                }
            }
        } while(error_state);
        System.out.println("epoch: " + epoch);
        System.out.println("iteration: " + iteration);

        return error_state;
    }
    public void test(List<List> input_elements) {
        System.out.println("Adaline#test");
        double target;
        for (int i = 0; i < input_elements.size(); i++) {
            target = process(input_elements.get(i), weight);
            System.out.println("Inputs : " + input_elements.get(i)  + " Target: " + target);
        }
    }
    private double process(List<Double> input_elements, List<Double> w) {
        double net = 0;
        for (int i = 0; i < input_elements.size(); i++)
            net += input_elements.get(i) * w.get(i);
        net += bias;
        return (net >= 0) ? 1 : -1;
    }
    private void bias_repair(double error) {
        bias = bias + learningrate * error;
    }
    private void weight_repair(List<Double> input_elements, double error) {
        for (int i = 0; i < weight.size(); i++)
            weight.set(i, ((double)weight.get(i) + learningrate  * error * input_elements.get(i)));
    }
}