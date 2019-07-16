import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

//this class will hold active jobs
public class Measurement{
   private static List<Job> activeJobs=new ArrayList<Job>();

   public static boolean addMeasurement(JSONObject jobRequest){
            if(jobRequest==null) return false;
            //TODO there is need for error checking for valid jobRequest structure before addition
            JSONObject jobDesc = jobRequest.getJSONObject("job_description");
            Job job=new Job(jobDesc);
            activeJobs.add(job);
            return true;
        }

    public static JSONArray getActiveJobs() {
            //TODO need to add logic for this based on attr such as how many times its already run today
            JSONArray sentJobs=new JSONArray();
            for(Job job:activeJobs){
                if(job.getCurrCount()<=job.getCountPerDay()){
                    sentJobs.put(job.getMeasurementDesc());
                }
            }
            return sentJobs;
    }

    public static void recordSuccessfulJob(JSONObject jobDesc){
         //assuming the JsonObj has id field only
         String key=jobDesc.getString("key");
         for(Job job:activeJobs){
             String currKey=(String)job.getMeasurementDesc().get("key");
             if(currKey.equals(key)){
                 job.addCurrCount();
                 break;
             }
         }
    }




}