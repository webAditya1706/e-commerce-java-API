package com.action.layer;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.services.layer.ProductService;

public class GetBrandListAction extends ActionSupport {
	private List<Map<String, Object>> brands;
	
	private String message;
	private boolean success;
	
	private ProductService service = new ProductService();
	
	public String getBrandList () {
		
		
		try {
			brands = service.fetchBrandList();
			if(brands == null || brands.isEmpty()) {
				success = false;
				message = "brands not found";
			}else {
				success = true;
				message = "Brand list fetched successfully";
			}
			return SUCCESS;
			
		} catch (Exception e) {
			// TODO: handle exception
			success = false;
			message = "DB Error " + e.getMessage();
			return ERROR;
		}
		
	}
	
	
	
	public List<Map<String, Object>> getBrands (){return brands;}
	public boolean isSuccess() {return success;}
	public String getMessage() { return message;}
	
	
}
