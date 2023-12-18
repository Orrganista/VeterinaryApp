package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.gr.veterinaryapp.model.dto.VetRequestDto;
import pl.gr.veterinaryapp.model.dto.VetResponseDto;
import pl.gr.veterinaryapp.model.entity.Vet;
import pl.gr.veterinaryapp.service.VetService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/vets")
public class VetRestController {

    private final VetService vetService;

    @GetMapping("/{id}")
    public VetResponseDto getVetById(@PathVariable long id) {
        return vetService.getVetById(id);
    }

    @PostMapping
    public VetResponseDto addVet(@RequestBody VetRequestDto vetRequestDTO) {
        return vetService.createVet(vetRequestDTO);
    }

    @GetMapping
    public List<VetResponseDto> getAllVets() {
        return vetService.getAllVets();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVet(@PathVariable long id) {
        vetService.deleteVet(id);
    }
}
