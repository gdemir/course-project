/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author gdemir
 */
public class Files {

    /**
     * @param args the command line arguments
     */

    public static double[][] read(String filename, int column) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(new File(filename));
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader in = new BufferedReader(isr);

        double[][] x = new double[100][column];
        String line;
        String[] temp;
        int i, j;
        for (i = 0; (line = in.readLine()) != null; i++) {
            temp = line.split(",");
            for (j = 0; j <= column-1; j++)
                x[i][j] = Integer.parseInt(temp[j]);
        }

        in.close();
        // resize
        double[][] new_x = new double[i][3];
        for (i = 0; i < new_x.length; i++)
            for (j = 0; j <= column-1; j++)
                new_x[i][j] = x[i][j];

        return new_x;
    }
}
