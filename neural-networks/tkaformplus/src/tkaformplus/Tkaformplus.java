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

/**
 *
 * @author gdemir
 */
public class Tkaformplus {

    /**
     * @param args the command line arguments
     */
    public static List<List> main(boolean process, int choice, List<List> io_elements, int hiddencount, final List<List> o) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        List<List> output = new ArrayList<List>();
        List<List> input_elements = io_elements.get(0);
        List<Double> output_elements = io_elements.get(1);

        switch (choice) {
            case 0:
                if (process) {
                    Perceptron perceptron = new Perceptron();
                    output = perceptron.train(io_elements);
                    Chart.plot(null);
                }
                else Perceptron.test(input_elements, o.get(0)); // 1 process var weight değeri
                break;
            case 1:
                if (process) {
                    Adaline adaline = new Adaline();
                    output = adaline.train(io_elements);
                    Chart.plot(output.get(1));
                }
                else Adaline.test(input_elements, o.get(0));   // 1 process var weight değeri
                break;
            case 2:
                if (process) {
                    Backpropagation backpropagation = new Backpropagation();
                    output = backpropagation.train(hiddencount, io_elements);
                    Chart.plot(output.get(4));
                }
                else Backpropagation.test(hiddencount, input_elements, o.get(0),o.get(1),o.get(2),o.get(3));
                break;
            default: System.out.print("wtf?!");
        }
        return (process) ? output : null;
    }
}
