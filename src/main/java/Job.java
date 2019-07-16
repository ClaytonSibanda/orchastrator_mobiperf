import org.json.JSONObject;

public class Job {
    JSONObject measurementDesc;
    int CountPerDay;
    int currCount;
    int Duration;

    public Job (JSONObject jobDesc){
        CountPerDay=jobDesc.getInt("per_day_count");
        measurementDesc=jobDesc.getJSONObject("measurement_desc");
        currCount=0;
        //duration will be calulcated based on the start and end date
    }

}
