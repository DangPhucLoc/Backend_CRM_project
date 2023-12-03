package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.services.JobServices;
import cybersoft.java18.crm.services.UserServices;
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

@WebServlet(name = "user", urlPatterns = {
        urlUtils.URL_USER,
        urlUtils.URL_USER_ADD
})
public class UserController extends HttpServlet {
    private final Gson gson = new Gson();
    static DataHandler dataHandler = new DataHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("check do get");
        PrintWriter printWriter = resp.getWriter();
        UserModel userModel = new UserModel();
        List<UserModel> listUsers = UserServices.getInstance().getAllUsers();

        String json = gson.toJson(listUsers);

        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Integer result = UserServices.getInstance().deleteJobById(id);
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
        try {
            BufferedReader br = new BufferedReader(req.getReader());
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            System.out.println("check do Put");
            String data = builder.toString();

            UserModel userModel = gson.fromJson(data, UserModel.class);
            Integer result = UserServices.getInstance().updateUsers(userModel);

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
            System.out.println("kiem tra do put " + userModel.getId() + userModel.getEmail());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                Integer result;
                UserModel userModel = new UserModel();
                System.out.println("check do Post");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String fullName = req.getParameter("fullName");
                String avatar = req.getParameter("avatar");
                String roleId = req.getParameter("roleId");
                result = UserServices.getInstance().saveUser(email, password, fullName, avatar, roleId);

                ResponseData responseData = new ResponseData();
                responseData.setStatusCode(200);
                if (result == 1) {

                    responseData.setSuccess(true);
                    responseData.setMessage("update success");
                } else {
                    responseData.setSuccess(false);
                    responseData.setMessage("update fail");
                }
//                    dataHandler.dataHandler(gson.toJson(responseData),resp);

                System.out.println(" check do post email: " + email + " password " + password + " fullName " + fullName + " roleId " +roleId);
        System.out.println("result "+result);
    }

}
