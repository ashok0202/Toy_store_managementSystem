package toy_store.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class PaymentDao {
    
    // Step-1: Create connection
    public Connection createConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        String url = "jdbc:mysql://localhost:3306/toy_store?createDatabaseIfNotExist=true"; 
        String user = "root";
        String pass = "Ashok@016";

        return DriverManager.getConnection(url, user, pass);
    }
    
    public void createTablePayment() throws SQLException {
        try (Connection con = createConnection();
             Statement s = con.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS payment ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "bill VARCHAR(45), "
                    + "customerId INT, "
                    + "FOREIGN KEY (customerId) REFERENCES customer(id))"); 
            
            System.out.println("Payment table created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to add a payment record
    public boolean addPaymentRecord(String bill, int customerId) throws SQLException {
        String sql = "INSERT INTO payment (bill, customerId) VALUES (?, ?)";
        try (Connection con = createConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
             
            pstmt.setString(1, bill);
            pstmt.setInt(2, customerId);

            int res = pstmt.executeUpdate();
            return res>0;
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
}
