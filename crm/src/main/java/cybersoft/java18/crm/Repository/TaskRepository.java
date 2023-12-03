package cybersoft.java18.crm.Repository;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.TaskModel;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends AbstractRepository<TaskModel>{
        @SneakyThrows
        public List<TaskModel> getAllTask() {
            return executeQuery((connection) ->{List<TaskModel> taskModels = new ArrayList<>();
                String query = """
                        select * from tasks
                        """;
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    TaskModel taskModel = new TaskModel();
                    taskModel.setId(resultSet.getInt("id")) ;
                    taskModel.setName(resultSet.getString("name"));
                    taskModel.setStartDate(resultSet.getString("start_date"));
                    taskModel.setEndDate((resultSet.getString("end_date")));
                    taskModel.setJobId((resultSet.getInt("job_id")));
                    taskModel.setStatusId(resultSet.getInt("status_id"));
                    taskModel.setUserId(resultSet.getInt("user_id"));


                    taskModels.add(taskModel);

                }
                return taskModels;
            });
        }

    public Integer deleteTask(String id) {
        return executeSaveAndUpdate((connection) -> {
            String query = """
                        delete from tasks where id = ?
                        """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);

            return preparedStatement.executeUpdate();


        });


    }

    public Integer updateTask (TaskModel taskModel) {
        return executeSaveAndUpdate( (connection ->
        {
            String query = """
                        update tasks 
                        set name =? , 
                         start_date = ?,
                         end_date = ?,
                         user_id = ?,
                         job_id = ?,
                         status_id = ?
                         where id =?
                        """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, taskModel.getName());
            preparedStatement.setDate(2,java.sql.Date.valueOf(taskModel.getStartDate()));
            preparedStatement.setDate(3,java.sql.Date.valueOf(taskModel.getEndDate()));
            preparedStatement.setInt(4,taskModel.getUserId());
            preparedStatement.setInt(5,taskModel.getJobId());
            preparedStatement.setInt(6,taskModel.getStatusId());
            preparedStatement.setInt(7  ,taskModel.getId());


            return preparedStatement.executeUpdate();
        }
        ));
    }
    public Integer saveTask (String name, String startDate, String endDate
                            ,String userId, String jobId, String statusId) {
        return executeSaveAndUpdate((connection -> {
            String query = """
                    insert into tasks (name,start_date, end_date, user_id, job_id, status_id)
                    value(?,?,?,?,?,?)
                    """;
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, endDate);
            preparedStatement.setString(4,userId);
            preparedStatement.setString(5,jobId);
            preparedStatement.setString(6,statusId);

            return preparedStatement.executeUpdate();
        }
        ));
    }



}
