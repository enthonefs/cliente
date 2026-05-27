package br.com.cliente.business.dto.out;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String numero;
}
