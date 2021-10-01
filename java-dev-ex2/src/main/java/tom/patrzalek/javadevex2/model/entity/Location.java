package tom.patrzalek.javadevex2.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "location")
@Data
@SuperBuilder
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    @NotNull
    @Column(name = "latitude")
    private Integer latitude;

    @NotNull
    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "is_last")
    private Boolean isLast;

    @ManyToOne
    private Device device;
}
