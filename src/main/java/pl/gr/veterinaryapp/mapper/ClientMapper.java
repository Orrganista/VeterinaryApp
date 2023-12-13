package pl.gr.veterinaryapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.gr.veterinaryapp.model.dto.ClientRequestDto;
import pl.gr.veterinaryapp.model.dto.ClientResponseDto;
import pl.gr.veterinaryapp.model.entity.Client;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    Client toClient(ClientRequestDto clientRequestDto);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "user.username", target = "username")
    })
    ClientResponseDto toClientResponseDto(Client client);

    List<ClientResponseDto> toClientResponseDtos(Collection<Client> clients);
}
