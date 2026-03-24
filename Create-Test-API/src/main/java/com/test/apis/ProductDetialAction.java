package com.test.apis;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.util.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetialAction extends ActionSupport {
	private String message;
	private boolean success;

	private Integer product_id;
	Map<String, Object> product = new TreeMap<>();

	public String getProductDetail() {

		String sql = "{CALL sp_product_by_id(?)}";

		try (Connection con = DBConnection.getConnection(); CallableStatement cs = con.prepareCall(sql);) {
			cs.setInt(1, product_id);

			try (ResultSet rs = cs.executeQuery();) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				if (rs.next()) {
					for (int i = 1; i <= columnCount; i++) {
						product.put(metaData.getColumnLabel(i), rs.getObject(i));
					}

				} else {
					message = "product not found";
					success = false;
					return SUCCESS;
				}
				message = "Product found";
				success = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			message = "Product not found";
			success = false;
		}

		return SUCCESS;
	}

	public Map<String, Object> getProduct() {
		/* product.sort((p1, p2) -> String.valueOf(p1.get("product_name"))); */

		return product;
	}

	public String getMessage() {
		return message;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

}
