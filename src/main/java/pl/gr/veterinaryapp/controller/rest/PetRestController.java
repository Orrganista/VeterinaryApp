package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gr.veterinaryapp.mapper.PetMapper;
import pl.gr.veterinaryapp.model.dto.PetRequestDto;
import pl.gr.veterinaryapp.model.dto.PetResponseDto;
import pl.gr.veterinaryapp.service.PetService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.gr.veterinaryapp.helper.LinkBuilder.addLinks;

@RequiredArgsConstructor
@RequestMapping("api/pets")
@RestController
public class PetRestController {

    private final PetService petService;
    private final PetMapper petMapper;

    @DeleteMapping(path = "/{id}")
    public void deletePet(@PathVariable int id) {
        petService.deletePet(id);
    }

    @GetMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public PetResponseDto getPetById(@AuthenticationPrincipal User user, @PathVariable long id) {
        var pet = petMapper.toPetResponseDto(petService.getPetById(user, id));
        addLinks(pet);
        return pet;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public List<PetResponseDto> getAllPets(@AuthenticationPrincipal User user) {
        var pets = petMapper.toPetResponseDtos(petService.getAllPets(user));

        for (var pet : pets) {
            addLinks(pet);
            var link = linkTo(methodOn(PetRestController.class).getPetById(user, pet.getId()))
                    .withSelfRel();
            pet.add(link);
        }

        return pets;
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public PetResponseDto createPet(@AuthenticationPrincipal User user, @RequestBody PetRequestDto petRequestDto) {
        System.out.println(user);

        var pet = petMapper.toPetResponseDto(petService.createPet(user, petRequestDto));
        addLinks(pet);
        return pet;
    }
}
