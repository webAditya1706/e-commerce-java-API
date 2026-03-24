package com.test.apis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.util.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

public class GetOrderListAction extends ActionSupport {

    private boolean success;
    private String message;

    private List<Map<String, Object>> orderList = new ArrayList<>();

    public String getOrderList() {
        orderList.clear();   // 🔥 VERY IMPORTANT

    	String sql =
    		    "SELECT " +
    		    "o.order_id, o.product_id, o.order_quantity, " +
    		    "o.created_at AS order_date, " +
    		    "p.product_name, p.list_price, " +
    		    "b.brand_name, c.category_name " +
    		    "FROM orders o " +
    		    "INNER JOIN product p ON o.product_id = p.product_id " +
    		    "INNER JOIN brand b ON b.brand_id = p.brand_id " +
    		    "INNER JOIN category c ON c.category_id = p.category_id " +
    		    "ORDER BY p.product_name";



        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	System.out.println(rs);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {

                Map<String, Object> order = new TreeMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    order.put(metaData.getColumnLabel(i), rs.getObject(i));
                }

                orderList.add(order);
            }

            if (orderList.isEmpty()) {
                message = "No orders found";
                success = false;
            } else {
                message = "Order list fetched successfully";
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

    // ===== GETTERS =====

    public List<Map<String, Object>> getOrderListData() {
        return orderList;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
