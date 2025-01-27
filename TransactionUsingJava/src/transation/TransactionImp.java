package transation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.out;

public class TransactionImp {

    public static void main(String[] args) throws Exception {//ClassNotFoundException {
        out.println("hello");

        String url = "jdbc:mysql://localhost:3306/screening_assignment";
        String username = "root";
        String password = "root";
        
        Scanner sc = new Scanner(System.in);
        out.print(" Enter the sender account number : ");
        String senderAccountNumber = sc.nextLine(); //"account789";
        out.println();
        
        out.print(" Enter the receiver account number : ");
        String receiverAccountNumber = sc.nextLine(); //"account456";
        out.println();
        
        out.print(" Enter transfer amount : ");
        double enterAmount = sc.nextDouble();
        
        
        String withdrawQuery = "update transaction set balance = balance - ? where account_number = ?";
        String depositQuery = "update transaction set balance = balance + ? where account_number = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            out.println(" Driver loaded successfully !!!");
        }
        catch (ClassNotFoundException e) {
            out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            out.println("Connection Established Successfully !!!");
            out.println();
            out.println();
            out.println();
            con.setAutoCommit(false);

            try {
            	
            	String checkBalanceQuery = "Select balance from transaction where account_number = ?";
            	PreparedStatement balanceStatement = con.prepareStatement(checkBalanceQuery);
            	balanceStatement.setString(1, senderAccountNumber);
            	
            	ResultSet rs = balanceStatement.executeQuery();
            	if(rs.next()) {
            		double total_amount = rs.getDouble("Balance");
            		
            		if(total_amount >= enterAmount) {
            			PreparedStatement withStatement = con.prepareStatement(withdrawQuery);
                        PreparedStatement depositStatement = con.prepareStatement(depositQuery);
                        withStatement.setDouble(1, enterAmount);
                        withStatement.setString(2, senderAccountNumber);
                        depositStatement.setDouble(1, enterAmount);
                        depositStatement.setString(2, receiverAccountNumber);
                        withStatement.executeUpdate();
                        depositStatement.executeUpdate();

                        con.commit();
                        out.println(" Transaction Successful");
            		}
            		else {
            			out.println(" Insufficient Balance !!!");
            		}
            	}
            	
                
            }
            catch (SQLException e) {
                con.rollback();
                out.println(" Transaction Failed !!!");
            }
        }

        catch (SQLException e) {
            out.println(e.getMessage());
        }
    }
}

//.
