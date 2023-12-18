package pl.gr.veterinaryapp.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import pl.gr.veterinaryapp.mapper.VisitMapper;
import pl.gr.veterinaryapp.model.dto.AvailableVisitDto;
import pl.gr.veterinaryapp.model.dto.VisitEditDto;
import pl.gr.veterinaryapp.model.dto.VisitRequestDto;
import pl.gr.veterinaryapp.model.dto.VisitResponseDto;
import pl.gr.veterinaryapp.service.VisitService;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("api/visits")
@RestController
public class VisitRestController {

    private final VisitService visitService;

    @DeleteMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVisit(@PathVariable long id) {
        visitService.deleteVisit(id);
    }

    @GetMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public VisitResponseDto getVisitById(@AuthenticationPrincipal User user, @PathVariable long id) {
        return visitService.getVisitById(user, id);
    }

    @GetMapping(path = "/available", produces = MediaTypes.HAL_JSON_VALUE)
    public List<AvailableVisitDto> getAvailableVisits(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") OffsetDateTime startDateTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") OffsetDateTime endDateTime,
            @RequestParam(required = false) List<Long> vetIds) {

        Set<Long> vetIdsSet;
        if (vetIds == null) {
            vetIdsSet = Collections.emptySet();
        } else {
            vetIdsSet = vetIds
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        return visitService.getAvailableVisits(startDateTime, endDateTime, vetIdsSet);
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public List<VisitResponseDto> getAllVisits(@AuthenticationPrincipal User user) {
        return visitService.getAllVisits(user);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public VisitResponseDto createVisit(@AuthenticationPrincipal User user,
                                        @RequestBody VisitRequestDto visitRequestDto) {
        return visitService.createVisit(user, visitRequestDto);
    }

    @PatchMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public VisitResponseDto finalizeVisit(@RequestBody VisitEditDto visitEditDto) {
        return visitService.finalizeVisit(visitEditDto);
    }
}
