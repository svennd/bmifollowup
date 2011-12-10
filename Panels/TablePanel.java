package Panels;

import db.dataset;
import howest.dhert.svenn.users;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/*
 *  trace   : AppHandler
 */
public class TablePanel
{
    
    public DefaultTableModel model;
    public JPanel table_list;
    public users current_user;
    public JTable table;
    
    // constructor
    public TablePanel(users current_user, DefaultTableModel model)
    {
        this.current_user   = current_user;
        this.model = model;
    }
    
    /*
     *  name    : show_table_screen
     *  use     : will make a JTable, using DefaultTableModel, made in AppHandler
     *  trace   : AppHandler
     */
    public JPanel show_table_screen()
    {
        // table JPanel
        table_list = new JPanel(new FlowLayout());
        
        // JTable we gonne work in; I prefer non-editable cells since 
        // i won't be listening to any changes in the JTable anyways
        table = new JTable(model){public boolean isCellEditable(int row, int col) {return false;}};
        
        table.getFillsViewportHeight();
        
        // make the table 'scrollable'
        JScrollPane scrollPane = new JScrollPane(table);
        
        // all data coming from database
        dataset table_data = new dataset();
        
        // string[size_of_result][7]
        String[][] data = table_data.get_list(current_user.getId());

        // table collum & width
        table_settings();

        // add all the rows in the JTable
        for (String[] i : data)
        {
            model.addRow(i);
        }

        scrollPane.setPreferredSize(new Dimension(560, 260));
        
        // add the JTable to JPanel
        table_list.add(scrollPane);
        
        return table_list;
    }
    
    /*
     *  name    : table_settings
     *  use     : will set the collumwidth of all tables, inc. the names
     *  trace   : TablePanel->show_table_screen
     */
    private void table_settings() {
        
        // collums
        model.addColumn("#");               model.addColumn("weight");
        model.addColumn("df");              model.addColumn("length");
        model.addColumn("df");              model.addColumn("BMI");
        model.addColumn("dfbmi");           model.addColumn("datum");
        
        // no auto resizing
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        // getting collum "connection"
        TableColumn id          = table.getColumnModel().getColumn(0);        TableColumn c_weight    = table.getColumnModel().getColumn(1);
        TableColumn dfweight    = table.getColumnModel().getColumn(2);        TableColumn c_length    = table.getColumnModel().getColumn(3);
        TableColumn dflength    = table.getColumnModel().getColumn(4);        TableColumn BMI         = table.getColumnModel().getColumn(5);
        TableColumn dfBMI       = table.getColumnModel().getColumn(5);        TableColumn datum       = table.getColumnModel().getColumn(7);

        // setting collumn width
        id.setPreferredWidth(20);               c_weight.setPreferredWidth(75);
        c_length.setPreferredWidth(75);         dfweight.setPreferredWidth(45);
        dflength.setPreferredWidth(45);         BMI.setPreferredWidth(155);
        dfBMI.setPreferredWidth(45);            datum.setPreferredWidth(160);
    }
}
