/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author gdemir
 */
public class Backpropagation {

    /**
     * @param args the command line arguments
     */
    
    private static List<Double> errors = new ArrayList<Double>();
    
    public static List<Double> bias_input = new ArrayList<Double>();
    public static List<Double> bias_output = new ArrayList<Double>();

    public List<List> weight_input = new ArrayList<List>();
    public List<Double> weight_output = new ArrayList<Double>();
    public List<Double> H = new ArrayList<Double>();
    public List<Double> Y = new ArrayList<Double>();
    private static List<Double> keynH = new ArrayList<Double>();
    private static double keynY;

    private static int epochmax = Integer.MAX_VALUE;
    private static double E = 0.004;                 // hata için
    private static double momentconst = 0.8;
    private static double lambda = 0.5;

    public Backpropagation() {
    }
    private void init(int row, int column, int hiddencount) {
        List<Double> vector;
        int i, j;
        
        Random random = new Random();
        for (i = 0; i < hiddencount; i++) {
            bias_input.add(random.nextGaussian()/10);
            weight_output.add(random.nextGaussian()/10);
        }
        for (i = 0; i < column; i++) {
            vector = new ArrayList<Double>();
            for (j = 0; j < hiddencount; j++)
                vector.add(random.nextGaussian()/10);
            weight_input.add(vector);
        }
        
        bias_output.add(random.nextGaussian()/10);
        for (i = 0; i < hiddencount; i++) H.add(0.0);
        for (i = 0; i < row; i++) Y.add(0.0);
    }
    public void initkeynh(int hiddencount) {
        for (int i = 0; i < hiddencount; i++)
            keynH.add(0.0);
    }
    public static void main(String[] args) {
        Backpropagation bbbb = new Backpropagation();

        List<List> io_elements = new ArrayList<List>();
        List<List> input_elements = new ArrayList<List>();
        List<Double> output_elements = new ArrayList<Double>();

        List<Double> vector = new ArrayList<Double>();

        vector = new ArrayList<Double>();vector.add(0.0);vector.add(0.0);input_elements.add(vector);
        output_elements.add(0.0);
        vector = new ArrayList<Double>();vector.add(0.0);vector.add(0.1);input_elements.add(vector);
        output_elements.add(0.1);

        io_elements.add(input_elements);
        io_elements.add(output_elements);
        int hiddencount = 2;
        List<List> output = bbbb.train(hiddencount, io_elements);

        input_elements = new ArrayList<List>();

        vector = new ArrayList<Double>();vector.add(0.0);vector.add(0.1);input_elements.add(vector);
        vector = new ArrayList<Double>();vector.add(0.1);vector.add(0.1);input_elements.add(vector);

        System.out.println("weightsi :" + output.get(0));
        System.out.println("weightso :" + output.get(1));
        System.out.println("biasi :" + output.get(2));
        System.out.println("biaso :" + output.get(3));
        Backpropagation.test(hiddencount, input_elements, output.get(0),output.get(1),output.get(2),output.get(3));
    }
    public static void test(int hiddencount, List<List> input_elements, List<List> wi, List<Double> wo, List<Double> bi, List<Double> bo) {
        System.out.println("Backpropagation#test");
        int i, row = input_elements.size();

        List<Double> h = new ArrayList<Double>();for (i = 0; i < hiddencount; i++) h.add(0.0);
        List<Double> y = new ArrayList<Double>();for (i = 0; i < row; i++) y.add(0.0);
        List<Double> o = new ArrayList<Double>();

        for (i = 0; i < row; i++)
            o = process2(hiddencount, i, input_elements.get(i), wi, wo, bi, bo, h, y);
        for (i = 0; i < row; i++)
         System.out.println(o.get(i));
    }
    public List<List> train(int hiddencount, List<List> io_elements) {
        System.out.println("Backpropagation#train");
        
        List<List> input_elements = io_elements.get(0);
        List<Double> output_elements = io_elements.get(1);

        int row = input_elements.size();
        int column = input_elements.get(0).size();
        init(row, column, hiddencount);
        initkeynh(hiddencount);     // sadece hidden kadar keynh üret

        int i = 0, epoch = 0;
        boolean state;
        do {
            
            epoch++;
            for (i = 0; i < row; i++){
                process(hiddencount, i, input_elements.get(i), column);
                keyn(hiddencount, i, output_elements);
                change(hiddencount, i, input_elements, column);
            }
            state = rmse(row, output_elements);
        } while(!state && epoch < epochmax);
        for (i = 0; i < row; i++)
            System.out.println("Y["+i+"]: " + Y.get(i));
        List<List> output = new ArrayList<List>();
        output.add(weight_input);
        output.add(weight_output);
        output.add(bias_input);
        output.add(bias_output);
        output.add(errors);
        return output;
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
            Y.set(j, Y.get(j) + (H.get(h) * weight_output.get(h)));
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
    public boolean rmse(int row, List<Double> output_elements) {
            double error = 0.0;
            for (int i = 0; i < row; i++)
                error += Math.pow(Y.get(i) - output_elements.get(i), 2);
            error = Math.sqrt(error / row);
            errors.add(error);
            //System.out.print("error:"+error);
            return (error > E) ? false : true;
    }
    
    public static double sigmoid(double x) {// sigmoid fonksiyonu
        return 1.0 / (1.0 + Math.exp(-x));
    }
    
    public void keyn(int hiddencount, int j, List<Double> output_elements) {//keyH keyH hsaplar
        keynY = Y.get(j) * (1.0 - Y.get(j)) * (output_elements.get(j) - Y.get(j));
        for (int h = 0; h < hiddencount; h++){
            keynH.set(h, H.get(h) * (1.0 - H.get(h)) * weight_output.get(h) * keynY);
        }
    }
    public void change(int hiddencount, int j, List<List> input_elements, int column) {// Weight ve Bias değerlerinin güncellenmesi
        int i;
        List<Double> wx, inp;
        double dwo = 0.0, dwi = 0.0, dbi = 0.0, dbo = 0.0;    

        for (int h = 0; h < hiddencount; h++){
            dwo = lambda * keynY * H.get(h) + momentconst * dwo;
            weight_output.set(h, weight_output.get(h) + dwo);
            for (i = 0; i < column; i++){
                inp = input_elements.get(j);
                wx = weight_input.get(i);
                dwi = lambda * keynH.get(h) * inp.get(i) + momentconst * dwi;   
                weight_input.get(i).set(h, wx.get(h) + dwi);
            }
            dbi = lambda * keynH.get(h) + momentconst * dbi;
            bias_input.set(h, bias_input.get(h) + dbi);
        }
        dbo = lambda * keynY + momentconst * dbo;
        bias_output.set(0, bias_output.get(0) + dbo);

    }
}