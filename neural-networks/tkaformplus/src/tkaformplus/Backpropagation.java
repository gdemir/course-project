/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author gdemir
 */
public class Backpropagation {

    /**
     * @param args the command line arguments
     */

    public boolean errorgraph = true;

    private static List<Double> errors = new ArrayList<Double>();

    public static List<Double> bias_input = new ArrayList<Double>();
    public static List<Double> bias_output = new ArrayList<Double>();
    public static List<Double> dbias_input = new ArrayList<Double>();
    public static List<Double> dbias_output = new ArrayList<Double>();

    public List<List> weight_input = new ArrayList<List>();
    public List<Double> weight_output = new ArrayList<Double>();
    public List<List> dweight_input = new ArrayList<List>();
    public List<Double> dweight_output = new ArrayList<Double>();

    public List<Double> H = new ArrayList<Double>();
    public List<Double> Y = new ArrayList<Double>();    
    private static List<Double> keynH = new ArrayList<Double>();
    private static double keynY;

    // error kordinat çizimleri için
    JFrame frame;
    JPanel jp;
    BufferedImage image;

    // sabitler
    private static int hiddencount = 2;
    private static int epochmax = Integer.MAX_VALUE;
    private static double E = 0.01;                 // hata için
    private static double momentconst = 0.8;
    private static double lambda = 0.5;
    private static int delaytime = 100;

    public Backpropagation(int emax, double error, int hcount, int dtime, boolean egraph) throws InterruptedException {

        epochmax = emax;
        E = error;
        hiddencount = hcount;
        delaytime = dtime;
        // Graph init begin
        if (egraph) {
            frame = Errorgraph.init();
        }
        // Graph end
        errorgraph = egraph;

        errors = new ArrayList<Double>();

        bias_input = new ArrayList<Double>();
        bias_output = new ArrayList<Double>();
        dbias_input = new ArrayList<Double>();
        dbias_output = new ArrayList<Double>();

        weight_input = new ArrayList<List>();
        weight_output = new ArrayList<Double>();
        dweight_input = new ArrayList<List>();
        dweight_output = new ArrayList<Double>();

        H = new ArrayList<Double>();
        Y = new ArrayList<Double>();
        keynH = new ArrayList<Double>();
        keynY = 0.0;
    }

