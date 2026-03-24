package com.dao.layer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.sql.Statement;

import com.action.layer.OrderItemDTO;
import com.example.util.DBConnection;

public class ProductDao {

	// ✅ GET PRODUCT LIST
	public List<Map<String, Object>> getProductList() throws Exception {
		String sql = "{CALL sp_get_product_list}";

		List<Map<String, Object>> products = new ArrayList<>();

		try (Connection con = DBConnection.getConnection();
				CallableStatement cs = con.prepareCall(sql);
				ResultSet rs = cs.executeQuery();) {

			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();

			while (rs.next()) {
				Map<String, Object> rows = new TreeMap<>();
				for (int i = 1; i <= colCount; i++) {
					rows.put(meta.getColumnName(i), rs.getObject(i));
				}
				// System.out.println(rows);
				products.add(rows);
				// System.out.println(products);
			}

			return products;

		}
	}

	// create a product
	public boolean createProduct(String productName, Integer brandId, Integer categoryId, Integer listPrice,
			Integer quantity) throws Exception {
		String sql = "{CALL sp_create_product(?,?,?,?,?)}";

		int rows;

		try (Connection con = DBConnection.getConnection(); CallableStatement cs = con.prepareCall(sql);) {
			cs.setString(1, productName);
			cs.setInt(2, brandId);
			cs.setInt(3, categoryId);
			cs.setInt(4, listPrice);
			cs.setInt(5, quantity);

			rows = cs.executeUpdate();
		}
		return rows > 0;

	}

	// ✅ DELETE PRODUCT USING STORED PROCEDURE
	public boolean deleteProduct(int productId) throws Exception {

		String sql = "{CALL sp_delete_product(?)}";
		int rows;

		try (Connection con = DBConnection.getConnection(); CallableStatement cs = con.prepareCall(sql)) {
			cs.setInt(1, productId);
			rows = cs.executeUpdate();
		}

		return rows > 0;
	}
	// ✅ GET PRODUCT BY ID USING STORED PROCEDURE

	public Map<String, Object> productDataById(Integer productId) throws Exception {

		String sql = "{CALL sp_product_by_id(?)}";
		Map<String, Object> product = new TreeMap<>();

		try (Connection con = DBConnection.getConnection(); CallableStatement cs = con.prepareCall(sql);) {
			cs.setInt(1, productId);

			try (ResultSet rs = cs.executeQuery();) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				if (rs.next()) {
					for (int i = 1; i <= columnCount; i++) {
						product.put(metaData.getColumnLabel(i), rs.getObject(i));
					}

				}
			}
		}
		return product;
	}

	// ✅ UPDATE PRODUCT USING STORED PROCEDURE
	public boolean updateProduct(Integer productId, String productName, Integer brandId, Integer categoryId,
			Integer listPrice, Integer quantity) throws Exception {
		System.out.println("productId: ====>" + productId);
		String sql = "{CALL sp_update_product(?,?,?,?,?,?)}";

		int row;

		try (Connection con = DBConnection.getConnection(); CallableStatement cs = con.prepareCall(sql);) {

			cs.setString(1, productName);
			cs.setInt(2, brandId);
			cs.setInt(3, categoryId);
			cs.setInt(4, listPrice);
			cs.setInt(5, quantity);
			cs.setInt(6, productId);

			row = cs.executeUpdate();
		}
		System.out.println("row ==>" + row);
		return row > 0;
	}

	public List<Map<String, Object>> getBrandList() throws Exception {
		String sql = "{CALL sp_brand_list}";

		List<Map<String, Object>> brands = new ArrayList<>();

		try (Connection con = DBConnection.getConnection();
				CallableStatement cs = con.prepareCall(sql);
				ResultSet rs = cs.executeQuery();) {

			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new TreeMap<>();
				for (int i = 1; i <= colCount; i++) {
					row.put(meta.getColumnLabel(i), rs.getObject(i));
				}
				brands.add(row);
			}
		}
		return brands;
	}

	public List<Map<String, Object>> getCategoryList() throws Exception {

		String sql = "{CALL sp_category_list}";
		List<Map<String, Object>> categoryList = new ArrayList<>();

		try (Connection con = DBConnection.getConnection();
				CallableStatement cs = con.prepareCall(sql);
				ResultSet rs = cs.executeQuery();) {

			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();

			while (rs.next()) {
				Map<String, Object> rows = new TreeMap<>();
				for (int i = 1; i <= colCount; i++) {
					rows.put(meta.getColumnLabel(i), rs.getObject(i));
				}
				categoryList.add(rows);
			}
			return categoryList;
		}
	}

	public Long createOrder() throws Exception {

		String sql = "{CALL sp_create_order}";

		try (Connection con = DBConnection.getConnection();
				CallableStatement cs = con.prepareCall(sql);
				ResultSet rs = cs.executeQuery();) {

			if (rs.next()) {
				long newOrderId = rs.getLong("NewOrderID");
				System.out.println("Generated Order ID: " + newOrderId);
				return newOrderId;
			} else {
				throw new Exception("Creating order failed, no ID obtained.");
			}
		}

	}

	public boolean addOrderProduct(Integer orderId, List<OrderItemDTO> products) throws Exception {

		String sql = "{CALL sp_add_order_product(?,?,?)}";

		try (Connection con = DBConnection.getConnection(); CallableStatement cs = con.prepareCall(sql);) {

			for (OrderItemDTO item : products) {

				cs.setInt(1, orderId);
				cs.setInt(2, item.getProductId());
				cs.setInt(3, item.getOrderQuantity());

				cs.addBatch();
			}

			int[] rows = cs.executeBatch();

			return rows.length > 0;
		}
	}

	public List<Map<String, Object>> getallOrders() throws Exception {

		String sql = "{CALL sp_get_order_produts()}";

		Map<Integer, Map<String, Object>> orderMap = new LinkedHashMap<>();

		try (Connection con = DBConnection.getConnection();
				CallableStatement cs = con.prepareCall(sql);
				ResultSet rs = cs.executeQuery();) {

			while (rs.next()) {

				int orderId = rs.getInt("order_id");

				Map<String, Object> order = orderMap.get(orderId);

				if (order == null) {

					order = new LinkedHashMap<>();

					order.put("order_id", orderId);
					order.put("created_at", rs.getTimestamp("created_at"));

					List<Map<String, Object>> products = new ArrayList<>();

					order.put("products", products);

					orderMap.put(orderId, order);
				}

				List<Map<String, Object>> products = (List<Map<String, Object>>) order.get("products");

				if (rs.getObject("product_id") != null) {

					Map<String, Object> product = new LinkedHashMap<>();

					product.put("product_id", rs.getInt("product_id"));
					product.put("product_name", rs.getString("product_name"));
					product.put("list_price", rs.getInt("list_price"));
					product.put("order_quantity", rs.getInt("order_quantity"));
					product.put("brand_name", rs.getString("brand_name"));
					product.put("category_name", rs.getString("category_name"));

					products.add(product);
				}
			}
		}

		return new ArrayList<>(orderMap.values());
	}
}