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

    public static JSONObject getActiveJobs() {
            //TODO need to add logic for this based on attr such as how many times its already run today
            return  null;
    }

}
