/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaform;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author gdemir
 */
public class Tkaform {

    /**
     * @param args the command line arguments
     */
    public static void main(int choice, double[] w, double lambda, double threshold) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        int r, c = 3;
        double[][] x = Files.read("dosya.txt", c);
        r = x.length;

        switch (choice) {
            case 0:  Perceptron.main(x, w, r, c, lambda, threshold);break;
            case 1:  Adaline.main(x, w, r, c, lambda, threshold);break;
            case 2:  System.out.print("henüz madaline hazır değil");break;
            default: System.out.print("wtf?!");
        }

    }
}
