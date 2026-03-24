package com.action.layer;

import java.util.Map;
import java.util.TreeMap;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class GetProductDataByID extends ActionSupport {

    private String message;
    private boolean success;
    private Integer productId;

    ProductService service = new ProductService();

    Map<String, Object> product = new TreeMap<>();

    public String productDataById() throws Exception {

        if (productId == null) {
            message = "Product id is required";
            success = false;
            return SUCCESS;
        }

        product = service.productDataByid(productId);

        if (product == null || product.isEmpty()) {
            message = "Product not found";
            success = false;
        } else {
            message = "Product fetched successfully";
            success = true;
        }

        return SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, Object> getProduct() {
        return product;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}