package com.test.apis;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.util.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

public class AddProductAction extends ActionSupport {
	// ==== response variable
	private String message;
	private boolean success;

	// ==== body variable
	private String product_name;
	private Integer brand_id;
	private Integer category_id;
	private Integer quantity;
	private Integer list_price;

	public String createProduct() {

		String sql = "INSERT INTO product " + "(product_name, brand_id,category_id, list_price, quantity)"
				+ "VALUES(?,?,?,?,? )";

		try (Connection con = DBConnection.getConnection(); 
			PreparedStatement ps = con.prepareStatement(sql);) {
			
			ps.setString(1, product_name);
			ps.setInt(2, brand_id);
			ps.setInt(3, category_id);
			ps.setInt(4, quantity);
			ps.setInt(5, list_price);
			
			ps.executeUpdate();
			message = "Producet inserted";
			success = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			message = "Db connection Error " + e.getMessage(); 
			success = false;
		}
		
		return SUCCESS;
	}
	
	public String getMessage () {
		return message;
	}
	
	public boolean getSuccess () {
		return success;
	}
	
	
	public void setProductName(String product_name) {
		this.product_name = product_name; 
	}
	
	public void setBrandName(Integer brand_id) {
		this.brand_id = brand_id;
	}
	
	public void setCategoryId(Integer category_id){
		this.category_id = category_id;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setListPrice(Integer list_price) {
		this.list_price = list_price;
	}
	
}
