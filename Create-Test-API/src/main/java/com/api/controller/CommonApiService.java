package com.api.controller;

import java.util.List;
import java.util.Map;

import com.example.util.CommonApiDao;

public class CommonApiService {

    private CommonApiDao dao = new CommonApiDao();

    public List<Map<String, Object>> executeApi(String type, int id) throws Exception {
        return dao.callStoredProcedure(type, id);
    }
}