package com.example.sistema_vendas_api.controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class TesteController {

    @GetMapping("/api/teste")
    public String teste() {
        return "✅ Endpoint /api/teste funcionando corretamente!";
    }
}
