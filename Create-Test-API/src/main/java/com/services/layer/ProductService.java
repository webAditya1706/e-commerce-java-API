package com.services.layer;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.action.layer.OrderItemDTO;
import com.dao.layer.ProductDao;
import com.example.util.DBConnection;

public class ProductService {

	private ProductDao dao = new ProductDao();

// Get ALl PRODUCT
	public List<Map<String, Object>> fetchProducts() throws Exception {
		return dao.getProductList();
	}

// REMOVE PRODUCT BY ID
	public boolean removeProduct(int productId) throws Exception {
		return dao.deleteProduct(productId);
	}

// ADD PRODUCT
	public boolean addProduct(String productName, Integer brandId, Integer categoryId, Integer listPrice,
			Integer quantity) throws Exception {

		if (productName == null || productName.trim().isEmpty()) {
			throw new Exception("Product name is required");
		}
		return dao.createProduct(productName, brandId, categoryId, listPrice, quantity);
	}

	// GET PRODUCT DAATA BY ID
	public Map<String, Object> productDataByid(Integer productId) throws Exception {
		return dao.productDataById(productId);

	}

// UPDATE PRODUCT BY ID
	public boolean updateProductByid(Integer productId, String productName, Integer brandId, Integer categoryId,
			Integer listPrice, Integer quantity) throws Exception {

		if (productName == null || productName.isEmpty()) {
			throw new Exception("Product name is required");
		}
		return dao.updateProduct(productId, productName, brandId, categoryId, listPrice, quantity);

	}

// GET ALL BRAND LIST
	public List<Map<String, Object>> fetchBrandList() throws Exception {
		return dao.getBrandList();
	}

// GET ALL CATEGORY
	public List<Map<String, Object>> fetchCategoryList() throws Exception {
		return dao.getCategoryList();
	}

	// CREATE ORDER
	public Long createOrder() throws Exception {
		return dao.createOrder();
	}

	// ADD ITEMS IN ORDER LIST
	public boolean addOrderItems(Integer orderId, List<OrderItemDTO> products) throws Exception {
		return dao.addOrderProduct(orderId, products);
	}

	// GET ALL ORDERS
	public List<Map<String, Object>> getAllOrder() throws Exception {
		return dao.getallOrders();
	}

}