package tom.patrzalek.javadevex2.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDTO {

    private Long id;

    private LocalDateTime date;

    private Integer longitude;

    private Integer latitude;

    private Boolean isLast;

    private Long deviceId;

}
