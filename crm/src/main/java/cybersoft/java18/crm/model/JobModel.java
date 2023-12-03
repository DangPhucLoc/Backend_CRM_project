package cybersoft.java18.crm.model;

import lombok.Data;

import java.sql.Date;

@Data
public class JobModel {
    private int id;
    private String name;
    private String startDate;
    private String endDate;

}
