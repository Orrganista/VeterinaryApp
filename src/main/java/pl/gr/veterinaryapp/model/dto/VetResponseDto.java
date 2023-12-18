package pl.gr.veterinaryapp.model.dto;

import lombok.Data;

import java.time.OffsetTime;

@Data
public class VetResponseDto {
    private String name;
    private String surname;
    private String photoUrl;
    private OffsetTime workStartTime;
    private OffsetTime workEndTime;
}
