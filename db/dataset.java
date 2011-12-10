package db;

import Resource.FloatHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class dataset extends FloatHelper
{

    protected JTable table;
        
    /*
     *  name    : add_entry
     *  use     : 
     */
    public String[] add_entry(String weight, String length, int user_id, DefaultTableModel model) {
        
        float vlength = 0;
        float vweight = 0;
        String data[] = new String[8];
        SimpleDateFormat formatter = new SimpleDateFormat("EEE. d MMM yy");
        Connection connection = connector.GetConnection();
        
        // get length or prev. value
        try
        {
            vlength = Float.parseFloat(length);
        }
        catch(Exception ex)
        {
            if ("empty String".equals(ex.getMessage()))
            {
                vlength = (model.getRowCount() != 0) ? get_last_length(user_id): 0;
            }
        }
        
        // get weight or prev. value
        try
        {
            vweight = Float.parseFloat(weight);
        }
        catch(Exception ex)
        {
            if ("empty String".equals(ex.getMessage()))
            {
                vweight = (model.getRowCount() != 0) ? get_last_weight(user_id): 0;
            }
        }
                
        // prev values
        if (model.getColumnCount() != 0)
        {
                Float data_2 = (round2(vweight - get_last_weight(user_id)));
            data[2] = (data_2 >= 0) ? " +" + Float.toString(data_2) : " " + Float.toString(data_2);
            
                Float data_4 = (round2(vlength - get_last_length(user_id)));
            data[4] = (data_4 >= 0) ? " +" + Float.toString(data_4) : " " +Float.toString(data_4);

            data[6] = Float.toString(round2((vweight/(vlength*vlength)) - (get_last_weight(user_id)/(get_last_length(user_id)*get_last_length(user_id)))));
        }
        
        // # nummer
        data[0] = Integer.toString(model.getRowCount() + 1 );
        // gewicht
        data[1] = Float.toString(vweight);
        // lengte
        data[3] = Float.toString(vlength);
        // bmi
        data[5] = Float.toString(round2(vweight/(vlength*vlength)));
        // format
        data[7] = formatter.format(System.currentTimeMillis());
        
        try 
        {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate("INSERT INTO dataset(weight, length, user_id, entry) VALUES('" + vweight + "','" + vlength + "','" + user_id + "', '" + System.currentTimeMillis() + "');");

            return data;
        } 
        catch (Exception ex) 
        {
            System.out.println("add_entry" + ex.getMessage());
        }
        finally 
        {
            try {connection.close();} catch (Exception ex) {}
        }

        return new String[0];
    }

    public float get_last_weight(int user_id) {

        Connection connection = connector.GetConnection();

        try {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT weight FROM dataset where user_id = " + user_id + " ORDER BY id DESC LIMIT 1;");

            if(rs.next())
                return rs.getFloat("weight");
            else
                return (float) 0;

        } catch (Exception ex) {
            System.out.println("get_last_Weight" + ex.getMessage());
            return 0;
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }

    public float get_last_bmi(int user_id)
    {
        float last_weight = get_last_weight(user_id);
        float last_length = get_last_length(user_id);
        
        return (last_weight/(last_length*last_length));
    }
    
    public int[] get_id_list(int user_id)
    {
        
        Connection connection = connector.GetConnection();
        
        try {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT id FROM dataset where user_id = " + user_id + ";");
            
            // array size bepalen
             rs.last();
            int array_size = rs.getRow();
            rs.beforeFirst();
            
            // array aanmaken
            int[] list = new int[array_size];
                    
            int i = 0; 
            while (rs.next()) {
                 list[i] = rs.getInt("id");
                 i++;
             }

            return list;
        }
        catch(SQLException x){System.out.println(x.getMessage()); return new int[0];}
        finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
    
    public String[] get_statics(int user_id)
    {
        String[] statics = new String[7]; 
         Connection connection = connector.GetConnection();
        
        try {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT "
                    + "max(weight) as max_weigth, "
                    + "min(weight) as min_weight, "
                    + "min(length) as min_length, "
                    + "max(length) as max_length, "
                    + "min(entry) as first_entry, "
                    + "max(entry) as last_entry, "
                    + "count( id ) as total_amount "
                    + "FROM dataset WHERE user_id = " + user_id + ";");
            
            rs.next();
            statics[0] = Float.toString(rs.getFloat("max_weigth"));
            statics[1] = Float.toString(rs.getFloat("min_weight"));
            statics[2] = Float.toString(rs.getFloat("max_length"));
            statics[3] = Float.toString(rs.getFloat("min_length"));
            statics[4] = Long.toString(rs.getLong("first_entry"));
            statics[5] = Long.toString(rs.getLong("last_entry"));
            statics[6] = Integer.toString(rs.getInt("total_amount"));
            
        }catch(SQLException x){System.out.println("get_statics"+x.getMessage());}
        return statics;
    }
    
    public void remove_id(int row_id)
    {
        Connection connection = connector.GetConnection();

        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DELETE FROM dataset WHERE id = " + row_id + ";");

        } catch (Exception x) {
        }
    }
    
    public void edit_id(float weight, float length, int row_id)
    {
       Connection connection = connector.GetConnection();

        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("UPDATE dataset SET weight = '" + weight + "', length = '" + length + "' WHERE id ='" + row_id + "';");

        } catch (Exception x) {
        }
    }
    
    public float[] get_values_for (int id){
        Connection connection = connector.GetConnection();

        try {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT weight, length FROM dataset where id = " + id + ";");

            rs.next();
            float[] x = new float[2];
                x[0] = rs.getFloat("weight");
                x[1] = rs.getFloat("length");
            return x;
             

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new float[0];
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
    
    public float get_last_length(int user_id) {

        Connection connection = connector.GetConnection();

        try {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT length FROM dataset where user_id = " + user_id + " ORDER BY id DESC LIMIT 1;");

            if(rs.next())
                return rs.getFloat("length");
            else
                return (float) 0;

        } catch (Exception ex) {
            System.out.println("get_last_length" + ex.getMessage());
            return 0;
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
    
    public String[][] get_list(int user_id) {
        Connection connection = connector.GetConnection();

        // datum formaat
        SimpleDateFormat formatter = new SimpleDateFormat("EEE. d MMM yy");
        
        try {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT * FROM dataset WHERE user_id = '" + user_id + "';");


            // niet zo nette manier om de grootte van de resultset te bepalen
            rs.last();
            int array_size = rs.getRow();
            // rs.first(); <-- geeft fout omdat rs.next() dan naar resultaat 2 springt
            rs.beforeFirst();

           String[][] data = new String[array_size][8]; 
           
            int t = 0, id = 0;
            float prev_weight = 0;
            float prev_length = 0;
            float bmi, prev_bmi = 0;
            while (rs.next()) {
                
                      // artificele "id"
                    data[t][0] = Integer.toString(++id);
               
                    // gewicht
                    float c_weight = rs.getFloat("weight");
                    float c_length = rs.getFloat("length");
                     data[t][1] = Float.toString(c_weight);
                     data[t][3] = Float.toString(c_length);
                    // alles na het eerste punt kunnen we verschillen berekenen
                    if (t >= 1) {
                         
                        // dunner geworden
                        data[t][2] = (c_weight - prev_weight < 0)
                                ? (" " + round2(c_weight - prev_weight))
                                : (" +" + round2(c_weight - prev_weight));

                        // gegroeit
                        data[t][4] = (c_length - prev_length < 0)
                                ? (" " + round2(c_length - prev_length))
                                : (" +" + round2(c_length - prev_length));
                    } else {
                        // gewicht
                        data[t][1] = Float.toString(rs.getFloat("weight"));
                        data[t][2] = "";

                        // lengte
                        data[t][3] = Float.toString(rs.getFloat("length"));
                        data[t][4] = "";
                    }
                       
                    bmi = (round2((rs.getFloat("weight") / (rs.getFloat("length") * rs.getFloat("length")))));
                    data[t][5] = Float.toString(bmi);
                    data[t][6] = Float.toString(round2(bmi-prev_bmi));
                    
                   
                    data[t][7] = (formatter.format(rs.getLong("entry")));

                    prev_weight = rs.getFloat("weight");
                    prev_length = rs.getFloat("length");
                    prev_bmi = bmi;
                    // volgende punt
                    t++;                
            }

            return data;


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new String[0][0];
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
    
    public String[][] get_bmi(int user_id) {
        Connection connection = connector.GetConnection();

        // datum formaat
        SimpleDateFormat formatter = new SimpleDateFormat("EEE. d MMM yy");
        
        try {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT * FROM dataset WHERE user_id = '" + user_id + "';");


          // niet zo nette manier om de grootte van de resultset te bepalen
          rs.last();
            int array_size = rs.getRow() - 1;
          rs.beforeFirst();
          
           String[][] data = new String[array_size+1][2]; 
           
            int t = 0;

            float bmi = 0, prev_bmi = 0;
            while (rs.next()) {
                    // gewicht
                    float c_weight = rs.getFloat("weight");
                    float c_length = rs.getFloat("length");
                        bmi = (round2((c_weight / (c_length * c_length))));
                    
                    data[t][0] = Float.toString(bmi);                   
                    data[t][1] = Integer.toString(t);

                    prev_bmi = bmi;
                    // volgende punt
                    t++;                
            }

            return data;


        } catch (Exception ex) {
            System.out.println("get_bmi"+ex.getMessage());
            return new String[0][0];
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
    
    public String[][] get_weight(int user_id) {
        Connection connection = connector.GetConnection();

        // datum formaat
        SimpleDateFormat formatter = new SimpleDateFormat("EEE. d MMM yy");
        
        try {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT * FROM dataset WHERE user_id = '" + user_id + "';");


          // niet zo nette manier om de grootte van de resultset te bepalen
          rs.last();
            int array_size = rs.getRow() - 1;
          rs.beforeFirst();
          
           String[][] data = new String[array_size+1][2]; 
           
            int t = 0;

            while (rs.next()) {
                
                    data[t][0] = Float.toString(rs.getFloat("weight"));                   
                    data[t][1] = Integer.toString(t);

                    // volgende punt
                    t++;                
            }

            return data;


        } catch (Exception ex) {
            System.out.println("getweight"+ex.getMessage());
            return new String[0][0];
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
}
