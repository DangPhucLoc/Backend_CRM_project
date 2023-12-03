package cybersoft.java18.crm.Repository;

import cybersoft.java18.crm.model.RoleModel;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository extends AbstractRepository<RoleModel> {
        @SneakyThrows
        public List<RoleModel> getAllRole() {
                return executeQuery((connection) ->{List<RoleModel> roleModels = new ArrayList<>();
                        String query = """
                        select * from roles
                        """;
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    RoleModel roleModel = new RoleModel();
                    roleModel.setId(resultSet.getInt("id")) ;
                    roleModel.setName( resultSet.getString("name")) ;
                    roleModel.setDescription(resultSet.getString("description")) ;

                    roleModels.add(roleModel);

            }
            return roleModels;
                });
            }

            public Integer deleteRole(String id) {
           return executeSaveAndUpdate((connection) -> {
                String query = """
                        delete from roles where id=?
                        """;
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,id);

                return preparedStatement.executeUpdate();


            });


            }
            public Integer updateRole (RoleModel roleModel) {
            return executeSaveAndUpdate( (connection ->
            {
                String query = """
                        update roles set name =? , description =? WHERE id =?
                        """;
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, roleModel.getName());
                preparedStatement.setString(2,roleModel.getDescription());
                preparedStatement.setInt(3, roleModel.getId());

                return preparedStatement.executeUpdate();
            }
                    ));
            }

            public Integer saveRole(String role, String description) {
            return executeSaveAndUpdate((connection ->  {
                String query = """
                       insert into roles (name, description) 
                       value ( ?,? ) 
                        """;
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,role);
                preparedStatement.setString(2,description);

                return preparedStatement.executeUpdate();
            }

                    ));
            }

        }
