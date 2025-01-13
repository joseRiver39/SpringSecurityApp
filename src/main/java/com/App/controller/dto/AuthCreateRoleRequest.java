/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.App.controller.dto;

import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author ANTONIO
 */
@Validated
public record AuthCreateRoleRequest (@Size(max = 3, message = "El Usuario no puede tener mas de  3 roles")List<String> roleListName){
    
}
