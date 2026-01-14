package com.gymapp.application.service.program;

import com.gymapp.api.dto.program.request.ProgramFilterRequest;
import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.program.response.ProgramResponse;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.application.mapper.program.ProgramMapper;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.entity.Program;
import com.gymapp.domain.entity.ProgramExercise;
import com.gymapp.domain.entity.User;
import com.gymapp.infrastructure.persistence.ProgramJpaRepository;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.ErrorCode;
import com.gymapp.shared.error.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gymapp.shared.error.ErrorConstants.THE_PROGRAM_WITH_ID_D_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService{

    private final ProgramJpaRepository jpaRepository;
    private final ProgramMapper programMapper;

    @Override
    public PageResponseDTO<ProgramResponse> search(
            ProgramFilterRequest programFilterRequest,
            Pageable pageable
    ) {
        // TODO sustituir por usuario autenticado
        Long ownerId = 1L;

        Page<Program> page = findPrograms(programFilterRequest, pageable, ownerId);

        List<ProgramResponse> content = page.map(programMapper::toResponse).getContent();

        return PageResponseDTO.<ProgramResponse>builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .empty(page.isEmpty())
                .content(content)
                .build();
    }

    @Override
    public ProgramResponse getById(Long id) {

        // TODO sustituir por usuario autenticado
        Long ownerId = 1L;

        Program program = jpaRepository
                .findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() ->
                        new AppException(
                                HttpStatus.NOT_FOUND,
                                ErrorCode.NOT_FOUND,
                                THE_PROGRAM_WITH_ID_D_DOES_NOT_EXIST
                        )
                );
        return programMapper.toResponse(program);
    }

    @Override
    public ProgramResponse createProgram(ProgramRequest request) {
        // TODO sustituir por usuario autenticado
        Long ownerId = 1L;

        Program program = programMapper.toEntity(request);

        User owner = new User();
        owner.setId(ownerId);
        program.setOwner(owner);

        program.setProgramExercises(
                mapProgramExercises(request.getProgramExercises(), program)
        );

        Program savedProgram = jpaRepository.save(program);

        return programMapper.toResponse(savedProgram);
    }

    @Override
    public ProgramResponse updateProgram(Long programId, ProgramRequest request) {

        // TODO sustituir por usuario autenticado
        Long ownerId = 1L;

        Program program = jpaRepository
                .findByIdAndOwnerId(programId, ownerId)
                .orElseThrow(() ->
                        new AppException(
                                HttpStatus.NOT_FOUND,
                                ErrorCode.NOT_FOUND,
                                THE_PROGRAM_WITH_ID_D_DOES_NOT_EXIST
                        )
                );

        applyUpdates(program, request);

        Program updatedProgram = jpaRepository.save(program);

        return programMapper.toResponse(updatedProgram);
    }

    @Override
    public void deleteProgram(Long id) {

        // TODO sustituir por usuario autenticado
        Long ownerId = 1L;

        Program program = jpaRepository
                .findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() ->
                        new AppException(
                                HttpStatus.NOT_FOUND,
                                ErrorCode.NOT_FOUND,
                                THE_PROGRAM_WITH_ID_D_DOES_NOT_EXIST
                        )
                );

        jpaRepository.delete(program);
    }



    private Page<Program> findPrograms(
            ProgramFilterRequest filter,
            Pageable pageable,
            Long ownerId) {
        boolean hasTitle = filter.getTitle() != null && !filter.getTitle().isBlank();
        boolean hasClientName = filter.getClientName() != null && !filter.getClientName().isBlank();

        if (hasTitle && hasClientName) {
            return jpaRepository
                    .findByOwnerIdAndTitleContainingIgnoreCaseAndClientNameContainingIgnoreCase(
                            ownerId,
                            filter.getTitle(),
                            filter.getClientName(),
                            pageable
                    );
        }
        if (hasTitle) {
            return jpaRepository
                    .findByOwnerIdAndTitleContainingIgnoreCase(
                            ownerId,
                            filter.getTitle(),
                            pageable
                    );
        }
        if (hasClientName) {
            return jpaRepository
                    .findByOwnerIdAndClientNameContainingIgnoreCase(
                            ownerId,
                            filter.getClientName(),
                            pageable
                    );
        }
        return jpaRepository.findByOwnerId(ownerId, pageable);
    }

    private List<ProgramExercise> mapProgramExercises(
            List<ProgramExerciseRequest> requests,
            Program program) {

        if (requests == null || requests.isEmpty()) {
            return List.of();
        }

        return requests.stream()
                .map(req -> {
                    ProgramExercise pe = new ProgramExercise();

                    pe.setSets(req.getSets());
                    pe.setReps(req.getReps());
                    pe.setRestTime(req.getRestTime());
                    pe.setDay(req.getDay());
                    pe.setNotes(req.getNotes());
                    pe.setIntensity(req.getIntensity());
                    pe.setTempo(req.getTempo());
                    pe.setPosition(req.getPosition());

                    pe.setProgram(program);

                    Exercise exercise = new Exercise();
                    exercise.setId(req.getExerciseId());
                    pe.setExercise(exercise);

                    return pe;
                })
                .toList();
    }

    private void applyUpdates(Program program, ProgramRequest request) {

        program.setStartDate(request.getStartDate());
        program.setEndDate(request.getEndDate());


        if (request.getTitle() != null) {
            program.setTitle(request.getTitle());
        }

        if (request.getClientName() != null) {
            program.setClientName(request.getClientName());
        }

        if (request.getNotes() != null) {
            program.setNotes(request.getNotes());
        }

        if (request.getDayLabels() != null) {
            program.setDayLabels(request.getDayLabels());
        }

        if (request.getProgramExercises() != null) {
            program.getProgramExercises().clear();
            program.getProgramExercises().addAll(
                    mapProgramExercises(request.getProgramExercises(), program)
            );
        }
    }


}
