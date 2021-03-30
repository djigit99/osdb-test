package dev.andriip.testosdb.company;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @SequenceGenerator(
            name = "company_sequence",
            sequenceName = "company_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_sequence"
    )
    private int id;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Column(
            name = "founded",
            nullable = false
    )
    private Date founded;
}
