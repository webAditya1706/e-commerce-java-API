package com.test.apis;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.util.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

public class OrderProductAction extends ActionSupport {
	
	private boolean success;
	private String message;
	
	private int product_id;
	private int order_quantity;
	private int customer_id;
	
	public String addOrder () {
		String sql = "INSERT INTO orders" + "(product_id, order_quantity, customer_id) " +
					"VALUES (?,?,?)";
		
		
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
						) {

			ps.setInt(1, product_id);
			ps.setInt(2, order_quantity);
			ps.setInt(3, customer_id);
			
			ps.executeUpdate();
			message = "Order created";
			success = true;
			
		} catch (Exception e) {
			message = "Database connection Error " + e.getMessage();
			success = false;
			return SUCCESS;
			// TODO: handle exception
		}
		
		
		return SUCCESS;
	}

	
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	
	public void setOrder_quantity(Integer order_quantity) {
		this.order_quantity = order_quantity;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	
	
	public String getMessage() {
		return message;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	
	
}
