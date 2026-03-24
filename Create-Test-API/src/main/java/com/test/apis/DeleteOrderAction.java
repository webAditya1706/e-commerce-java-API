package com.test.apis;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.util.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteOrderAction extends ActionSupport {

	private String message;
	private boolean success;

	private Integer order_id;

	public String deleteOrder() {

		String sql = "DELETE FROM orders WHERE order_id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setInt(1, order_id);


			int rows = ps.executeUpdate();

	        if (rows > 0) {
	            message = "Order canceled successfully";
	            success = true;
	        } else {
	            message = "Order not found";
	            success = false;
	        }

		} catch (Exception e) {
			// TODO: handle exception
			message = "Data base connection error";
			success = false;
		}

		return SUCCESS;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	

}
