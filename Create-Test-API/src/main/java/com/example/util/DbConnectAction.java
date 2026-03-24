package com.example.util;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.Connection;

public class DbConnectAction extends ActionSupport {

    private String message;
    private boolean success;

    // This method will be called from struts.xml
    public String connectdataBase() {
        try (Connection conn = DBConnection.getConnection()) {
            // If connection is successful
            if (conn != null && !conn.isClosed()) {
                message = "DB connection is successful";
                success = true;
            } else {
                message = "Failed to connect to DB";
                success = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Database error occurred: " + e.getMessage();
            success = false;
            return ERROR;
        }

        return SUCCESS;
    }

    // JSON or JSP will use these getters
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
