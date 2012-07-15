/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

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
 
    public boolean errorgraph = true;

    // error kordinat çizimleri için
    JFrame frame;
    JPanel jp;
    BufferedImage image;
    
    private static double E = 0.01;                 // hata için
    private static List<Double> weight = new ArrayList<Double>();
    private static List<Double> errors = new ArrayList<Double>();
    private static double learningrate = 0.5;
    private static double bias = -1;
    private static int delaytime = 100;
    private static int epochmax = Integer.MAX_VALUE;
    public List<Double> Y = new ArrayList<Double>();

    public Perceptron(int emax, double error, int dtime, boolean egraph) throws InterruptedException {
        epochmax = emax;
        E = error;
        delaytime = dtime;
        // Graph init begin
        if (egraph) {
            frame = Errorgraph.init();
        }
        // Graph end
        errorgraph = egraph;

        errors = new ArrayList<Double>();

        weight = new ArrayList<Double>();
        Y = new ArrayList<Double>();
    }
    private void initweight(int column) {
        for (int i = 0; i < column; i++)
            weight.add(1 + Math.random());
        //weight.set(0, 1.0);
        //weight.set(1, 2.0);
    }
   private void initoutput(int row){
        for (int i = 0; i < row; i++) Y.add(0.0);
    }
    public boolean train(List<List> io_elements) throws InterruptedException {
        System.out.println("Perceptron#train");

        List<List> input_elements = io_elements.get(0);
        List<Double> output_elements = io_elements.get(1);
        int row = input_elements.size();
        int column = input_elements.get(0).size();

        initweight(column);
        initoutput(row);
        
        double target;
        int i = 0, iteration = 0, epoch = 0;
        boolean error_state = false;

        do {
            epoch++;
            for (i = 0; i < row; i++) {
                iteration++;
                target = process(input_elements.get(i));
                Y.set(i, target);
                //System.out.println(iteration + ". iterasyon: Target: " + target + "Beklenen: " + output_elements.get(i) + " weight: " + weight);
                if (target != output_elements.get(i)) {
                    weight_repair((target > output_elements.get(i)) ? false : true, input_elements.get(i));
                }
            }
            error_state = rmse(row, output_elements);
        } while(error_state && epoch < epochmax);
        for (i = 0; i < row; i++)
            System.out.println("Y["+i+"]: " + Y.get(i));
        System.out.println("epoch: " + epoch);
        System.out.println("iteration: " + iteration);

        if (error_state)
            error_state = (epoch == epochmax) ?  false : true;
        return error_state;
    }
    public void test(List<List> input_elements) {
        System.out.println("Perceptron#test");
        double target;

        int row = input_elements.size();

        initoutput(row);
        for (int i = 0; i < row; i++) {
            target = process(input_elements.get(i));
            System.out.println("Inputs : " + input_elements.get(i)  + " Target: " + target);
        }
    }
    private boolean rmse(int row, List<Double> output_elements) throws InterruptedException {
            double error = 0.0;
            for (int i = 0; i < row; i++)
                error += Math.pow(Y.get(i) - output_elements.get(i), 2);
            error = Math.sqrt(error / row);
            errors.add(error);
            System.out.println("error:" + error);
             // Graph update begin
            if (errorgraph) {
                Errorgraph.update(frame, errors, delaytime);
            }
            // Graph end
            return (error > E) ? true : false;
   }
   private static double process(List<Double> input_elements) {
        double net = 0;
        for (int i = 0; i < input_elements.size(); i++)
            net += input_elements.get(i) * weight.get(i);
        return (net > bias) ? 1 : 0;
   }
   private void weight_repair(boolean choice, List<Double> input_elements) {
        for (int i = 0; i < weight.size(); i++)
            if (choice)
                weight.set(i, ((double)weight.get(i) + learningrate * input_elements.get(i)));
            else
                weight.set(i, ((double)weight.get(i) - learningrate * input_elements.get(i)));
    }
   public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {    
        try {
            List<List> io_elements = Matrix.fileread("train_perceptron.txt", 2); // train : 3 column; test : 2 column
            List<List> input_elements = io_elements.get(0);
            List<Double> output_elements = io_elements.get(1);

            Perceptron perceptron = new Perceptron(1000, 0.01, 100, true);
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
}