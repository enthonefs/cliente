package br.com.cliente.business.dto.in;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String numero;
}
