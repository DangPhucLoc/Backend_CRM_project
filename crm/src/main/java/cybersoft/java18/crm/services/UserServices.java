package cybersoft.java18.crm.services;

import cybersoft.java18.crm.Repository.UserRepository;
import cybersoft.java18.crm.model.UserModel;

import java.util.List;

public class UserServices {
    private static UserServices INSTANCE = null;

    private UserRepository userRepository = new UserRepository();

    public static UserServices getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserServices();
        }
        return INSTANCE;
    }

    public List<UserModel> getAllUsers() {
        return userRepository.getAllUser();
    }
    public Integer deleteJobById(String id) {
        return userRepository.deleteUser(id);
    }

    public Integer updateUsers(UserModel userModel) {
        return userRepository.updateUser(userModel);
    }

    public Integer saveUser(String email, String password, String fullName, String avatar, String roleId) {
        return userRepository.saveUser(email,password,fullName,avatar,roleId);
    }
}
