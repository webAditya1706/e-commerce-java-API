package com.action.layer;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class ProductListAction extends ActionSupport {

    private List<Map<String, Object>> products;
    private boolean success;
    private String message;

    private ProductService service = new ProductService();

    public String getProductList() {

        try {
            products = service.fetchProducts();

            if (products == null || products.isEmpty()) {
                success = false;
                message = "Product not found";
            } else {
                success = true;
                message = "Product list fetched successfully";
            }

            return SUCCESS;

        } catch (Exception e) {
            success = false;
            message = "DB error: " + e.getMessage();
            return ERROR;
        }
    }

    public List<Map<String, Object>> getProducts() { return products; }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
