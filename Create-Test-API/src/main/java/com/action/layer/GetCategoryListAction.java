package com.action.layer;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class GetCategoryListAction extends ActionSupport {
	
	private List<Map<String, Object>> categoryList;
	private String message;
	private Boolean success;
	
	private ProductService services = new ProductService();
	
	public String getCategoryList() {
		try {
			categoryList = services.fetchCategoryList();
			
			if(categoryList == null || categoryList.isEmpty()) {
				success = false;
				message = "Category not found";
			}else {
				success = true;
				message = "Category list fetched successfully";
			}
			
			return SUCCESS;
			
		} catch (Exception e) {
			// TODO: handle exception
			success = false;
			message = "DB Error " + e.getMessage();
			return ERROR;
		}
	}
	
	
	public List<Map<String, Object>> getCategory(){return categoryList;}
	public boolean isSuccess () {return success;}
	public String getMessage() {return message;}

}
