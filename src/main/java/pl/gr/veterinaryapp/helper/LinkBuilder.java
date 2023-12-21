package pl.gr.veterinaryapp.helper;

import org.springframework.hateoas.Link;
import pl.gr.veterinaryapp.controller.rest.AnimalRestController;
import pl.gr.veterinaryapp.controller.rest.ClientRestController;
import pl.gr.veterinaryapp.controller.rest.PetRestController;
import pl.gr.veterinaryapp.controller.rest.VetRestController;
import pl.gr.veterinaryapp.model.dto.PetResponseDto;
import pl.gr.veterinaryapp.model.dto.VisitResponseDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LinkBuilder {

    public static void addLinks(PetResponseDto petResponseDto) {
        petResponseDto.add(createAnimalLink(petResponseDto.getAnimalId()),
                createClientLink(petResponseDto.getClientId()));
    }

    public static void addLinks(VisitResponseDto visitResponseDto) {
        visitResponseDto.add(createVetLink(visitResponseDto.getVetId()),
                createPetLink(visitResponseDto.getPetId()));
    }

    public static Link createVetLink(long id) {
        return linkTo(VetRestController.class)
                .slash(id)
                .withRel("vet");
    }

    private static Link createClientLink(long id) {
        return linkTo(methodOn(ClientRestController.class).getClientById(id))
                .withRel("client");
    }

    private static Link createAnimalLink(long id) {
        return linkTo(methodOn(AnimalRestController.class).getAnimalById(id))
                .withRel("animal");
    }

    private static Link createPetLink(long id) {
        return linkTo(PetRestController.class)
                .slash(id)
                .withRel("pet");
    }
}
