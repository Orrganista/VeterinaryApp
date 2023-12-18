package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gr.veterinaryapp.controller.rest.AnimalRestController;
import pl.gr.veterinaryapp.controller.rest.ClientRestController;
import pl.gr.veterinaryapp.controller.rest.PetRestController;
import pl.gr.veterinaryapp.exception.IncorrectDataException;
import pl.gr.veterinaryapp.exception.ResourceNotFoundException;
import pl.gr.veterinaryapp.mapper.PetMapper;
import pl.gr.veterinaryapp.model.dto.PetRequestDto;
import pl.gr.veterinaryapp.model.dto.PetResponseDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.model.entity.Client;
import pl.gr.veterinaryapp.model.entity.Pet;
import pl.gr.veterinaryapp.repository.AnimalRepository;
import pl.gr.veterinaryapp.repository.ClientRepository;
import pl.gr.veterinaryapp.repository.PetRepository;
import pl.gr.veterinaryapp.service.PetService;
import pl.gr.veterinaryapp.validator.UserValidator;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ClientRepository clientRepository;
    private final AnimalRepository animalRepository;
    private final PetMapper petMapper;

    @Override
    public List<PetResponseDto> getAllPets(User user) {
        List<Pet> pets = petRepository.findAll()
                .stream()
                .filter(pet -> UserValidator.isUserAuthorized(user, pet.getClient()))
                .collect(Collectors.toList());

        List<PetResponseDto> petResponseDtos = petMapper.toPetResponseDtos(pets);
        for (var dto : petResponseDtos) {
            addLinks(dto);
            var link = linkTo(methodOn(PetRestController.class).getPet(user, dto.getId()))
                    .withSelfRel();
            dto.add(link);
        }

        return petResponseDtos;
    }

    @Override
    public PetResponseDto getPetById(User user, long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id."));

        if (!UserValidator.isUserAuthorized(user, pet.getClient())) {
            throw new ResourceNotFoundException("Wrong id.");
        }
        PetResponseDto petResponseDto = petMapper.toPetResponseDto(pet);
        addLinks(petResponseDto);
        return petResponseDto;
    }

    @Transactional
    @Override
    public PetResponseDto createPet(User user, PetRequestDto petRequestDto) {
        if (petRequestDto.getName() == null) {
            throw new IncorrectDataException("Name cannot be null.");
        }

        if (petRequestDto.getBirthDate() == null) {
            throw new IncorrectDataException("Birth date cannot be null.");
        }

        Animal animal = animalRepository.findById(petRequestDto.getAnimalId())
                .orElseThrow(() -> new IncorrectDataException("Wrong animal id."));
        Client client = clientRepository.findById(petRequestDto.getClientId())
                .orElseThrow(() -> new IncorrectDataException("Wrong client id."));

        if (!UserValidator.isUserAuthorized(user, client)) {
            throw new ResourceNotFoundException("User don't have access to this pet");
        }

        Pet newPet = Pet.builder()
                .name(petRequestDto.getName())
                .birthDate(petRequestDto.getBirthDate())
                .animal(animal)
                .client(client)
                .build();

        Pet createdPet = petRepository.save(newPet);
        PetResponseDto petResponseDto = petMapper.toPetResponseDto(createdPet);
        addLinks(petResponseDto);

        return petResponseDto;
    }

    @Transactional
    @Override
    public void deletePet(long id) {
        Pet result = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id."));
        petRepository.delete(result);
    }

    private Link createClientLink(long id) {
        return linkTo(methodOn(ClientRestController.class).getClientById(id))
                .withRel("client");
    }

    private Link createAnimalLink(long id) {
        return linkTo(methodOn(AnimalRestController.class).getAnimalById(id))
                .withRel("animal");
    }

    private void addLinks(PetResponseDto petResponseDto) {
        petResponseDto.add(createAnimalLink(petResponseDto.getAnimalId()),
                createClientLink(petResponseDto.getClientId()));
    }
}
