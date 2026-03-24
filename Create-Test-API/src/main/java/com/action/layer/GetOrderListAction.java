package com.action.layer;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class GetOrderListAction extends ActionSupport {

    private boolean success;
    private String message;

    private List<Map<String,Object>> orders;

    ProductService service = new ProductService();

    public String orderlistMethod() {

        try {

            orders = service.getAllOrder();

            if (orders == null || orders.isEmpty()) {
                success = false;
                message = "No orders found";
            } else {
                success = true;
                message = "Order list fetched successfully";
            }

            return SUCCESS;

        } catch (Exception e) {

            success = false;
            message = "DB Error: " + e.getMessage();
            return ERROR;
        }
    }

    public List<Map<String,Object>> getOrders(){
        return orders;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }
}