import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RequestHandler implements Runnable {

    final String MEASUREMENT_REQUEST_TYPE="Measurement";
    final String MEASUREMENT_CHECK_IN_TYPE="checkIn";

    private Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
       JSONObject request=encodeJSON();
       String requestType = (String)request.get("request_type");
       if(requestType.equals(MEASUREMENT_CHECK_IN_TYPE)){
           //send the client a list of available jobs;
           sendActiveTasks();
       }
       else if(requestType.equals(MEASUREMENT_REQUEST_TYPE)){
            //the request contains Measurement Description so should be added to the list of jobs
            addMeasurement(request);
       }
       else{
           //send error json to repeat since the type is not supported
       }
    }
    public JSONObject encodeJSON(){
        JSONObject request=null;
        try {
            Scanner in = new Scanner(clientSocket.getInputStream());
            String jsonString=in.nextLine();
            request=new JSONObject(jsonString);
            return request;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }
    public void addMeasurement(JSONObject measurementDesc){
        Measurement.addMeasurement(measurementDesc);
    }
    public void sendActiveTasks(){
        try {
            PrintWriter out=new PrintWriter(clientSocket.getOutputStream());
            JSONArray activeTasks=Measurement.getActiveTask();
            out.println(activeTasks.toString());
            out.flush();
            System.out.println(activeTasks.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
