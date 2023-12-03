package cybersoft.java18.crm.services;

import cybersoft.java18.crm.Repository.TaskRepository;
import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.model.UserModel;

import java.util.List;

public class TaskServices {
    private static TaskServices INSTANCE = null;

    private TaskRepository taskRepository = new TaskRepository();

    public static TaskServices getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TaskServices();
        }
        return INSTANCE;
    }
    public List<TaskModel> getAllTask() {
        return taskRepository.getAllTask();
    }
    public Integer deleteTaskById(String id) {
        return taskRepository.deleteTask(id);
    }

    public Integer updateTask(TaskModel taskModel) {
        return taskRepository.updateTask(taskModel);
    }

    public Integer saveTask(String name, String startDate, String endDate, String userId, String jobId, String statusId) {
        return taskRepository.saveTask(name, startDate, endDate, userId, jobId, statusId);
    }
}
