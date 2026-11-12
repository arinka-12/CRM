/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author 4rink
 */
public class koneksidatabase {
    public static Connection con;
    public static Statement stm;
    
    void koneksi22(){   
        try{
            String url = "jdbc:mysql://localhost:3306/monitoringpesanan";
            String user = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            stm = con.createStatement();
            System.out.println("Koneksi berhasil!");
        }catch (Exception e) {
            System.out.println("Koneksi gagal!");
            e.getMessage();
    
        }
    }
    
    
}
