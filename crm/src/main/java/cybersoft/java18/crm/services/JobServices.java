package cybersoft.java18.crm.services;

import cybersoft.java18.crm.Repository.JobRepository;
import cybersoft.java18.crm.model.JobModel;

import java.util.List;

public class JobServices {
    private static JobServices INSTANCE = null;

    private JobRepository jobRepository = new JobRepository();

     public static JobServices getInstance() {
         if(INSTANCE == null) {
             INSTANCE = new JobServices();
         }
         return INSTANCE;
     }

     public List<JobModel> getAllJobs() {
         return jobRepository.getAllJob();
     }

     public Integer deleteJobById(String id) {
         return jobRepository.deleteJob(id);
     }

     public Integer updateJobById(JobModel jobModel) {
         return jobRepository.updateJob(jobModel);
     }

     public Integer saveJob (String name, String startDate, String endDate) {
         return jobRepository.saveJob(name, startDate, endDate);
     }

}
