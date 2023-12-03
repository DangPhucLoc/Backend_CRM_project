package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.services.RoleServices;
import cybersoft.java18.crm.utils.urlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "role", urlPatterns = {
        urlUtils.URL_ROLE,
        urlUtils.URL_ROLE_ADD

})
public class RoleController extends HttpServlet {
    private final Gson gson = new Gson();
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("keimtra do get");

        PrintWriter printWriter = resp.getWriter();
        RoleModel roleModel = new RoleModel();
        List<RoleModel> listRoles = RoleServices.getInstance().getAllRole();

        String json = gson.toJson(listRoles);


        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");
        String description = req.getParameter("description");

        Integer result = RoleServices.getInstance().saveRole(role, description);
        ResponseData responseData = new ResponseData();
        responseData.setStatusCode(200);
        if (result == 1) {
            responseData.setSuccess(true);
            responseData.setMessage("add success");
        } else {
            responseData.setSuccess(false);
            responseData.setMessage("add fail");
        }

        System.out.println("kiem tra do post " + role + " - " + description);
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Integer result = RoleServices.getInstance().deleteRoleById(id);
        System.out.println("delete " + result);
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


        RoleModel roleModel = gson.fromJson(data, RoleModel.class);
        Integer result = RoleServices.getInstance().updateRoleById(roleModel);

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
        System.out.println("kiem tra do put " + roleModel.getId() + " - " + roleModel.getName());
    }
}
