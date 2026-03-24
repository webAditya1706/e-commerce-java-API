package com.action.layer;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class DeleteProductAction extends ActionSupport {

    private int productId;
    private boolean success;
    private String message;

    private ProductService service = new ProductService();

    public String deleteProduct() {

        if (productId <= 0) {
            success = false;
            message = "Invalid product ID";
            return INPUT;
        }

        try {
            boolean deleted = service.removeProduct(productId);

            if (deleted) {
                success = true;
                message = "Product deleted successfully";
            } else {
                success = false;
                message = "Product not found";
            }

            return SUCCESS;

        } catch (Exception e) {
            success = false;
            message = "DB error: " + e.getMessage();
            return ERROR;
        }
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}