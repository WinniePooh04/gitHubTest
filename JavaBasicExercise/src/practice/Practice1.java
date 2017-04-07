package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.PreparedStatement;

public class Practice1 {
	private static List<M_customer> CustomerList = new ArrayList<M_customer>();
	
	private static M_customer m_customer;

	// need to access this in the Practice1 class
	public List<M_customer> getPerList() {
		return CustomerList;
	}

	public static void main(String[] args) throws SQLException,ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst = null;
		ResultSet rstUpdate = null;
		String cusInfo = m_customer.getCusInfo();
		System.out.println(cusInfo);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// mysql connect
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/exercise", "root", "");

			stmt = conn.createStatement();
			// select statement
			try {
				rst = stmt.executeQuery("SELECT customer_cd,customer_nm FROM m_customer");

				while (rst.next()) {
					M_customer customerCD = new M_customer();
					customerCD.setCustomer_cd(rst.getString("customer_cd"));
					customerCD.setCustomer_name(rst.getString("customer_nm"));
					CustomerList.add(customerCD);
				}
			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
			} finally {
				if (rst != null) {
					rst.close();
				}
			}
			// update query
			try {
				 // �����R�~�b�g���I�t�ɐݒ�
	            conn.setAutoCommit(false);
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				// get current date time with Date()
				Date date = new Date();
				PreparedStatement Update = (PreparedStatement) conn
						.prepareStatement("UPDATE t_order SET customer_nm =?,update_dt=? WHERE customer_cd=?");
				for (M_customer accountInfo : CustomerList) {
					Update.setString(1, accountInfo.getCustomer_name());
					Update.setString(2, dateFormat.format(date));
					Update.setString(3, accountInfo.getCustomer_cd());
					Update.executeUpdate();
					System.out.println("Update data successfully...");
					// �g�����U�N�V�������R�~�b�g
		            conn.commit();
		            System.out.println("�R�~�b�g���܂���...");
				}
			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
				try{
					// �g�����U�N�V���������[���o�b�N
		            conn.rollback();
		            System.out.println("The data is roll back...");
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			} catch (Exception e) {
				// Handle errors for JDBC
				e.printStackTrace();
			} finally {
				if (rstUpdate != null) {
					rstUpdate.close();
				}
			}

		} catch (SQLException se) {
			System.out.println("SQLException���������܂���...");
            // �G���[�E���b�Z�[�W���o��
            System.out.print(se.getMessage());
            se.printStackTrace();
		} catch (ClassNotFoundException  e) {
			// Handle errors for Class.forName
			e.printStackTrace();
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