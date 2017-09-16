/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pedro Maschio
 */
public class FabricaDeConexao {
    private String url = "jdbc:mysql://localhost/crud";
    private String user = "root";
    private String password = "";
    
    public Connection getConnection(){
        try {
            return DriverManager.getConnection(url , user, password);
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
