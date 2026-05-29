package br.com.cliente.business;

import br.com.cliente.business.converter.ClienteConverter;
import br.com.cliente.business.dto.in.ClienteRequestDTO;
import br.com.cliente.business.dto.in.LoginRequestDTO;
import br.com.cliente.business.dto.out.ClienteResponseDTO;
import br.com.cliente.infrastructure.entitys.Cliente;
import br.com.cliente.infrastructure.exceptions.ConflictException;
import br.com.cliente.infrastructure.exceptions.ResourceNotFoundException;
import br.com.cliente.infrastructure.repository.ClienteRepository;
import br.com.cliente.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteConverter clienteConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public ClienteResponseDTO salvarCliente(ClienteRequestDTO dto){
        existe(dto.getEmail());
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));

        Cliente cliente = clienteConverter.paraClienteEntity(dto);
        return clienteConverter.paraClienteResponseDTO(clienteRepository.save(cliente));

    }

    public void existe(String email){
        try {
            boolean existe = verificaEmailExistente(email);

            if (existe){
                throw new ConflictException("Email já existente! " + email);
            }
        }catch (ConflictException ex){
            throw new ConflictException("Email já existente! " + email);
        }
    }

    public boolean verificaEmailExistente(String email){
        return clienteRepository.existsByEmail(email);

    }

    public String login(LoginRequestDTO dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    public List<ClienteResponseDTO> buscarTodosOsClientes(){
        List<Cliente> listaClientes = clienteRepository.findAll();

        return clienteConverter.paraListaClienteResponseDTO(listaClientes);

    }

    public ClienteResponseDTO buscarPorEmail(String email){
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado: " + email));

        return clienteConverter.paraClienteResponseDTO(cliente);

    }

    public ClienteResponseDTO altualizarDados(ClienteRequestDTO requestDTO, String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Cliente clienteEntity = clienteRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado: " + email));

        Cliente clienteAtualizado = Cliente.builder()
                .id(clienteEntity.getId())
                .nome(requestDTO.getNome() != null ? requestDTO.getNome() : clienteEntity.getNome())
                .email(requestDTO.getEmail() != null ? requestDTO.getEmail() : clienteEntity.getEmail())
                .senha(passwordEncoder.encode(requestDTO.getSenha()) != null ?
                        passwordEncoder.encode(requestDTO.getSenha()) :
                        passwordEncoder.encode(clienteEntity.getSenha()))
                .numero(requestDTO.getNumero() != null ? requestDTO.getNumero() : clienteEntity.getNumero())
                .build();

        return clienteConverter.paraClienteResponseDTO(clienteRepository.save(clienteAtualizado));

    }

}
