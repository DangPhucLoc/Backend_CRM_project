package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.model.ResponseData;
import cybersoft.java18.crm.services.JobServices;
import cybersoft.java18.crm.utils.urlUtils;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "job", urlPatterns = {
        urlUtils.URL_JOB,
        urlUtils.URL_JOB_ADD
})
public class JobController extends HttpServlet {
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("kiem tra do get");

        PrintWriter printWriter = resp.getWriter();
        JobModel jobModel = new JobModel();
        List<JobModel> listJobs = JobServices.getInstance().getAllJobs();
        String json = gson.toJson(listJobs);
        resp.setContentType("application/json");
        printWriter.print(json);
        printWriter.flush();
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String startDate = req.getParameter("startDate");
            String endDate = req.getParameter("endDate");

            Integer result = JobServices.getInstance().saveJob(name, startDate, endDate);
            ResponseData responseData = new ResponseData();
            responseData.setStatusCode(200);
            if (result == 1) {
                responseData.setSuccess(true);
                responseData.setMessage("add success");
            } else {
                responseData.setSuccess(false);
                responseData.setMessage("add fail");
            }

            System.out.println("name" + name + " startdate: "+startDate);
            System.out.println("result:" +result);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Integer result = JobServices.getInstance().deleteJobById(id);
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
            String data = builder.toString();
            //String json = paramJson(data);
            JobModel jobModel = gson.fromJson(data, JobModel.class);
            Integer result = JobServices.getInstance().updateJobById(jobModel);
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
            System.out.println("kiem tra do put " + jobModel.getId() + " - " + jobModel.getName() +" - "+jobModel.getStartDate());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String paramJson(String paramIn) {
        paramIn = paramIn.replaceAll("=", "\":\"");
        paramIn = paramIn.replaceAll("&", "\",\"");
        return "{\"" + paramIn + "\"}";
    }

}
