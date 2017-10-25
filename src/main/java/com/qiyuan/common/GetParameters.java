package com.qiyuan.common;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class GetParameters {
    public static void getParameters(HttpServletRequest request,String...p) {

        BufferedReader reader = null;
        try {
            reader = request.getReader();
            StringBuilder reportBuilder = new StringBuilder();
            String tempStr = "";
            while ((tempStr = reader.readLine()) != null) {
                reportBuilder.append(tempStr);
            }
            String reportContent = reportBuilder.toString();
            String req = SecretUtils.Decrypt3DES(reportContent);
            ObjectMapper json = new ObjectMapper();
            Map<String, String> requestMap = json.readValue(req, Map.class);

            for (int i = 0; i < p.length; i++) {
                for (Map.Entry<String, String> entry : requestMap.entrySet()) {
//            value = StringUtil.isEmpty(value) ? "" : value;
                    p[i] = p[i].equals(entry.getKey()) ? entry.getValue() : "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
