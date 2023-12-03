package cybersoft.java18.crm.Repository;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.StatusModel;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository extends AbstractRepository{
    @SneakyThrows
    public List<StatusModel> getAllStatus() {
        return executeQuery((connection) ->{List<StatusModel> statusModels = new ArrayList<>();
            String query = """
                        select * from status
                        """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(resultSet.getInt("id")) ;
                statusModel.setName( resultSet.getString("name")) ;
                statusModels.add(statusModel);
            }
            return statusModels;
        });
    }

    public Integer deleteStatus(String id) {
        return executeSaveAndUpdate((connection) -> {
            String query = """
                        delete from status where id = ?
                        """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);

            return preparedStatement.executeUpdate();


        });


    }
    public Integer updateStatus (StatusModel statusModel) {
        return executeSaveAndUpdate( (connection ->
        {
            String query = """
                        update status set name =? WHERE id =?
                        """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, statusModel.getName());
            preparedStatement.setInt(2,statusModel.getId());

            return preparedStatement.executeUpdate();
        }
        ));
    }

    public Integer saveStatus(String name) {
        return executeSaveAndUpdate((connection ->  {
            String query = """
                       insert into status (name) 
                       value ( ? ) 
                        """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);

            return preparedStatement.executeUpdate();
        }

        ));
    }
}
