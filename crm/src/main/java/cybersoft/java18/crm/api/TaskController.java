package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.services.TaskServices;
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

@WebServlet(name ="task", urlPatterns = {
        urlUtils.URL_TASK,
        urlUtils.URL_TASK_ADD
})
public class TaskController extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        TaskModel taskModel = new TaskModel();
        List<TaskModel> listTask = TaskServices.getInstance().getAllTask();

        String json = gson.toJson(listTask);
        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskModel taskModel = new TaskModel();

        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String userId = req.getParameter("userId");
        String jobId = req.getParameter("jobId");
        String statusId = req.getParameter("statusId");

        Integer result = TaskServices.getInstance().saveTask(name,startDate,endDate,userId,jobId,statusId);
        ResponseData responseData = new ResponseData();
        responseData.setStatusCode(200);
        if (result == 1) {

            responseData.setSuccess(true);
            responseData.setMessage("update success");
        } else {
            responseData.setSuccess(false);
            responseData.setMessage("update fail");
        }
        System.out.println("check doPost : name "+name + " start Date : "+startDate+ " job Id : "+jobId);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Integer result = TaskServices.getInstance().deleteTaskById(id);

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
            String data = builder.toString();
            TaskModel taskModel = gson.fromJson(data, TaskModel.class);
            Integer result = TaskServices.getInstance().updateTask(taskModel);

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
            System.out.println("check doPut name :" + taskModel.getName() + " startDate: " + taskModel.getStartDate() + " jobId " + taskModel.getJobId());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
