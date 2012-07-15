package tkaformplus;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Lvq {

    public boolean errorgraph = true;

    // error kordinat çizimleri için
    JFrame frame;
    JPanel jp;
    BufferedImage image;

    private static double E = 0.01;                 // hata için
    public int kohenencount = 2;         // kohenen katmanı sayısı
    public List<Double> euclideandistance = new ArrayList<Double>();
    public List<List> weight = new ArrayList<List>();
    private static List<Double> errors = new ArrayList<Double>();
    private double lambda = 0.5;
    private static int delaytime = 100;
    public int epochmax = 100;       // max epoch sayısı
    public List<Double> Y = new ArrayList<Double>();

    public Lvq (int emax, double error, int kcount, int dtime, boolean egraph) throws InterruptedException{

        epochmax = emax;
        E = error;
        kohenencount = kcount;
        delaytime = dtime;
        // Graph init begin
        if (egraph) {
            frame = Errorgraph.init();
        }
        // Graph end
        errorgraph = egraph;

        errors = new ArrayList<Double>();

        weight = new ArrayList<List>();
        euclideandistance = new ArrayList<Double>();
        Y = new ArrayList<Double>();
    }
    private void init(int column) {
        List<Double> vector;

        Random random = new Random();
        for (int i = 0; i < kohenencount; i++) {
            euclideandistance.add(0.0);
            vector = new ArrayList<Double>();
            for (int j = 0; j < column; j++)
                vector.add(random.nextGaussian()/10);
            weight.add(vector);
        }
    }
    private void initoutput(int row){
        for (int i = 0; i < row; i++) Y.add(0.0);
    }
    public boolean train(List<List> io_elements) throws InterruptedException {
        System.out.println("Lvq#train");

        List<List> input_elements = io_elements.get(0);
        List<Double> output_elements = io_elements.get(1);

        int row = input_elements.size();
        int column = input_elements.get(0).size();

        init(column);
        initoutput(row);

        int winningneuron, out, epoch = 0;
        List<Double> winner, input;
        boolean error_state = false;

        do {
            epoch++;
            for (int i = 0; i < row; i++) {
                winningneuron = process(i, column, input_elements);
                weightrepair(winningneuron, column, i, input_elements, output_elements);
            }
            error_state = rmse(row, output_elements);
        } while (error_state && epoch < epochmax);
        for (int i = 0; i < row; i++)
            System.out.println("Y["+i+"]: " + Y.get(i));
        System.out.println("epoch: " + epoch);

        if (error_state)
            error_state = (epoch == epochmax) ?  false : true;
        return error_state;
    }
    public void test(List<List> input_elements) {
        System.out.println("Lvq#test");

        int row = input_elements.size();
        int column = input_elements.get(0).size();

        int out, winningneuron;
        for (int i = 0; i < row; i++) {
            winningneuron =  process(i, column, input_elements);
            out = ((kohenencount / 2) <= winningneuron) ? 0 : 1;
            System.out.println("Inputs : " + input_elements.get(i)  + " Target: " + out);
        }
    }
    private void weightrepair(int winningneuron, int column, int i, List<List> input_elements, List<Double> output_elements) {

        double out = ((kohenencount / 2) <= winningneuron) ? 0 : 1;

        List<Double> winner = weight.get(winningneuron);
        List<Double> input = input_elements.get(i);
        Y.set(i, (double)out);
        if (output_elements.get(i) == out) {
            for (int j = 0; j < column; j++)
                weight.get(winningneuron).set(j, winner.get(j) +  lambda * (input.get(j) - winner.get(j)));
        } else {
            for (int j = 0; j < column; j++)
                weight.get(winningneuron).set(j, winner.get(j) -  lambda * (input.get(j) - winner.get(j)));
        }
    }
    private int process(int i, int column, List<List> input_elements) {
        int winningneuron = 0, out;
        for (int k = 0; k < kohenencount; k++) {
            euclideandistance.set(k, distance(column, weight.get(k), input_elements.get(i)));
            if (k != 0 && euclideandistance.get(k) < euclideandistance.get(winningneuron))
                winningneuron = k;
        }
        return winningneuron;
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
    private double distance(int column, List<Double> vector1, List<Double> vector2) {
        double distance = 0;
        for (int i = 0; i < column; i++)
            distance += Math.pow((vector1.get(i) - vector2.get(i)), 2);
        return distance;
    }
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException, InterruptedException {
        Lvq lvq = new Lvq(50, 0.001, 4, 100, true);
        List<List> io_elements = new ArrayList<List>();
        List<List> input_elements = new ArrayList<List>();
        List<Double> output_elements = new ArrayList<Double>();

        io_elements = Matrix.fileread("train_lvq.txt", 4);
        lvq.train(io_elements);

        io_elements = Matrix.fileread("test_lvq.txt", 4);
        input_elements = io_elements.get(0);

        lvq.test(input_elements);
    }
}