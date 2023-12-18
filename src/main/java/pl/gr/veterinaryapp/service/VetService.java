package pl.gr.veterinaryapp.service;

import pl.gr.veterinaryapp.model.dto.VetRequestDto;
import pl.gr.veterinaryapp.model.dto.VetResponseDto;
import pl.gr.veterinaryapp.model.entity.Vet;

import java.util.List;

public interface VetService {

    VetResponseDto getVetById(long id);

    List<VetResponseDto> getAllVets();

    VetResponseDto createVet(VetRequestDto vetRequestDTO);

    void deleteVet(long id);
}
