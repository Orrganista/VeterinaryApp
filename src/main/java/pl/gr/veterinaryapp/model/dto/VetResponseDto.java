package pl.gr.veterinaryapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetTime;

@Data
public class VetResponseDto {

    private Long id;
    private String name;
    private String surname;
    private String photoUrl;
    private OffsetTime workStartTime;
    private OffsetTime workEndTime;
}
