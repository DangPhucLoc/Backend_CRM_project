package cybersoft.java18.crm.services;

import cybersoft.java18.crm.Repository.StatusRepository;
import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.StatusModel;

import java.util.List;

public class StatusServices {
    private static StatusServices INSTANCE = null;

    private StatusRepository statusRepository = new StatusRepository();
    public static StatusServices getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StatusServices();
        }
        return INSTANCE;
    }

    public List<StatusModel> getAllStatus() {
        return statusRepository.getAllStatus();
    }
    public Integer deleteStatusById (String id) {
        return statusRepository.deleteStatus(id);
    }

    public Integer updateStatusById (StatusModel statusModel) {
        return statusRepository.updateStatus(statusModel);
    }

    public Integer saveStatus( String name) {
        return statusRepository.saveStatus(name);
    }
}
