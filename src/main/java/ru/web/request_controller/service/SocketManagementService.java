package ru.web.request_controller.service;

import org.springframework.stereotype.Service;
import ru.web.request_controller.model.IncomingData;
import ru.web.request_controller.model.ResponseData;

import java.io.*;
import java.net.Socket;

@Service
public class SocketManagementService {

    final String PRE_CONNECTION_STRING = "LOGIN 1.8 \"%s\" \"%s\"";

    public Socket getSocket(IncomingData data){
        try {
            return new Socket(data.getServer(), data.getPort());
        } catch (IOException e) {
            return null;
        }
    }

    public boolean loginServer(Socket socket, IncomingData data, ResponseData responseData){

        boolean connected = false;
        String answer = "";

        try {
            String connectionString = String.format(PRE_CONNECTION_STRING, data.getLogin(), data.getPassword());

            answer = sendCommand(socket, connectionString);

            connected = answer.equals("OK");
            responseData.setConnected(connected);
            responseData.setConnectResponce(answer);

        } catch (IOException e) {
            responseData.setConnected(connected);
            responseData.setConnectResponce("Не удалось авторизоваться на сервере");
        }

        return connected;
    }

    public String sendCommand(Socket socket, String command) throws IOException {

        OutputStream outputStream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println(command);
        out.flush();

        InputStream inputStream = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        return in.readLine();
    }

    public boolean sendSocketCommand(Socket socket, IncomingData data, ResponseData responseData){

        boolean commandExecuted = false;
        String answer = "";
        try {

            answer = sendCommand(socket, data.getCommand());

            commandExecuted = answer.equals("OK");
            responseData.setCommandExecuted(commandExecuted);
            responseData.setCommandResponce(answer);

        } catch (IOException e) {
            responseData.setCommandExecuted(false);
            responseData.setCommandResponce("Не удалось выполнить команду");
        }

        return commandExecuted;
    }
}
