package br.com.cliente.controller;

import br.com.cliente.business.ClienteService;
import br.com.cliente.business.dto.in.ClienteRequestDTO;
import br.com.cliente.business.dto.in.LoginRequestDTO;
import br.com.cliente.business.dto.out.ClienteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvarCliente(@RequestBody ClienteRequestDTO dto){
        return ResponseEntity.ok(service.salvarCliente(dto));

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto){
        return ResponseEntity.ok(service.login(dto));
    }

}
