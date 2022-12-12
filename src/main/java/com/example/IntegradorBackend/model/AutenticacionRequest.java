package com.example.IntegradorBackend.model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class AutenticacionRequest {
    private String username;
    private String password;
}
