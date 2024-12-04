package ru.web.request_controller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.web.request_controller.model.IncomingData;
import ru.web.request_controller.model.ResponseData;
import ru.web.request_controller.service.SocketManagementService;

import java.io.IOException;
import java.net.Socket;

@RestController
public class HandlerPostRequest {

    private final SocketManagementService service;

    @Autowired
    public HandlerPostRequest(SocketManagementService service) {
        this.service = service;
    }

    @PostMapping(value = "/send_message_to_tcp")
    public ResponseEntity<String> openBarrier(@RequestBody IncomingData data) throws IOException {

        HttpStatus httpStatus = HttpStatus.OK;
        Socket socket = service.getSocket(data);

        if (socket == null) {
            return new ResponseEntity<>("Invalid Server IP or port", HttpStatus.BAD_REQUEST);
        }

        ResponseData responseData = new ResponseData();
        if (!service.loginServer(socket, data, responseData)) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else if (!service.sendSocketCommand(socket, data, responseData)) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        String jsonResponse = getJSON(responseData);
        socket.close();

        return new ResponseEntity<>(jsonResponse, httpStatus);
    }

    public String getJSON(ResponseData responseData) {

        try {
            return new ObjectMapper().writeValueAsString(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error converting to JSON";
        }
    }
}
