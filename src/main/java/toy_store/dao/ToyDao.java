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
import toy_store.dto.Toys;

public class ToyDao {
    // step-1: Create connection
    public Connection createConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        String url = "jdbc:mysql://localhost:3306/toy_store?createDatabaseIfNotExist=true";  // Fix the typo here
        String user = "root";
        String pass = "Ashok@016";

        return DriverManager.getConnection(url, user, pass);
    }

    // step-2: Create table for toy
    public void CreateTableForToy() throws SQLException {
        try (Connection con = createConnection();
             Statement s = con.createStatement()) {

            // Ensure table creation SQL is correct
            s.execute("CREATE TABLE IF NOT EXISTS toy ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "brand VARCHAR(45), "
                    + "cost DOUBLE, "
                    + "colour VARCHAR(45), "
                    + "quantity INT, "
                    + "type VARCHAR(45))");  // Add the missing closing parenthesis here
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // step-3: Save the toy
    public int saveToy(Toys toy) throws SQLException {
        String insertSql = "INSERT INTO toy(brand, cost, colour, quantity, type) VALUES(?, ?, ?, ?, ?)";
        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(insertSql)) {

            ps.setString(1, toy.getBrand());
            ps.setDouble(2, toy.getCost());
            ps.setString(3, toy.getColour());
            ps.setInt(4, toy.getQuantity());
            ps.setString(5, toy.getType());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public boolean updateToy(double cost,int quantity,int id) {
    	String updateQuery="UPDATE toy SET cost=?, quantity=? WHERE id = ?";
    	
    	try(Connection con=createConnection();
    		PreparedStatement ps=con.prepareStatement(updateQuery)) {
    		
    		ps.setDouble(1, cost);
    		ps.setInt(2, quantity);
    		ps.setInt(3, id);
    		
    		 int rowsAffected = ps.executeUpdate(); // Execute the update statement
 	        
 	        return rowsAffected > 0; // Return true if the update affected at least one row, false otherwise
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	
    }
    public boolean deleteToy(String brand) {
    	String deleteQuery="delete form toy where brand=?";
    	try(Connection con=createConnection();
    	    PreparedStatement ps=con.prepareStatement(deleteQuery)) {
    		ps.setString(1,brand);
    		
    		int resDelete=ps.executeUpdate();
    		
    		return resDelete >0;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	
    }
    public List<Toys> getToyAll() throws SQLException{
		String queary="SELECT * FROM toy";
		List<Toys> toysList=new ArrayList<>();
		try(Connection con=createConnection();
			PreparedStatement ps=con.prepareStatement(queary);
			ResultSet rs=ps.executeQuery()) {

			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("brand");
				double address=rs.getDouble("cost");
				String color=rs.getString("colour");
				int quantity=rs.getInt("quantity");
				String type=rs.getString("type");
				
				Toys toy=new Toys(id, name, address, color, quantity, type);
				toysList.add(toy);	
			}
			return toysList;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