    private void init(int row, int column, int hiddencount) {
        List<Double> vector, vector2;
        int i, j;

        Random random = new Random();
        for (i = 0; i < hiddencount; i++) {
            bias_input.add(random.nextGaussian()/10);
            dbias_input.add(0.0);
            weight_output.add(random.nextGaussian()/10);
            dweight_output.add(0.0);
        }
        for (i = 0; i < column; i++) {
            vector = new ArrayList<Double>();
            vector2 = new ArrayList<Double>();
            for (j = 0; j < hiddencount; j++) {
                vector.add(random.nextGaussian()/10);
                vector2.add(random.nextGaussian()/10);
            }
            weight_input.add(vector);
            dweight_input.add(vector2);
        }
        bias_output.add(random.nextGaussian()/10);
        dbias_output.add(0.0);
    }
    public void initoutput(int hiddencount, int row) {
        int i;
        for (i = 0; i < hiddencount; i++) H.add(0.0);
        for (i = 0; i < row; i++) Y.add(0.0);
    }
    public void initkeynh(int hiddencount) {
        for (int i = 0; i < hiddencount; i++)
            keynH.add(0.0);
        keynY = 0.0;
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        int hcount = 3;
        Backpropagation backpropagation = new Backpropagation(5, 0.05, hcount, 1, true);

        List<List> io_elements = new ArrayList<List>();
        List<List> input_elements = new ArrayList<List>();
        List<Double> output_elements = new ArrayList<Double>();

        int column = 2;
        io_elements = Matrix.fileread("train_xor.txt", column);
        backpropagation.train(io_elements);

        io_elements = Matrix.fileread("test_xor.txt", column);
        input_elements = io_elements.get(0);

        backpropagation.test(hiddencount, input_elements);
    }
    public boolean train(List<List> io_elements) throws InterruptedException {
        System.out.println("Backpropagation#train");

        List<List> input_elements = io_elements.get(0);
        List<Double> output_elements = io_elements.get(1);

        int row = input_elements.size();
        int column = input_elements.get(0).size();

        init(row, column, hiddencount);
        initoutput(hiddencount, row);
        initkeynh(hiddencount);     // sadece hidden kadar keynh üret

        int i = 0, epoch = 0;
        boolean error_state = false;
        do {

            epoch++;
            for (i = 0; i < row; i++){
                process(hiddencount, i, input_elements.get(i), column);
                keyn(hiddencount, i, output_elements);
                change(hiddencount, i, input_elements, column);
            }
            error_state = rmse(row, output_elements);
        } while(error_state && epoch < epochmax);
        for (i = 0; i < row; i++)
            System.out.println("Y["+i+"]: " + Y.get(i));
        System.out.println("epoch: " + epoch);
        
        return error_state;
    }
    public void test(int hiddencount, List<List> input_elements) {
        System.out.println("Backpropagation#test");
        int i, row = input_elements.size();
        int column = input_elements.get(0).size();

        initoutput(hiddencount, row);
        for (i = 0; i < row; i++)
            process(hiddencount, i, input_elements.get(i), column);

        for (i = 0; i < row; i++)
            System.out.println(Y.get(i));
    }
    public void process(int hiddencount, int j, List<Double> input_elements, int column) {
        int h, i;
        List<Double> wx;

        for (h = 0; h < hiddencount; h++) {
            H.set(h, 0.0);
            for (i = 0; i < column; i++) {
                wx = weight_input.get(i);
                H.set(h, (H.get(h) + input_elements.get(i) * wx.get(h)));
            }
            H.set(h, sigmoid(H.get(h) + bias_input.get(h)));
        }
        Y.set(j, 0.0);
        for (h = 0; h < hiddencount; h++)
            Y.set(j, Y.get(j) + H.get(h) * weight_output.get(h));
        Y.set(j, sigmoid(Y.get(j) + bias_output.get(0)));
    }
    public static List<Double> process2(int hiddencount, int j, List<Double> input_elements, List<List> wi, List<Double> wo,List<Double> bi, List<Double> bo, List<Double> h, List<Double> y) {
        int k, i;
        List<Double> wx;

        for (k = 0; k < hiddencount; k++) {
            h.set(k, 0.0);
            for (i = 0; i < input_elements.size(); i++) {
                wx = wi.get(i);
                h.set(k, h.get(k) + input_elements.get(i) * wx.get(k));
            }
            h.set(k, sigmoid(h.get(k) + bi.get(k)));
        }
        y.set(j, 0.0);
        for (k = 0; k < hiddencount; k++) {
            y.set(j, y.get(j) + h.get(k) * wo.get(k));
        }
        y.set(j, sigmoid(y.get(j) + bo.get(0)));
        return y;
    }
    public boolean rmse(int row, List<Double> output_elements) throws InterruptedException {
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
    public static double sigmoid(double x) {// sigmoid fonksiyonu
        return 1.0 / (1.0 + Math.exp(-x));
    }
    public void keyn(int hiddencount, int j, List<Double> output_elements) {// keyH keyH hesaplar
        keynY = Y.get(j) * (1.0 - Y.get(j)) * (output_elements.get(j) - Y.get(j));
        for (int h = 0; h < hiddencount; h++){
            keynH.set(h, H.get(h) * (1.0 - H.get(h)) * weight_output.get(h) * keynY);
        }
    }
    public void change(int hiddencount, int j, List<List> input_elements, int column) {// Weight ve Bias değerlerinin güncellenmesi
        List<Double> wi, inp, dwi;   

        for (int h = 0; h < hiddencount; h++){
            dweight_output.set(h, lambda * keynY * H.get(h) + momentconst * dweight_output.get(h));
            weight_output.set(h, weight_output.get(h) + dweight_output.get(h));
            for (int i = 0; i < column; i++){
                inp = input_elements.get(j);
                wi = weight_input.get(i);
                dwi = dweight_input.get(i);
                dweight_input.get(i).set(h, lambda * keynH.get(h) * inp.get(i) + momentconst * dwi.get(h));
                dwi = dweight_input.get(i);
                weight_input.get(i).set(h, wi.get(h) + dwi.get(h));
            }
            dbias_input.set(h, lambda * keynH.get(h) + momentconst * dbias_input.get(h));
            bias_input.set(h, bias_input.get(h) + dbias_input.get(h));
        }
        dbias_output.set(0, lambda * keynY + momentconst * dbias_output.get(0));
        bias_output.set(0, bias_output.get(0) + dbias_output.get(0));
    } 
}