package com.action.layer;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class AddProductAction extends ActionSupport {

	private boolean success;
	private String message;

	private String productName;
	private Integer brandId;
	private Integer categoryId;
	private Integer listPrice;
	private Integer quantity;

	private ProductService services = new ProductService();

	public String createProduct() {

		if (productName == null || productName.trim().isEmpty()) {
			success = false;
			message = "Product name is required";
			return INPUT;
		}

		try {
			boolean created = services.addProduct(productName, brandId, categoryId, listPrice, quantity);

			if (created) {
				success = true;
				message = "Product created successfully";
			} else {
				success = false;
				message = "Failed to create product";
			}
			
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception

			success = false;
			message = "DB error " + e.getMessage();
			return ERROR;
		}

	}

	
	// -------- getter and setter ---------
			// setter
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setListPrice(Integer listPrice) {
		this.listPrice = listPrice;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	// getter
	public boolean getSuccess () {
		return success;
	}
	
	public String getMessage() {
		return message;
	}

}