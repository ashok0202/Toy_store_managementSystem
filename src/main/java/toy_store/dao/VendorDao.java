package toy_store.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Driver;
import toy_store.dto.Vendor;

public class VendorDao {
	
	public Connection createConnection() throws SQLException {
//      Replace these with your actual database connection details
        String url = "jdbc:mysql://localhost:3306/toy_store?createDatabaseIfNotExist=true";
        String user = "root";
        String pass = "Ashok@016";
        return DriverManager.getConnection(url, user, pass);
    }

    public void createTableForVendor() throws SQLException {
        try (Connection con = createConnection();
             Statement s = con.createStatement()) {
             
//         Create the vendor table if it does not exist
            s.execute("CREATE TABLE IF NOT EXISTS vendor (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(45), " +
                    "email VARCHAR(45) UNIQUE, " +
                    "pwd VARCHAR(45), " +
                    "phone BIGINT)");
        } catch (SQLException e) {
            e.printStackTrace();  // Log any SQL exceptions
        }
    }
	
	public int saveVendor(Vendor vendor) throws SQLException {
        String insertSQL = "INSERT INTO vendor (name, email, pwd, phone) VALUES (?, ?, ?, ?)";
        
        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(insertSQL)) {
             
            ps.setString(1, vendor.getName());
            ps.setString(2, vendor.getEmail());
            ps.setString(3, vendor.getPwd());
            ps.setLong(4, vendor.getPhone());
            
//     Execute the insert statement and return the result
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Log any SQL exceptions
            return 0;
        }
    }
	
//  Find vendor by email
    public Vendor findByEmail(String email) throws SQLException {
        Connection con = createConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM vendor WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Vendor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("pwd"),
                    rs.getLong("phone")
            );
        }
        return null; // Return null if vendor not found
    }
    
 // Find vendor by email and password (login)
    public Vendor findByEmailAndPassword(String email, String password) throws SQLException {
        Connection con = createConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM vendor WHERE email = ? AND pwd = ?");
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Vendor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("pwd"),
                    rs.getLong("phone")
            );
        }
        
       // close() the connection  &&  preparedStatement;
        con.close();
        ps.close();
        return null; // Return null if login fails
    
    }
    
	public Vendor fetchProfile(String name) throws SQLException {
		String fetchQuery = "SELECT * FROM vendor WHERE name = ?";

		Connection con = createConnection();
		PreparedStatement ps = con.prepareStatement(fetchQuery);

		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return new Vendor(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("email"), 
					rs.getString("pwd"),
					rs.getLong("phone"));
		}

		return null; // Return null if no result is found or in case of an exception
	}
	
	public boolean updateVendor(String name, String password) throws SQLException {
	    String updateVen = "UPDATE vendor SET pwd = ? WHERE name = ?";
	    
	    // Use try-with-resources to automatically close resources
	    try (Connection con = createConnection();
	         PreparedStatement ps = con.prepareStatement(updateVen)) {
	        
	        // Set the parameters for the prepared statement
	        ps.setString(1, password);
	        ps.setString(2, name);
	        
	        // Execute the update and return true if at least one row was affected
	        int res = ps.executeUpdate();
	        return res > 0;
	        
	    } catch (SQLException e) {
	        e.printStackTrace(); // Handle any SQL exceptions
	        return false; // Return false if there's an error
	    }
	}


    
}
