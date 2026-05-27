package br.com.cliente.business.converter;

import br.com.cliente.business.dto.in.ClienteRequestDTO;
import br.com.cliente.business.dto.out.ClienteResponseDTO;
import br.com.cliente.infrastructure.entitys.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter {

    public ClienteResponseDTO paraClienteResponseDTO(Cliente entity){
        return ClienteResponseDTO.builder()
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .numero(entity.getNumero())
                .id(entity.getId())
                .build();

    }

    public Cliente paraClienteEntity(ClienteRequestDTO dto){
        return Cliente.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .numero(dto.getNumero())
                .build();
    }

}
