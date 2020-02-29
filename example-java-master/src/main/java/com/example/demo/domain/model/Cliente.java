package com.example.demo.domain.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CLIENTE", uniqueConstraints = @UniqueConstraint(name = "UC_CLIENTE", columnNames = {"CPF"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Cliente {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLIENTE_ID")
    @SequenceGenerator(
            name = "SEQ_CLIENTE_ID",
            allocationSize = 1,
            sequenceName = "SEQ_CLIENTE_ID"
    )
    private Long id;

    @Setter
    @NotNull(message = "Cliente não pode ser nulo")
    @Column(name = "CPF")
    private String cpf;

}
