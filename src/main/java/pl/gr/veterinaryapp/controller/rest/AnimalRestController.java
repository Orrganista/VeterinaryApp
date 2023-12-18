package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.dto.AnimalResponseDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.service.AnimalService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/animals")
public class AnimalRestController {

    private final AnimalService animalService;

    @GetMapping("/{id}")
    public AnimalResponseDto getAnimalById(@PathVariable long id) {
        return animalService.getAnimalById(id);
    }

    @PostMapping
    public AnimalResponseDto createAnimal(@RequestBody AnimalRequestDto animalRequestDTO) {
        return animalService.createAnimal(animalRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable long id) {
        animalService.deleteAnimal(id);
    }

    @GetMapping
    public List<AnimalResponseDto> getAllAnimals() {
        return animalService.getAllAnimals();
    }
}
