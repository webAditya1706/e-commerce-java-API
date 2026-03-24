package com.example.util;

import com.example.util.DBConnection;
import java.sql.*;
import java.util.*;

public class CommonApiDao {

    public List<Map<String, Object>> callStoredProcedure(String type, int id) throws Exception {

        List<Map<String, Object>> list = new ArrayList<>();

        Connection con = DBConnection.getConnection();
        CallableStatement cs = con.prepareCall("{call sp_common_api(?, ?)}");

        cs.setString(1, type);
        cs.setInt(2, id);

        ResultSet rs = cs.executeQuery();
        ResultSetMetaData meta = rs.getMetaData();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                row.put(meta.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        rs.close();
        cs.close();
        con.close();

        return list;
    }
}
