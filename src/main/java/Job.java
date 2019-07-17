import org.json.JSONObject;
import java.util.Date;

public class Job {
    private JSONObject measurementDesc;
    private Date startTime;
    private Date endTime;
    private Date nextReset;
    private int jobInterval; //in hrs
    private int requiredNodeCount;
    private int currCount;

    public Job (JSONObject jobDesc){
        requiredNodeCount =jobDesc.getInt("node_count");
        jobInterval=jobDesc.getInt("job_interval");//this will dictate if recurring or not
        startTime=Utils.getDate(jobDesc.getString("start_time"));
        endTime=Utils.getDate(jobDesc.getString("end_time"));
        measurementDesc=jobDesc.getJSONObject("measurement_desc");
        currCount=0;
        setNextResetTime();
        //TODO create nextReset
    }

    private void setNextResetTime(){
        //if not recurring creating a new next reset time is useless as wont use this field
        if(isRecurring()) {
            this.nextReset=Utils.addHours(startTime,jobInterval);
        }
    }
    //get job with the right parameters in the measurement desc
    public JSONObject getMeasurementDesc() {
        measurementDesc.put("start_time",Utils.formatDate(startTime));
        measurementDesc.put("end_time",Utils.formatDate(nextReset));
        //TODO will have to alter the priority based on the interval
        return measurementDesc;
    }

    public boolean canStart(Date currentTime){
        return currentTime.after(startTime);
    }

    public boolean nodesReached(){
        return currCount == requiredNodeCount;
    }

    public boolean isRecurring(){
        return jobInterval != 0;
    }

    public void addCurrCount(){
        int count=currCount+1;
        if(count<=requiredNodeCount) {
            this.currCount = count;
        }
    }

    public void decrementCurrCount(){
        int count=currCount-1;
        if(count>=0) {
            this.currCount=count;
        }
    }

    public boolean jobElapsed(){
        Date presentTime = new Date(); //either create once or all the time when checking
        return presentTime.after(endTime);
    }

    public boolean isRemovable(){
        if(jobElapsed()){
            return true;
        }
        if(!isRecurring()){ //means the job is not to be repeated and if the req nodes are reached then can be removed
            return nodesReached();
        }
        return false; //then is recurring and
    }

    public boolean isResettable(){
     if(!isRecurring()) return false; //if the job does not need to be repeated then doesnt need to be reset
     else{
         if (nodesReached()){  //if it is recurring and the node count is reached reset
             return true;
         }
         if(nextReset.after(startTime)){
             //if the next reset time is reached then reset the time and nodes (might need to change TODO ASK JOSIAH)
             // and wait for the next repetition of the job
             return true;
         }
     }
     return false;
    }

    public void reset(){
        if(isRecurring()){
            currCount=0;
            startTime=nextReset;
            //this will create a new Date obj thus start and next wont be pointing to the same object
            //will use new start time(obtained from the prev reset time) and interval to create the next Reset time
            setNextResetTime();
        }
    }
}
