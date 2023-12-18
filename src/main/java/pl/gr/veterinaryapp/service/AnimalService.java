package pl.gr.veterinaryapp.service;

import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.dto.AnimalResponseDto;
import pl.gr.veterinaryapp.model.entity.Animal;

import java.util.List;

public interface AnimalService {

    AnimalResponseDto getAnimalById(long id);

    AnimalResponseDto createAnimal(AnimalRequestDto animalRequestDTO);

    void deleteAnimal(long id);

    List<AnimalResponseDto> getAllAnimals();
}
