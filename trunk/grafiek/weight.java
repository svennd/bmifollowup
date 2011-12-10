package grafiek;

import db.dataset;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

public class weight {
    
    public void show_chart(int user_id)
    {
        // all data coming from database
        dataset table_data = new dataset();
        
        // string[size_of_result][7]
        String[][] data = table_data.get_weight(user_id);

        // add all the rows in the JTable
        
        XYSeries series = new XYSeries("verloop gewicht");
        
        for (String[] i : data)
        {
           series.add(Float.parseFloat(i[1]), Float.parseFloat(i[0]));
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("", "dt", "gewicht", xyDataset, PlotOrientation.VERTICAL, true, true, false);
        ChartFrame frame1 = new ChartFrame("Verloop gewicht", chart);
        frame1.setVisible(true);
        frame1.setSize(600, 300);
    }
}
