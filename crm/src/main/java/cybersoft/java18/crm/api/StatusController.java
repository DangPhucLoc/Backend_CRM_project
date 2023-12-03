package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.StatusModel;
import cybersoft.java18.crm.services.RoleServices;
import cybersoft.java18.crm.services.StatusServices;
import cybersoft.java18.crm.utils.urlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name ="status" , urlPatterns = {
        urlUtils.URL_STATUS
})
public class StatusController extends HttpServlet {
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        StatusModel statusModel = new StatusModel();
        List<StatusModel> listStatus = StatusServices.getInstance().getAllStatus();

        String json = gson.toJson(listStatus);

        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        Integer result = StatusServices.getInstance().saveStatus(name);
        ResponseData responseData = new ResponseData();
        responseData.setStatusCode(200);
        if (result == 1) {
            responseData.setSuccess(true);
            responseData.setMessage("add success");
        } else {
            responseData.setSuccess(false);
            responseData.setMessage("add fail");
        }

        System.out.println("kiem tra do post " + name );
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Integer result = StatusServices.getInstance().deleteStatusById(id);
        ResponseData responseData = new ResponseData();
        responseData.setStatusCode(200);
        if (result == 1) {
            responseData.setSuccess(true);
            responseData.setMessage("delete success");
        } else {
            responseData.setSuccess(false);
            responseData.setMessage("delete fail");
        }

        req.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = resp.getWriter();

        printWriter.print(gson.toJson(responseData));
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        BufferedReader br = new BufferedReader(req.getReader());
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }

        String data = builder.toString();

        StatusModel statusModel = gson.fromJson(data, StatusModel.class);
        Integer result = StatusServices.getInstance().updateStatusById(statusModel);

        ResponseData responseData = new ResponseData();

        if (result == 1) {
            responseData.setSuccess(true);
            responseData.setMessage("update success");
        } else {
            responseData.setSuccess(false);
            responseData.setMessage("update fail");
        }
        PrintWriter printWriter = resp.getWriter();

        printWriter.print(gson.toJson(responseData));
        printWriter.flush();
        System.out.println("kiem tra do put " + statusModel.getName());
    }

}
