import java.util.List;

public class JobTracker implements Runnable {
    public void run() {
     List<Job> activeJobs=Measurement.getJobs();
     while(true){
         //TODO synchronize appropriately as well as how often does the thread check
            //if end time is reached remove job
            //if its a recurring job once
            //loop backwards so as to avoid skipping an index if I remove and element
            for(int i=activeJobs.size()-1;i>=0;i--){
                Job job=activeJobs.get(i);
                if(job.isRemovable()){
                    activeJobs.remove(i);
                    continue;
                }
                if(job.isResettable()){
                    job.reset();
                }
            }
        }
    }
}
