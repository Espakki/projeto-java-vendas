package com.example.sistema_vendas_api.controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class TesteController {

    @GetMapping("/")
    public String home() {
        return "🚀 API Sistema de Vendas funcionando! gg porraaaaaaaaaaaaaaaa !!!!!!";
    }

    @GetMapping("/teste")
    public String teste() {
        return "✅ Endpoint /teste funcionando corretamente!";
    }
}
