package ru.web.request_controller.model;

import lombok.Data;

@Data
public class ResponseData {
    private Boolean connected;
    private Boolean commandExecuted;
    private String connectResponce;
    private String commandResponce;
}
