package com.test.apis;

import java.sql.*;
import java.util.*;

import com.example.util.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

public class ProductAction extends ActionSupport {

    // ===== Common Response Fields =====
    private String message;
    private boolean success;

    // ===== Insert Fields =====
    private String productName;
    private Integer brandId;
    private Integer categoryId;
    private Integer quantity;
    private Integer listPrice;

    // ===== List Response =====
    private List<Map<String, Object>> products = new ArrayList<>();

    // ===============================
    // 🔹 CREATE PRODUCT METHOD
    // ===============================
    public String createProduct() {

        String sql = "INSERT INTO product " +
                "(product_name, brand_id, category_id, list_price, quantity) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, productName);
            ps.setInt(2, brandId);
            ps.setInt(3, categoryId);
            ps.setInt(4, listPrice);
            ps.setInt(5, quantity);

            ps.executeUpdate();

            message = "Product inserted successfully";
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
            message = "Database error: " + e.getMessage();
            success = false;
            return ERROR;
        }

        return SUCCESS;
    }

    // ===============================
    // 🔹 FETCH PRODUCT LIST METHOD
    // ===============================
    public String fetchProductList() {

        String sql = "SELECT * FROM product";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
                message = "Product fetched successfully";
                success = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "Database error: " + e.getMessage();
            success = false;
            return ERROR;
        }

        return SUCCESS;
    }

    // ===============================
    // 🔹 GETTERS
    // ===============================
    public String getMessage() { return message; }

    public boolean isSuccess() { return success; }

    public List<Map<String, Object>> getProducts() { return products; }

    // ===============================
    // 🔹 SETTERS (IMPORTANT)
    // ===============================
    public void setProductName(String productName) { this.productName = productName; }

    public void setBrandId(Integer brandId) { this.brandId = brandId; }

    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public void setListPrice(Integer listPrice) { this.listPrice = listPrice; }
}
