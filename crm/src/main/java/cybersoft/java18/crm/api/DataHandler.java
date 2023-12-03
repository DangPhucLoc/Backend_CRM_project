package cybersoft.java18.crm.api;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DataHandler {
    public void dataHandler(String json, HttpServletResponse response) throws IOException {
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin","*");
            PrintWriter printWriter = response.getWriter();
            printWriter.print(json);
            printWriter.flush();
        }catch (Exception e) {
            throw new RuntimeException("fail to convert Data to Json");
        }
    }
}
