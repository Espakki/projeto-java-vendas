package com.example.sistema_vendas_api.controller;


import com.example.sistema_vendas_api.model.Cliente;
import com.example.sistema_vendas_api.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    // listar
    @GetMapping
    public List<Cliente> findAll(){
        return clienteService.listarClientes();
    }
    // cadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> save(@Valid @RequestBody Cliente cliente){
        Cliente salvo = clienteService.salvarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
    // busca por id
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id){
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // atualizar por id
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Integer id, @Valid @RequestBody Cliente clienteAtualizado) {
        Cliente atualizado = clienteService.atualizarCliente(id, clienteAtualizado);
        return ResponseEntity.ok(atualizado);
    }
    // deletar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clienteService.deletarCliente(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (msg1, msg2) -> msg1));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
        return ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage(),
                        (msg1, msg2) -> msg1));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleEntityNotFound(EntityNotFoundException ex) {
        return Map.of("message", ex.getMessage());
    }
}
