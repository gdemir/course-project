/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdemir
 */
public class Adaline {

    /**
     * @param args the command line arguments
     */
    private static List<List> output = new ArrayList<List>();
    private static List<Double> weight = new ArrayList<Double>();
    private static List<Double> errors = new ArrayList<Double>();
    private static double learningrate = 0.5;
    private static double bias = -1;

    public Adaline() {
    }
    public static void main(String[] args) {    
        try {
            List<List> o = new ArrayList<List>();
            List<List> io_elements = Matrix.fileread("train.txt", 2); // train : 3 column; test : 2 column
            List<List> input_elements = io_elements.get(0);
            List<Double> output_elements = io_elements.get(1);

            Adaline adaline = new Adaline();
            o = adaline.train(io_elements);
                io_elements = Matrix.fileread("test.txt", 2); // train : 3 column; test : 2 column
                input_elements = io_elements.get(0);
                output_elements = io_elements.get(1);

            Adaline.test(input_elements, o.get(0));
            
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
    
    public List<List> train(List<List> io_elements) {
        System.out.println("Adaline#train");
        double target, error, sum_error = 0;
        int i = 0, iteration = 0;

        List<List> input_elements = io_elements.get(0);
        List<Double> output_elements = io_elements.get(1);
        int row = input_elements.size();
        int column = input_elements.get(0).size();

        initweight(column);
        
        while (true) {
            iteration++;
            target = process(input_elements.get(i), weight);
            System.out.println(iteration + ". iterasyon: Target: " + target + " weight: " + weight);
            if (target == output_elements.get(i)) {
                i++; // diğer x'e geç
                if (i >= row) break; // her şey okey çıkıyoruz
            } else { // onar ve başa dön
                error = (output_elements.get(i) - target);
                sum_error += error * error;
                
                weight_repair(input_elements.get(i), error);
                bias_repair(error);
                
                error = Math.sqrt(sum_error / iteration);
                errors.add(error);
                System.out.print("error:"+error);
                i = 0;
            }
        }
        output.add(weight);
        output.add(errors);
        return output;
    }
    public static void test(List<List> input_elements, List<Double> w) {
        System.out.println("Adaline#test");
        double target;
        System.out.println(" weight: " + w);
        for (int i = 0; i < input_elements.size(); i++) {
            target = process(input_elements.get(i), w);
            System.out.println("Inputs : " + input_elements.get(i)  + " Target: " + target);
        }
    }
    private static double process(List<Double> input_elements, List<Double> w) {
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