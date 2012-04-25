/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdemir
 */
public class Matrix {

    /**
     * @param args the command line arguments
     */

    public static List<List> fileread(String filename, int column) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(new File(filename));
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader in = new BufferedReader(isr);

        List<List> io_elements = new ArrayList<List>();
        List<List> input_elements = new ArrayList<List>();
        List<Double> output_elements = new ArrayList<Double>();
        
        String line;
        String[] temp;
        int i, j;
        for (i = 0; (line = in.readLine()) != null; i++) {
            temp = line.split(",");
            List vector = new ArrayList<Double>();
            for (j = 0; j < column; j++)
                vector.add(Double.parseDouble(temp[j]));
            input_elements.add(vector);
            if (temp.length > j)
                output_elements.add(Double.parseDouble(temp[j]));
        }
        
        io_elements.add(input_elements);
        io_elements.add(output_elements);

        System.out.println("file reading...(" + filename + ")");
        System.out.println("inputs: " + input_elements);
        System.out.println("outputs: " + output_elements);
        in.close();

        return io_elements;
    }
    public static List<List> textread(String text, int column) throws UnsupportedEncodingException, FileNotFoundException, IOException {

        List<List> io_elements = new ArrayList<List>();
        List<List> input_elements = new ArrayList<List>();
        List<Double> output_elements = new ArrayList<Double>();
        
        String line;
        String[] lines = text.split("\n");
        String[] temp;
        int i, j;
        for (i = 0; i < lines.length; i++) {
            if ("".equals(lines[i]))
                continue;
            temp = lines[i].split(",");
            List vector = new ArrayList<Double>();
            for (j = 0; j < column; j++)
                vector.add(Double.parseDouble(temp[j]));
            input_elements.add(vector);
            if (temp.length > j)
                output_elements.add(Double.parseDouble(temp[j]));
        }
        
        io_elements.add(input_elements);
        io_elements.add(output_elements);

        System.out.println("text reading...");
        System.out.println("inputs: " + input_elements);
        System.out.println("outputs: " + output_elements);
        return io_elements;
    }
    public static void main(String[] args) {
        try {
            List<List> list = Matrix.fileread("test.txt", 2);
            System.out.println(list.get(0));
            System.out.println(list.get(1));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
