package com.test.apis;

import com.api.controller.CommonApiService;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.Map;

public class CommonApiAction extends ActionSupport {

    private String type;   // product / order
    private int id;

    private boolean success;
    private String message;
    private List<Map<String, Object>> data;

    private CommonApiService service = new CommonApiService();

    // ===== API METHOD =====
    public String executeApi() {
        try {
            data = service.executeApi(type, id);
            success = true;
            return SUCCESS;
        } catch (Exception e) {
            success = false;
            message = e.getMessage();
            return ERROR;
        }
    }

    // ===== Getters & Setters =====
    public void setType(String type) { this.type = type; }
    public void setId(int id) { this.id = id; }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Map<String, Object>> getData() { return data; }
}