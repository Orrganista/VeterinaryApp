package pl.gr.veterinaryapp.service;

import pl.gr.veterinaryapp.model.dto.ClientRequestDto;
import pl.gr.veterinaryapp.model.dto.ClientResponseDto;
import pl.gr.veterinaryapp.model.entity.Client;

import java.util.List;

public interface ClientService {

    ClientResponseDto getClientById(long id);

    ClientResponseDto createClient(ClientRequestDto clientRequestDTO);

    void deleteClient(long id);

    List<ClientResponseDto> getAllClients();
}
