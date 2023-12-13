package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gr.veterinaryapp.mapper.ClientMapper;
import pl.gr.veterinaryapp.model.dto.ClientRequestDto;
import pl.gr.veterinaryapp.model.dto.ClientResponseDto;
import pl.gr.veterinaryapp.service.ClientService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/clients")
public class ClientRestController {

    private final ClientService clientService;
    private final ClientMapper mapper;

    @GetMapping("/{id}")
    public ClientResponseDto getClientById(@PathVariable long id) {
        return mapper.toClientResponseDto(clientService.getClientById(id));
    }

    @PostMapping
    public ClientResponseDto createClient(@RequestBody ClientRequestDto clientRequestDTO) {
        return mapper.toClientResponseDto(clientService.createClient(clientRequestDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
    }

    @GetMapping
    public List<ClientResponseDto> getAllClients() {
        return mapper.toClientResponseDtos(clientService.getAllClients());
    }
}
