package cybersoft.java18.crm.Repository;

import cybersoft.java18.crm.model.JobModel;

import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class JobRepository extends AbstractRepository<JobModel>{
    @SneakyThrows
    public List<JobModel> getAllJob() {
        return executeQuery((connection -> {
            List<JobModel> jobModels = new ArrayList<>();
            String query = """
                    select * from jobs
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setId(resultSet.getInt("id"));
                jobModel.setName(resultSet.getString("name"));
                jobModel.setStartDate(resultSet.getString("start_date"));
                jobModel.setEndDate(resultSet.getString("end_date"));

                jobModels.add(jobModel);
            }
         return jobModels;
        }

        ));
    }

    public Integer deleteJob(String id) {
        return executeSaveAndUpdate((connection) -> {
            String query = """
                    delete from jobs where id = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);

            return preparedStatement.executeUpdate();

        }
        );
    }

    public Integer updateJob (JobModel jobModel) {
        return executeSaveAndUpdate (connection ->{
            String query = """
                    update jobs
                    set name =?, start_date=?, end_date=?
                    where id =?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, jobModel.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf( jobModel.getStartDate()));
            preparedStatement.setDate(3, java.sql.Date.valueOf( jobModel.getEndDate()));
            preparedStatement.setInt(4,jobModel.getId());

            return preparedStatement.executeUpdate();
        });
    }
    public Integer saveJob (String name, String startDate, String endDate) {
        return executeSaveAndUpdate((connection ->{
            String query = """
                    insert into jobs (name, start_date, end_date)
                    value (?,?,?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, endDate);


            return preparedStatement.executeUpdate();

        }
        ));
    }


}
