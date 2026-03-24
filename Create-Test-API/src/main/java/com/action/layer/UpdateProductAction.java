package com.action.layer;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class UpdateProductAction extends ActionSupport {

	private boolean success;
	private String message;

	private Integer productId;
	private String productName;
	private Integer brandId;
	private Integer categoryId;
	private Integer listPrice;
	private Integer quantity;

	ProductService service = new ProductService();

	public String updateProduct() {
		
		System.out.println("inside update method");
		if (productName == null || productName.trim().isEmpty()) {
			success = false;
			message = "Product name is required";
			return INPUT;
		}

		if (productId == null) {
			success = false;
			message = "productId is required";
			return INPUT;
		}

		try {
			boolean updated = service.updateProductByid(productId, productName, brandId, categoryId, listPrice,
					quantity);
System.out.println("updated ==>" + updated);
			if (updated) {
				success = true;
				message = "Product uptedeted successfully";
			} else {
				success = false;
				message = "Product not found";
			}

		} catch (Exception e) {
			// TODO: handle exception
			success = false;
			message = "DB Error: " + e.getMessage();
			return ERROR;
		}
		return SUCCESS;
	}
// ----------- ** GETTER ** -------
	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
	
	// ------------ *** SETTER *** ---------
	
	public void setProductId(Integer productId) {
	    this.productId = productId;
	}

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

}
