/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;


import java.awt.Canvas;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdemir
 */
class ICanvas extends Canvas implements Runnable {

    boolean train = false;

    Perceptron perceptron = null;
    Adaline adaline = null;
    Backpropagation backpropagation = null;
    Lvq lvq = null;
    Art art = null;

    public List<List> io_elements;

    public void startAnimation(Art algorithm) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        this.art = algorithm;
        start();
    }
    public void startAnimation(Lvq algorithm) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        this.lvq = algorithm;
        start();
    }
    public void startAnimation(Backpropagation algorithm) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        this.backpropagation = algorithm;
        start();
    }
    public void startAnimation(Adaline algorithm) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        this.adaline = algorithm;
        start();
    }
    public void startAnimation(Perceptron algorithm) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        this.perceptron = algorithm;
        start();
    }
    public void start() {
        Thread th  = new Thread(this);
        // th.setPriority(Thread.MIN_PRIORITY);
        th.start();
    }
    @Override
    public void run() {
      List<Boolean> state = null;
      boolean error = false;
      while (train) {
         if (train == false) break;
         try {
               repaint();         
               if (this.perceptron != null)      error = this.perceptron.train(io_elements);
               if (this.adaline != null)         error = this.adaline.train(io_elements);
               if (this.backpropagation != null) error = this.backpropagation.train(io_elements);
               if (this.lvq != null)             error = this.lvq.train(io_elements);
               if (this.art != null)             error = this.art.train(io_elements);
               
               if (!error) break; // hata yok ise kır çık
            } catch (InterruptedException ex) {
                Logger.getLogger(ICanvas.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
    }
    public void stop() {
        train = false;
    }
    ICanvas() {
        train = false;

        perceptron = null;
        adaline = null;
        backpropagation = null;
        lvq = null;
        art = null;

    }
}