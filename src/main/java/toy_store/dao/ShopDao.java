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

import toy_store.dto.Shop;

public class ShopDao {
	
//	step-1
	public Connection createConnection() throws SQLException{
//		load and register
	    DriverManager.registerDriver(new Driver());
	    String url = "jdbc:mysql://localhost:3306/toy_store?createDatabaseIfNotExist=true";  // Fix the typo here
        String user = "root";
        String pass = "Ashok@016";
        
        return DriverManager.getConnection(url, user, pass);
	}
	
//	step -2;
	public void createTableForShop() throws SQLException{
		
		//try-with-resource()
		try(Connection con=createConnection();
		    Statement s=con.createStatement()) {
			
			//create the shop table;
			
			s.execute("CREATE TABLE IF NOT EXISTS shop ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "name varchar(45), "
                    + "address varchar(45),"
                    + "venemail varchar(45))");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	step-3;
	public int saveShop(Shop shop)throws SQLException{
		//SQl Query;
		String insertSql="INSERT INTO shop(name,address,venemail) values(?,?,?)";
		try(Connection con=createConnection();
		    PreparedStatement ps=con.prepareStatement(insertSql)) {
			
			ps.setString(1, shop.getName());
			ps.setString(2,shop.getAddress());
			ps.setString(3, shop.getVenderEmail());
			
			return ps.executeUpdate();
			
		} catch (Exception e) {
		
			e.printStackTrace();
			
			return 0;
		}
	}
	// Delete customer by ID
	public boolean deleteById(int id) throws SQLException {
	    String deleteSQL = "DELETE FROM shop WHERE id = ?";
	    
	    try (Connection con = createConnection();
	         PreparedStatement ps = con.prepareStatement(deleteSQL)) {
	     
	        ps.setInt(1, id);  // Set the customer id to delete
	   
	        int rowsAffected = ps.executeUpdate(); // Execute delete statement
	        
	        return rowsAffected > 0; // If rowsAffected is greater than 0, the customer was deleted
	        
	    } catch (SQLException e) {
	        e.printStackTrace();  // Log any SQL exceptions
	        return false;  // Return false if the deletion fails
	    }
	}
	// Update shop by ID
	public boolean updateById(String name, String address, int id) throws SQLException {
	    String updateSQL = "UPDATE shop SET name=?, address=? WHERE id = ?";

	    try (Connection con = createConnection();
	         PreparedStatement ps = con.prepareStatement(updateSQL)) {
	        
	        ps.setString(1, name);
	        ps.setString(2, address);
	        ps.setInt(3, id);

	        int rowsAffected = ps.executeUpdate(); // Execute the update statement
	        
	        return rowsAffected > 0; // Return true if the update affected at least one row, false otherwise
	        
	    } catch (SQLException e) {
	        e.printStackTrace();  // Log any SQL exceptions
	        return false;  // Return false if the update fails
	    }
	}
	
	public List<Shop> getshopAll() throws SQLException{
		String queary="SELECT * FROM shop";
		List<Shop> shopList=new ArrayList<>();
		try(Connection con=createConnection();
			PreparedStatement ps=con.prepareStatement(queary);
			ResultSet rs=ps.executeQuery()) {
	
			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String address=rs.getString("address");
				String venEmail=rs.getString("venemail");
				Shop shop=new Shop(id, name, address, venEmail);
				shopList.add(shop);
			}
			return shopList;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



}
