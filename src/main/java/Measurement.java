import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Measurement{
        static List<JSONObject> activeTask=new ArrayList<JSONObject>();

        public static boolean addMeasurement(JSONObject task){
            if(task==null) return false;
            //TODO need to add more error checking for valid task structure before addition
            activeTask.add(task);
            return true;
        }

        public static JSONArray getActiveTask(){
            return new JSONArray(activeTask);
        }
}
