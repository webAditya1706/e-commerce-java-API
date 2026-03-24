package com.action.layer;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class CreateOrderAction extends ActionSupport {

    private boolean success;
    private String message;
    private Long orderId;

    ProductService service = new ProductService();

    public String createOrder() {

        try {

            Long newOrderId = service.createOrder();

            if (newOrderId != null) {
                success = true;
                message = "Order created!";
                orderId = newOrderId;
            } else {
                success = false;
                message = "Failed to create order";
            }

            return SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
            message = "Failed to create order";
            return ERROR;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Long getOrderId() {
        return orderId;
    }
}