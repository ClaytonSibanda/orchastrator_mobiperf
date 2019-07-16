import org.json.JSONObject;

public class Job {
    private JSONObject measurementDesc;
    private int countPerDay;
    private int currCount;
    private int Duration;

    public Job (JSONObject jobDesc){
        countPerDay =jobDesc.getInt("per_day_count");
        measurementDesc=jobDesc.getJSONObject("measurement_desc");
        currCount=0;
        //duration will be calulcated based on the start and end date
    }

    public JSONObject getMeasurementDesc() {
        return measurementDesc;
    }

    public int getCountPerDay() {
        return countPerDay;
    }

    public int getCurrCount() {
        return currCount;
    }

    public int getDuration() {
        return Duration;
    }

    public void addCurrCount(){
        int count=currCount+1;
        if(count<= countPerDay) {
            this.currCount = count;
        }
    }
}
