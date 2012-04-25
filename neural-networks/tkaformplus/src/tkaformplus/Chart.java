/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tkaformplus;

import java.util.List;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author gdemir
 */
public class Chart {
 
    /**
     * @param args the command line arguments
     */
    
    public static JFreeChart plot(List<Double> errors) {
    
        XYSeries series = new XYSeries("XYGraph");
        if (errors != null)
            for (int i = 0; i < errors.size(); i++)
                series.add(i+1, errors.get(i));
        else
            series.add(0, 0);
        // Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        // Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart(
            "XY Error Graph", // Title
            "x", // x-axis Label
            "y", // y-axis Label
            dataset, // Dataset
            PlotOrientation.VERTICAL, // Plot Orientation
            true, // Show Legend
            true, // Use tooltips
            false // Configure chart to generate URLs?
        );
        /*
        try {
            
            ChartUtilities.saveChartAsJPEG(new File(name+"chart.jpg"), chart, 600, 600);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }*/
        return chart;
    }
}