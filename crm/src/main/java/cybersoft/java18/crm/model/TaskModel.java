package cybersoft.java18.crm.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaskModel {
    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private int userId;
    private int jobId;
    private int statusId;
}
