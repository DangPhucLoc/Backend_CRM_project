package cybersoft.java18.crm.services;

import cybersoft.java18.crm.Repository.RoleRepository;
import cybersoft.java18.crm.model.RoleModel;

import java.util.List;

public class RoleServices {
    private static RoleServices INSTANCE = null;
    private RoleRepository roleRepository = new RoleRepository();

      public static RoleServices getInstance() {
          if(INSTANCE == null) {
              INSTANCE = new RoleServices();
          }
          return INSTANCE;
      }

      public List<RoleModel> getAllRole() {
          return roleRepository.getAllRole();
      }
      public Integer deleteRoleById (String id) {
         return roleRepository.deleteRole(id);
      }

      public Integer updateRoleById (RoleModel roleModel) {
          return roleRepository.updateRole(roleModel);
      }

      public Integer saveRole( String role, String description) {
          return roleRepository.saveRole(role, description);
      }

}
