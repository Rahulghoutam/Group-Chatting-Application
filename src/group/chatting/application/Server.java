package group.chatting.application;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable {
    
    Socket socket;//golabal declare
    
    public static Vector client = new Vector();
    
    public Server(Socket socket){
        try{
            this.socket = socket;
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    //this is the multi threading concept
    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            client.add(writer);
            
            while(true){
                String data = reader.readLine().trim();//trim is use cut the unwanted space in messages
                System.out.println("Received" + data);
                
                for(int i=0; i<client.size(); i++){
                    try{
                        BufferedWriter bw = (BufferedWriter)client.get(i);
                        bw.write(data);
                        bw.write("\r\n");
                        bw.flush();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                
            }
            

        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) throws Exception {
        ServerSocket s = new ServerSocket(2003);
        while(true){
            Socket socket = s.accept();
            Server server = new Server(socket);//store the socket in the constructor
            Thread thread = new Thread(server);
            thread.start();//this method call the run method internally
        }
    }
    
}
