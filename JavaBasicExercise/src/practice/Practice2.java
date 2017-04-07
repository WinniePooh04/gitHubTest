package practice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Practice2 {

	public static void main(String[] args) throws SQLException,ClassNotFoundException{
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			// mysql connect
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exercise", "root", "");

			stmt = conn.createStatement();
			 // 自動コミットをオフに設定
            conn.setAutoCommit(false);
			rst = stmt.executeQuery("SELECT staff_cd,part_cd,customer_cd,customer_nm,SUM(quantity) AS total_qty,create_dt FROM t_order GROUP BY staff_cd,part_cd,customer_cd ");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// get current date time with Date()
			Date date = new Date();
			while(rst.next()){
				String sql = "INSERT into t_order_sum (staff_cd, part_cd, customer_cd, customer_nm, quantity, create_dt, update_dt) VALUES" + "(?,?,?,?,?,?,?)";
				java.sql.PreparedStatement insertStr = conn.prepareStatement(sql);
				insertStr.setString(1,rst.getString("staff_cd"));
				insertStr.setString(2, rst.getString("part_cd"));
				insertStr.setString(3,rst.getString("customer_cd"));
				insertStr.setString(4,rst.getString("customer_nm"));
				insertStr.setString(5, rst.getString("total_qty"));
				insertStr.setString(6, rst.getString("create_dt"));
				insertStr.setString(7, dateFormat.format(date));
				insertStr.executeUpdate();
				System.out.println("Insert data successfully");
				// トランザクションをコミット
	            conn.commit();
	            System.out.println("コミットしました...");
			}
		}catch(SQLException s){
			// Handle errors for JDBC
			s.printStackTrace();
			try{
				// トランザクションをロールバック
	            conn.rollback();
	            System.out.println("The data is roll back...");
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}catch (ClassNotFoundException e) {
			// Handle errors for Class.forName
			e.getMessage();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}
}