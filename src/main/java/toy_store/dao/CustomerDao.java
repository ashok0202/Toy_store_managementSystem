package toy_store.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.Driver;
import toy_store.dto.Customer;
import toy_store.dto.Vendor;

public class CustomerDao {

    public Connection createConnection() throws SQLException {
        DriverManager.registerDriver(new Driver()); // Load and register MySQL driver
        String url = "jdbc:mysql://localhost:3306/toy_store?createDatabaseIfNotExist=true";
        String user = "root";
        String pass = "Ashok@016";
        return DriverManager.getConnection(url, user, pass);
    }

    public void createTableForCustomer() throws SQLException {
        try (Connection con = createConnection();
             Statement s = con.createStatement()) {
             
            // Create the customer table
            s.execute("CREATE TABLE IF NOT EXISTS customer ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(45), "
                    + "email VARCHAR(45) UNIQUE, "
                    + "pwd VARCHAR(45), "
                    + "wallet DOUBLE, "
                    + "phone BIGINT)");
        } catch (SQLException e) {
            e.printStackTrace();  // Log any SQL exceptions
        }
    }

    public int saveCustomer(Customer customer) throws SQLException {
        String insertSQL = "INSERT INTO customer (name, email, pwd, wallet, phone) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(insertSQL)) {
             
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPwd());
            ps.setDouble(4, customer.getWallet());
            ps.setLong(5, customer.getPhone());
            
            // Execute the insert statement and return the result
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Log any SQL exceptions
            return 0;
        }
    }
    
 // Find customer by email
    public Customer findByEmail(String email) throws SQLException {
        Connection con = createConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM customer WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("pwd"),
                    rs.getDouble("wallet"),
                    rs.getLong("phone")
            );
        }
        return null; // Return null if customer not found
    }
    

    // Find customer by email and password (login)
    public Customer findByEmailAndPassword(String email, String password) throws SQLException {
        Connection con = createConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM customer WHERE email = ? AND pwd = ?");
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("pwd"),
                    rs.getDouble("wallet"),
                    rs.getLong("phone")
            );
        }
        return null; // Return null if login fails
    }  

	
    public boolean updateCustomer(String name, double wallet, String password) throws SQLException {
        String fetchQuery = "SELECT wallet FROM customer WHERE name = ?";
        String updateQuery = "UPDATE customer SET pwd = ?, wallet = ? WHERE name = ?";

        try (Connection con = createConnection();
             PreparedStatement fetchStmt = con.prepareStatement(fetchQuery);
             PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
            
            fetchStmt.setString(1, name);
            ResultSet rs = fetchStmt.executeQuery();
            
            if (rs.next()) {
                double currentWallet = rs.getDouble("wallet");
                wallet = wallet + currentWallet; 
            } else {
                
                return false;
            }
            updateStmt.setString(1, password);
            updateStmt.setDouble(2, wallet); // Updated wallet balance
            updateStmt.setString(3, name);

            int rowsAffected = updateStmt.executeUpdate();
            return rowsAffected > 0; 

        } catch (SQLException e) {
            e.printStackTrace(); 
            return false; 
        }
    }


	public Customer fetchProfile(String name) throws SQLException {
		// TODO Auto-generated method stub
		String fetchQuery = "SELECT * FROM customer WHERE name = ?";

		Connection con = createConnection();
		PreparedStatement ps = con.prepareStatement(fetchQuery);

		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("pwd"),
                    rs.getDouble("wallet"),
                    rs.getLong("phone")
            );
		}
		return null;
	}
	
	public boolean deductBalance(int customerId, double amount) throws SQLException {
	    try (Connection con = createConnection();
	         PreparedStatement ps = con.prepareStatement("UPDATE customer SET wallet = wallet - ? WHERE id = ? AND wallet >= ?")) {
	        ps.setDouble(1, amount);
	        ps.setInt(2, customerId);
	        ps.setDouble(3, amount); // Ensure sufficient balance

	        int rowsUpdated = ps.executeUpdate();
	        return rowsUpdated > 0; // Return true if balance was deducted
	    }
	}


	 
    
}
