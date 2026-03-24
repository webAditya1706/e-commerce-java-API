package com.test.apis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.util.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

public class ProductListAction extends ActionSupport {
	private List<Map<String, Object>> products = new ArrayList<>();
	private boolean success;
	private String message;

	public String fetchProductList() {
		String sql = "SELECT * FROM product";
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				Map<String, Object> product = new TreeMap<>();

	            for (int i = 1; i <= columnCount; i++) {
	                product.put(metaData.getColumnLabel(i), rs.getObject(i));
	            }

	            products.add(product);

			}

			if (products.isEmpty()) {
				message = "Product not found";
				success = false;
			} else {
				message = "Product fetched successfuly";
				success = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			message = "database error: " + e.getMessage();
			success = false;
			return ERROR;
		}

		return SUCCESS;
	}

	public List<Map<String, Object>> getProducts() {
		products.sort((p1, p2) -> String.valueOf(p1.get("product_name"))
				.compareToIgnoreCase(String.valueOf(p2.get("product_name")))

		);
		return products;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean isSuccess() {
		return success;
	}
}