package pl.gr.veterinaryapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.gr.veterinaryapp.model.dto.VetRequestDto;
import pl.gr.veterinaryapp.model.dto.VetResponseDto;
import pl.gr.veterinaryapp.model.entity.Vet;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VetMapper {

    Vet toVet(VetRequestDto vetRequestDto);
    VetResponseDto toVetResponseDto(Vet vet);
    List<VetResponseDto> toVetResponseDtos(List<Vet> vets);
}
