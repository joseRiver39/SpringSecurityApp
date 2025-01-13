package com.App.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author ANTONIO
 */
@JsonPropertyOrder({"username","messaje","jwt","status"})
public record AuthResponse(String username,
        String message,
        String jwt,
        boolean status) {

}
