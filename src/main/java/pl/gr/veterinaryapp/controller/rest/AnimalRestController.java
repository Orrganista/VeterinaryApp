package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gr.veterinaryapp.mapper.AnimalMapper;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.dto.AnimalResponseDto;
import pl.gr.veterinaryapp.service.AnimalService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/animals")
public class AnimalRestController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    @GetMapping("/{id}")
    public AnimalResponseDto getAnimalById(@PathVariable long id) {
        return animalMapper.toAnimalResponseDto(animalService.getAnimalById(id));
    }

    @PostMapping
    public AnimalResponseDto createAnimal(@RequestBody AnimalRequestDto animalRequestDTO) {
        return animalMapper.toAnimalResponseDto(animalService.createAnimal(animalRequestDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable long id) {
        animalService.deleteAnimal(id);
    }

    @GetMapping
    public List<AnimalResponseDto> getAllAnimals() {
        return animalMapper.toAnimalResponseDtos(animalService.getAllAnimals());
    }
}
