package com.action.layer;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class AddOrderAction extends ActionSupport {

    private Integer orderId;
    private List<OrderItemDTO> products;

    private boolean success;
    private String message;

    ProductService service = new ProductService();

    public String addOrders() {

        try {

            if (orderId == null) {
                message = "orderId is required";
                success = false;
                return SUCCESS;
            }

            if (products == null || products.isEmpty()) {
                message = "products list is empty";
                success = false;
                return SUCCESS;
            }

            boolean result = service.addOrderItems(orderId, products);

            if (result) {
                success = true;
                message = "Products added to order";
            } else {
                success = false;
                message = "Failed to add products";
            }

            return SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
            message = "DB ERROR: " + e.getMessage();
            return ERROR;
        }
    }

    // getters setters

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItemDTO> products) {
        this.products = products;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}