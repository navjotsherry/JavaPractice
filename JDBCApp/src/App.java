import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet result = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "root");

            stmt = conn.createStatement();

            // Delete exisiting users  
            stmt.executeUpdate("delete from emp;");


            // Adding one entry using prepare Statement
            pstmt = conn.prepareStatement("insert into emp values (?, ?, ?)");
            pstmt.setInt(1, 1);
            pstmt.setString(2, "Raj");
            pstmt.setInt(3, 10000);

            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " rows inserted.");

            // Adding 5 more queries using execute update
            stmt.executeUpdate("insert into emp values(2,'Rajesh',10000)");
            stmt.executeUpdate("insert into emp values(3,'Rajesh',12000)");
            stmt.executeUpdate("insert into emp values(4,'Rajesh',14000)");
            stmt.executeUpdate("insert into emp values(5,'Rajesh',15000)");
            stmt.executeUpdate("insert into emp values(6,'Suresh',15000)");
            stmt.executeUpdate("insert into emp values(7,'Sam',15000)");

            //Show all the user data with all 7 records added
            System.out.println("All the users after adding 7 users:");
            result = stmt.executeQuery("select * from emp;");
            while (result.next()) {
                System.out.println("ID: " + result.getInt("id"));
                System.out.println("Name: " + result.getString("name"));
            }

            //Delete id 1
            stmt.executeUpdate("delete from emp where id=1");
            System.out.println("After deleting id 1 user:");
            result = stmt.executeQuery("select * from emp;");
        
            while (result.next()) {
                System.out.println("ID: " + result.getInt("id"));
                System.out.println("Name: " + result.getString("name"));
            }

            //Deleting all the users with name Rajesh
            stmt.executeUpdate("delete from emp where name='Rajesh';");
            System.out.println("After deleting Users named Rajesh:");
            result = stmt.executeQuery("select * from emp;");
        
            while (result.next()) {
                System.out.println("ID: " + result.getInt("id"));
                System.out.println("Name: " + result.getString("name"));
            }

        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
