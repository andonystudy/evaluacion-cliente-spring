package com.example.evaluacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/clients")
    public List<Client> findAll(){
        return clientService.findAll();
    }

    @PostMapping("/clients")
    public ResponseEntity<?> create(@RequestBody Client client){
        Map<String, Object> resp = new HashMap<>();
        List<String> errors = new ArrayList<>();

        if(client.getDni().length() != 8) errors.add("El dni debe tener 8 dígitos.");
        if(client.getDni().isEmpty()) errors.add("El dni no puede estar vacío.");
        if(client.getName().isEmpty()) errors.add("El nombre no puede estar vacío.");
        if(client.getLastName().isEmpty()) errors.add("El apellido no puede estar vacío.");

        try {
            if (errors.isEmpty() || errors == null){
                Client clientCreated = clientService.create(client);
                resp.put("msg","El cliente ".concat(client.getName()).concat(" se ha registrado con éxito"));
                resp.put("Cliente", clientCreated);
            }else{
                resp.put("msg","Se encontraron campos vacíos.");
                resp.put("Errores",errors);
                return new ResponseEntity<>(resp, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (DataAccessException ex){
            resp.put("msg","Error en la BD");
            resp.put("error","Error: ".concat(ex.getMessage()).concat(" - ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Client client){
        Map<String, Object> resp = new HashMap<>();
        List<String> errors = new ArrayList<>();

        if(client.getDni().length() != 8) errors.add("El dni debe tener 8 dígitos.");
        if(client.getDni().isEmpty()) errors.add("El dni no puede estar vacío.");
        if(client.getName().isEmpty()) errors.add("El nombre no puede estar vacío.");
        if(client.getLastName().isEmpty()) errors.add("El apellido no puede estar vacío.");

        try {
            if (clientService.findById(id) == null){
                resp.put("msg","El id ".concat(id.toString()).concat(" no está registrado en nuestra DB."));
                return new ResponseEntity<>(resp, HttpStatus.NOT_ACCEPTABLE);
            }else if(errors.isEmpty() || errors == null) {
                Client clientUpdated = clientService.update(client, id);
                resp.put("msg","El cliente ".concat(clientUpdated.getName()).concat(" se ha actualizado con éxito"));
                resp.put("Cliente Actualizado", clientUpdated);
            }else{
                resp.put("msg","Se encontraron campos vacíos.");
                resp.put("errors",errors);
                return new ResponseEntity<>(resp, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (DataAccessException ex){
            resp.put("msg","Error en la BD");
            resp.put("error","Error: ".concat(ex.getMessage()).concat(" - ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
