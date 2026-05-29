package br.com.cliente.controller;

import br.com.cliente.business.ClienteService;
import br.com.cliente.business.dto.in.ClienteRequestDTO;
import br.com.cliente.business.dto.in.LoginRequestDTO;
import br.com.cliente.business.dto.out.ClienteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> buscarTodosOsClientes(){
        return ResponseEntity.ok(service.buscarTodosOsClientes());
    }

    @GetMapping(params = "email")
    public ResponseEntity<ClienteResponseDTO> buscarPorEmail(@RequestParam String email){
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @PutMapping
    public ResponseEntity<ClienteResponseDTO> atualizarDados(@RequestBody ClienteRequestDTO requestDTO,
                                                             @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(service.altualizarDados(requestDTO, token));
    }

}
