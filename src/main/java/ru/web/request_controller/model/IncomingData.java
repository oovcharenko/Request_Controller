package ru.web.request_controller.model;

import lombok.Getter;

@Getter
public class IncomingData {
    private String server;
    private Short port;
    private String login;
    private String password;
    private String command;

}
