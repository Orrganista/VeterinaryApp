package pl.gr.veterinaryapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gr.veterinaryapp.exception.IncorrectDataException;
import pl.gr.veterinaryapp.exception.ResourceNotFoundException;
import pl.gr.veterinaryapp.mapper.ClientMapper;
import pl.gr.veterinaryapp.model.dto.ClientRequestDto;
import pl.gr.veterinaryapp.model.dto.ClientResponseDto;
import pl.gr.veterinaryapp.model.entity.Client;
import pl.gr.veterinaryapp.model.entity.VetAppUser;
import pl.gr.veterinaryapp.repository.ClientRepository;
import pl.gr.veterinaryapp.repository.UserRepository;
import pl.gr.veterinaryapp.service.ClientService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserRepository userRepository;

    @Override
    public ClientResponseDto getClientById(long id) {
        System.out.println("XXX");
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id."));
        return clientMapper.toClientResponseDto(client);
    }

    @Override
    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toClientResponseDtos(clients);
    }

    @Transactional
    @Override
    public ClientResponseDto createClient(ClientRequestDto clientRequestDTO) {
        if (clientRequestDTO.getSurname() == null || clientRequestDTO.getName() == null) {
            throw new IncorrectDataException("Name and Surname should not be null.");
        }

        VetAppUser user = userRepository.findByUsername(clientRequestDTO.getUsername())
                .orElse(null);

        Client client = clientMapper.toClient(clientRequestDTO);
        client.setUser(user);
        Client createdClient = clientRepository.save(client);
        return clientMapper.toClientResponseDto(createdClient);
    }

    @Transactional
    @Override
    public void deleteClient(long id) {
        Client result = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id."));
        clientRepository.delete(result);
    }
}
