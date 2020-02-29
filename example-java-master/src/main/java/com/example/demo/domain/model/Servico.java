package com.example.demo.domain.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "SERVICO", uniqueConstraints = @UniqueConstraint(name = "UC_SERVICO", columnNames = {"ID", "CLIENTE_ID"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Servico {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_SERVICO_ID")
    @SequenceGenerator(
            name = "SEQ_SERVICO_ID",
            allocationSize = 1,
            sequenceName = "SEQ_SERVICO_ID"
    )
    private Long id;

    @Setter
    @NotNull(message = "Cliente não pode ser null")
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;
}
