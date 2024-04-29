package com.project.hospitalmanagement.controllers.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class dataBase {

   public static Connection connectDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital management", "root", "" );
            return connect;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*public Connection connectDB(){

        String databaseName =  "sql11697180";
        String databaseUser = "sql11697180";
        String databasePassword = "BR2jXBAWQf";
        String url = "jdbc:mysql://sql11.freesqldatabase.com:3306/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, databaseUser, databasePassword);
            return connect;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }*/
}



