import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class OrchServer {
    final static int PORT=8000;
    public static void start(){
        try {
            ServerSocket serverSocket= new ServerSocket(PORT);
            System.out.println("Server Started");
            while(true){
                Socket clientSocket=serverSocket.accept();
                Thread handler = new Thread(new RequestHandler(clientSocket));
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
