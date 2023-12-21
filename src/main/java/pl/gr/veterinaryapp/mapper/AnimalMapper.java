package pl.gr.veterinaryapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.dto.AnimalResponseDto;
import pl.gr.veterinaryapp.model.entity.Animal;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalMapper {

    Animal toAnimal(AnimalRequestDto animalRequestDto);

    AnimalResponseDto toAnimalResponseDto(Animal animal);

    List<AnimalResponseDto> toAnimalResponseDtos(List<Animal> animals);
}
