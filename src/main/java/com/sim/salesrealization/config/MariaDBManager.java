package com.sim.salesrealization.config;

import com.sim.salesrealization.model.DropDownEnum;
import com.sim.salesrealization.model.OneBMSInput;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/****************************************************************************
 * This Class creates and manages connections to MariaDB with an
 * order_summary table. Its run() method prints the summary of the orders table at
 * periodic intervals. It also supports an insert function to insert
 * new records to the table for testing purposes
 ***************************************************************************
 **/

public class MariaDBManager implements  Serializable {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private Connection conn;

    public static void main(String[] args) {

        System.out.println("Starting MariaDB DB Manager");
        MariaDBManager sqldbm = new MariaDBManager();
        sqldbm.setUp();
        //sqldbm.insertSummary();
       // sqldbm.getDropDownValues("");
       // sqldbm.insertAll();
        //sqldbm.getAllrecords();
        //sqldbm.run();
    }

    private void insertAll() {
        String filename="/home/allianz/Documents/insert";
        Path path = Paths.get(filename);

        try (Stream<String> stream = Files.lines(path)) {
            List<String> sqlList=stream.collect(Collectors.toList());
            System.out.println(sqlList.size());
            for(String sql:sqlList){
                System.out.println(sql);
                conn.createStatement().execute(sql);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {

        }
    }

    private void insertSummary() {
        try{

            String sql ="INSERT INTO oneBMS VALUES ( '1', 'First Fit','KHHHD3B9','SCDPSCD BJWVP VSJE ZXLRXZ',2,5.0);";
            System.out.println(sql);
            conn.createStatement().execute(sql);

            String sql1 ="INSERT INTO oneBMS VALUES ( '2', 'First Fit','VB49U9U','SCDPSCD BJWVP VSJE ZXLRXZ',4,10.0);";
            System.out.println(sql1);
            conn.createStatement().execute(sql1);

            String sql3 ="INSERT INTO oneBMS VALUES ( '3', 'First Fit','VB49U9U','ABC ZXLRXZ',3,20.0);";
            System.out.println(sql3);
            conn.createStatement().execute(sql3);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp() {
        System.out.println("Setting up MariaDB Connection");
        String url = "jdbc:mysql://localhost:3306/streaming";
        try {
            //conn = DriverManager.getConnection(url,"streaming","streaming");
            conn = DriverManager.getConnection(url,"root","password");
          //  insertSummary();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void teardown() {
        try {
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }



    public List<OneBMSInput> getAllrecords(){
        List<OneBMSInput> allRecords = new ArrayList<>();
        String selectSql = "SELECT *  FROM oneBMS";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(selectSql);

            OneBMSInput record =null;
        while (rs.next()) {
            record = new OneBMSInput();
            record.setId(rs.getLong("ID"));
            record.setEngineType(rs.getString("EngineType"));
            record.setCustomerSpecification(rs.getString("CustomerSpecification"));
            record.setCustomerName(rs.getString("CustomerName"));
            record.setNoOfEngines(rs.getInt("NoofEngines"));
            record.setdBUNetPrice(rs.getDouble("DBUNetPrice"));

            allRecords.add(record);

            /*System.out.println(ANSI_BLUE
                    +"---------------------------------------------------------------\n "
                    +"DB Summary : "
                    + "Records = " + rs.getInt("TotalRecords") + ", "
                    + "Value = " + rs.getDouble("TotalValue") + "\n"
                    +"---------------------------------------------------------------"
                    + ANSI_RESET);*/
        }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allRecords;
    }

    public List<String> getDropDownValues(String query, DropDownEnum current_dropdown){
        List<String> allDropDown = new ArrayList<>();
       // String queryTest= "SELECT DISTINCT CustomerName FROM oneBMS";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
               //allDropDown.add(rs.getString("CustomerName"));
                allDropDown.add(rs.getString(current_dropdown.value()));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allDropDown;
    }
}
