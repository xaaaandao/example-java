package com.example.demo.domain.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "VISTORIA", uniqueConstraints = @UniqueConstraint(name = "UC_VISTORIA", columnNames = {"ID", "DATA", "VISTORIA_SERVICO"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Vistoria {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_VISTORIA_ID")
    @SequenceGenerator(
            name = "SEQ_VISTORIA_ID",
            allocationSize = 1,
            sequenceName = "SEQ_VISTORIA_ID"
    )
    private Long id;

    @Setter
    @NotNull(message = "Data não pode ser nulo")
    @Column(name = "DATA")
    private Date data;

    @ManyToOne
    @JoinColumn(name = "VISTORIA_SERVICO")
    private Servico servico;
}
