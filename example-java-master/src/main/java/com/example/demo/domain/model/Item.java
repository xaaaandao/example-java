package com.example.demo.domain.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ITEM", uniqueConstraints = @UniqueConstraint(name = "UC_ITEM", columnNames = {"ID", "DESCRICAO", "STATUS", "SERVICO_ID"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Item {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ITEM_ID")
    @SequenceGenerator(
            name = "SEQ_ITEM_ID",
            allocationSize = 1,
            sequenceName = "SEQ_ITEM_ID"
    )
    private Long id;

    @Setter
    @NotNull(message = "Descrição não pode ser nulo")
    @JoinColumn(name = "DESCRICAO")
    private String descricao;

    @Setter
    @NotNull(message = "Status não pode ser nulo")
    @JoinColumn(name = "STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "SERVICO_ID")
    private Servico servico;

}
