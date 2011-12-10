package grafiek;

import db.dataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class bmi {
    
    public void show_chart(int user_id)
    {
                // all data coming from database
        dataset table_data = new dataset();
        
        // string[size_of_result][7]
        String[][] data = table_data.get_bmi(user_id);

        // add all the rows in the JTable
        
        XYSeries series = new XYSeries("verloop BMI");
        
        for (String[] i : data)
        {
           series.add(Float.parseFloat(i[1]), Float.parseFloat(i[0]));
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("", "dt", "bmi", xyDataset, PlotOrientation.VERTICAL, true, true, false);
        ChartFrame frame1 = new ChartFrame("Verloop BMI", chart);
        frame1.setVisible(true);
        frame1.setSize(600, 300);
    }
}
