package pl.gr.veterinaryapp.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.gr.veterinaryapp.config.WebSecurityConfig;
import pl.gr.veterinaryapp.jwt.JwtAuthenticationFilter;
import pl.gr.veterinaryapp.mapper.AnimalMapper;
import pl.gr.veterinaryapp.model.dto.AnimalRequestDto;
import pl.gr.veterinaryapp.model.dto.AnimalResponseDto;
import pl.gr.veterinaryapp.model.dto.ClientResponseDto;
import pl.gr.veterinaryapp.model.entity.Animal;
import pl.gr.veterinaryapp.service.AnimalService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AnimalRestController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = {WebSecurityConfigurerAdapter.class, JwtAuthenticationFilter.class})
        },
        excludeAutoConfiguration = {WebSecurityConfig.class})
class AnimalRestControllerTest {

    private static final long ID = 1L;
    private static final String SPECIES = "CAT";

    @MockBean
    private AnimalService animalService;

    @MockBean
    private AnimalMapper animalMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void createAnimal_CorrectData_Created() throws Exception {
        var animalRequest = new AnimalRequestDto();
        animalRequest.setSpecies(SPECIES);

        var animal = new Animal();

        var animalResponse = new AnimalResponseDto();
        animalResponse.setSpecies(animalRequest.getSpecies());

        when(animalService.createAnimal(any(AnimalRequestDto.class))).thenReturn(animal);
        when(animalMapper.toAnimalResponseDto(any(Animal.class))).thenReturn(animalResponse);

        mockMvc.perform(post("/api/animals")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(animalRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.species").value(SPECIES));

        verify(animalService).createAnimal(animalRequest);
        verify(animalMapper).toAnimalResponseDto(eq(animal));
    }

    @Test
    @WithMockUser
    void getAnimalById_CorrectData_Returned() throws Exception {
        var animal = new Animal();

        var animalResponse = new AnimalResponseDto();
        animalResponse.setSpecies(SPECIES);
        animalResponse.setId(ID);

        when(animalService.getAnimalById(anyLong())).thenReturn(animal);
        when(animalMapper.toAnimalResponseDto(any(Animal.class))).thenReturn(animalResponse);

        mockMvc.perform(get("/api/animals/{id}", ID)
                        .content(objectMapper.writeValueAsString(animal))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.species").value(SPECIES));

        verify(animalService).getAnimalById(1);
        verify(animalMapper).toAnimalResponseDto(eq(animal));
    }

    @Test
    @WithMockUser
    void getAllAnimals_CorrectData_Returned() throws Exception {
        var animals = List.of(new Animal(), new Animal());

        var animalResponse = List.of(createNewAnimalResponse("CAT"), createNewAnimalResponse("DOG"));

        when(animalService.getAllAnimals()).thenReturn(animals);
        when(animalMapper.toAnimalResponseDtos(anyList())).thenReturn(animalResponse);

        mockMvc.perform(get("/api/animals", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].species").value("CAT"))
                .andExpect(jsonPath("$.[1].species").value("DOG"));

        verify(animalService).getAllAnimals();
        verify(animalMapper).toAnimalResponseDtos(eq(animals));
    }

    private AnimalResponseDto createNewAnimalResponse(String species) {
        var animalResponse = new AnimalResponseDto();
        animalResponse.setSpecies(species);
        return animalResponse;
    }
}
