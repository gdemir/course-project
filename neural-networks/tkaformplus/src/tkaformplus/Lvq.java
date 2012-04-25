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
        public int epochmax = 100;       // max epoch sayısı
        public int kohenencount;         // kohenen katmanı sayısı
        private double lambda = 0.5;

        
        // error kordinat çizimleri için
        JFrame frame;
        JPanel jp;
        BufferedImage image;

        public List<Double> euclideandistance = new ArrayList<Double>();
        public List<List> weight = new ArrayList<List>();
        private static int delaytime = 100;

        public Lvq (int emax, int kcount, int dtime, boolean egraph) throws InterruptedException{
            
            epochmax = emax;
            kohenencount = kcount;
            delaytime = dtime;
             // Graph init begin
            if (egraph) {
                frame = Errorgraph.init();
            }
            // Graph end
            errorgraph = egraph;

            euclideandistance = new ArrayList<Double>();
            weight = new ArrayList<List>();
        }
        private void init(int kohenencount, int column) {
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
        private double distance(int column, List<Double> vector1, List<Double> vector2) {
             double distance = 0;
             for (int i = 0; i < column; i++)
                 distance += Math.pow((vector1.get(i) - vector2.get(i)), 2);
             return distance;
        }
        public boolean train(List<List> io_elements) throws InterruptedException {
            System.out.print("Lvq train");

            List<List> input_elements = io_elements.get(0);
            List<Double> output_elements = io_elements.get(1);

            int row = input_elements.size();
            int column = input_elements.get(0).size();

            init(kohenencount, column);

            int winningneuron, out, epoch;
            List<Double> winner, input;
            for (epoch = 0; epoch < epochmax; epoch++) {
                for (int i = 0; i < row; i++) {
                    winningneuron = 0;
                    for (int k = 0; k < kohenencount; k++) {
                        euclideandistance.set(k, distance(column, weight.get(k), input_elements.get(i)));
                        if (k != 0 && euclideandistance.get(k) < euclideandistance.get(winningneuron))
                            winningneuron = k;
                    }
                    out = ((kohenencount / 2) <= winningneuron) ? 0 : 1;
                    winner = weight.get(winningneuron);
                    input = input_elements.get(i);
                    if (output_elements.get(i) == out)
                        for (int j = 0; j < column; j++)
                            weight.get(winningneuron).set(j, winner.get(j) +  lambda * (input.get(j) - winner.get(j)));
                    else
                        for (int j = 0; j < column; j++)
                            weight.get(winningneuron).set(j, winner.get(j) -  lambda * (input.get(j) - winner.get(j)));
                    System.out.println("target: " + output_elements.get(i) + "out: " + out);
                }
                // Graph update begin
                if (errorgraph) {
                    Errorgraph.update(frame, null, delaytime);
                }
                // Graph end
            }
            System.out.println("epoch: " + epoch);
            return (epoch == epochmax) ?  false : true;
        }
        public void test(List<List> input_elements) {
            System.out.println("Lvq test");

            int row = input_elements.size();
            int column = input_elements.get(0).size();

            int winningneuron, out;
            for (int i = 0; i < row; i++) {
                winningneuron = 0;
                for (int k = 0; k < kohenencount; k++) {
                    euclideandistance.set(k, distance(column, weight.get(k), input_elements.get(i)));
                    if (k != 0 && euclideandistance.get(k) < euclideandistance.get(winningneuron))
                        winningneuron = k;
                }
                out = ((kohenencount / 2) <= winningneuron) ? 0 : 1;
                System.out.println("Input["+i+"] -> ClusterNo:" + out);
            }
        }
        
        public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException, InterruptedException {
            Lvq lvq = new Lvq(190, 2, 100, false);
            List<List> io_elements = new ArrayList<List>();
            List<List> input_elements = new ArrayList<List>();
            List<Double> output_elements = new ArrayList<Double>();

            io_elements = Matrix.fileread("train_lvq.txt", 2);
            lvq.train(io_elements);

            io_elements = Matrix.fileread("test_lvq.txt", 2);
            input_elements = io_elements.get(0);

            lvq.test(input_elements);
         }
 }