/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaform;

/**
 *
 * @author gdemir
 */
public class Adaline {

    /**
     * @param args the command line arguments
     */
    public static void main(double[][] x, double[] w, int r, int c, double lambda, double threshold) {

        int i = 0;
        double net, target, e;
        int iterasyon = 0;
        System.out.println("Adaline Algorithm");
        while (true) {
            iterasyon++;
            net = net(x[i], w, c);
            target = target(net);
            System.out.println(iterasyon + ". iterasyon: Target: " + target + " Out: " + net + " weight1: " + w[0] + " weight2: " + w[1]);
            if (target == x[i][c - 1]) {
                i++; // diğer x'e geç
                if (i >= r) break;
            } else { // onar ve başa dön
                e = (x[i][c - 1] - (double)target);
                w = repair_weight(x[i], w, lambda, e);
                threshold = repair_threshold(threshold, lambda, e);
                if (w[0] < -1 || w[1] < -1) break;
                i = 0; 
            }
        }
    }
    public static double target(double net) {
        return (net >= 0) ? 1 : -1;
    }
    public static double repair_threshold(double threshold, double lambda, double e) {
        return threshold + lambda * e;
    }
    public static double[] repair_weight(double[] x, double[] w, double lambda, double e) {
        for (int i = 0; i < w.length; i++)
            w[i] = w[i] + lambda * e * x[i];
        return w;
    }
    public static double net(double[] x, double[] w, int c) {
        double net = 0;
        for (int i = 0; i < c - 1; i++)
            net += x[i] * w[i];
        return net;
    }
}
