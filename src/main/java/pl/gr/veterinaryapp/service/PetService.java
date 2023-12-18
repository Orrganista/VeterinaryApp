package pl.gr.veterinaryapp.service;

import org.springframework.security.core.userdetails.User;
import pl.gr.veterinaryapp.model.dto.PetRequestDto;
import pl.gr.veterinaryapp.model.dto.PetResponseDto;
import pl.gr.veterinaryapp.model.entity.Pet;

import java.util.List;

public interface PetService {

    PetResponseDto getPetById(User user, long id);

    PetResponseDto createPet(User user, PetRequestDto petRequestDTO);

    void deletePet(long id);

    List<PetResponseDto> getAllPets(User user);
}
