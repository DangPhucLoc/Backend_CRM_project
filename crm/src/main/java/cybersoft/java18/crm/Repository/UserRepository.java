package cybersoft.java18.crm.Repository;

import cybersoft.java18.crm.model.UserModel;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends AbstractRepository<UserModel>{

    @SneakyThrows
    public List<UserModel> getAllUser() {
        return executeQuery((connection -> {
            List<UserModel> userModels = new ArrayList<>();
            String query = """
                    select * from users
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(resultSet.getInt("id"));
                userModel.setEmail(resultSet.getString("email"));
                userModel.setPassword(resultSet.getString("password"));
                userModel.setFullName(resultSet.getString("fullname"));
                userModel.setAvatar(resultSet.getString("avatar"));
                userModel.setRoleId(resultSet.getInt("role_id"));

                userModels.add(userModel);

            }
            return userModels;
        }

        ));
    }

    public Integer deleteUser(String id) {
        return executeSaveAndUpdate((connection) -> {
            String query = """
                    delete from users where id = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);

            return preparedStatement.executeUpdate();
        });
    }

    public Integer updateUser(UserModel userModel) {
        return executeSaveAndUpdate( (connection -> {
            String query = """
                update users set email =?, password= ?, fullname =?, avatar =?, role_id =? where id =?
                """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userModel.getEmail());
            preparedStatement.setString(2,userModel.getPassword());
            preparedStatement.setString(3, userModel.getFullName());
            preparedStatement.setString(4,userModel.getAvatar());
            preparedStatement.setInt(5,userModel.getRoleId());
            preparedStatement.setInt(6,userModel.getId());

            return preparedStatement.executeUpdate();
        }
        ));
    }

    public Integer saveUser(String email, String password , String fullName, String avatar, String roleId) {
        return executeSaveAndUpdate((connection -> {
            String query = """
                    insert into users (email, password, fullname,avatar,role_id)
                    value(?,?,?,?,?)
                    """;
            PreparedStatement preparedStatement;

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, fullName);
                preparedStatement.setString(4,avatar);
                preparedStatement.setString(5,roleId);

            return preparedStatement.executeUpdate();
        }
        ));
    }


}
