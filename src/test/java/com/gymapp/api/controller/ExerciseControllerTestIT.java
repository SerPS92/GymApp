package com.gymapp.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.GymappApplication;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.MuscleGroup;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.JsonTestUtils;
import com.gymapp.shared.dto.PageResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = GymappApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExerciseControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExerciseJpaRepository jpaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<Long> insertedIds = new ArrayList<>();

    @BeforeEach
    void setUp(){
        insertTestData();
    }

    @AfterEach
    void cleanUp(){
        if(!insertedIds.isEmpty()){
            jpaRepository.deleteAllById(insertedIds);
        }
    }

    @ParameterizedTest
    @MethodSource("provideExercisePaginationCases")
    @DisplayName("Should return exercises applying filters, sorting, and pagination")
    void getExercisesHappyPathTestIT(String filterName, int page, int size, String sort,
                                     List<ExerciseResponse> expectedContent, long totalElements) throws Exception {

        int totalPages = (totalElements == 0) ? 0 : (int) Math.ceil((double) totalElements / size);
        boolean first = (page == 0);
        boolean last = (totalElements == 0) || (page == totalPages - 1);
        boolean hasNext = (totalElements != 0) && (page < totalPages - 1);
        boolean hasPrev = page > 0;

        PageResponseDTO<ExerciseResponse> expected = PageResponseDTO.<ExerciseResponse>builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(first)
                .last(last)
                .hasNext(hasNext)
                .hasPrevious(hasPrev)
                .empty(expectedContent.isEmpty())
                .content(expectedContent)
                .build();

        MvcResult result = mockMvc.perform(
                get("/api/exercises")
                        .param("name", filterName)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String expectedJson = objectMapper.writeValueAsString(expected);
        String actualJson = result.getResponse().getContentAsString();

        JsonTestUtils.assertJsonIgnoringFields(expectedJson, actualJson, List.of("id"));
    }

    @Test
    @DisplayName("Should return an empty list when there are no matches for the exercise name")
    void shouldReturnEmptyListWhenExerciseNameDoesNotMatchAny() throws Exception {
        mockMvc.perform(get("/api/exercises")
                        .param("name", "NonExistentExercise"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.empty").value(true));
    }

    @Test
    @DisplayName("Should return 400 when both name and muscleGroup are sent simultaneously")
    void shouldReturnBadRequestWhenBothFiltersPresent() throws Exception {
        mockMvc.perform(get("/api/exercises")
                        .param("name", "press")
                        .param("muscleGroup", "CHEST"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.detail").exists());
    }



    private static Stream<Arguments> provideExercisePaginationCases() {
        List<ExerciseResponse> all = List.of(
                ExerciseResponse.builder().name("Bench Press test").muscleGroup(MuscleGroup.CHEST).description("Classic chest exercise").build(),
                ExerciseResponse.builder().name("Squat test").muscleGroup(MuscleGroup.LEGS).description("Fundamental lower body exercise").build(),
                ExerciseResponse.builder().name("Deadlift test").muscleGroup(MuscleGroup.BACK).description("Full body compound movement").build(),
                ExerciseResponse.builder().name("Bicep Curl test").muscleGroup(MuscleGroup.BICEPS).description("Isolation exercise for biceps").build()
        );

        List<ExerciseResponse> byNameAsc = List.of(all.get(0), all.get(3), all.get(2), all.get(1));
        List<ExerciseResponse> byIdAsc   = List.of(all.get(0), all.get(1), all.get(2), all.get(3));
        List<ExerciseResponse> byIdDesc  = List.of(all.get(3), all.get(2), all.get(1), all.get(0));

        return Stream.of(
                Arguments.of("", 0, 2, "id,desc",
                        List.of(byIdDesc.get(0), byIdDesc.get(1)), 4L),

                Arguments.of("Bench", 0, 5, "name,asc",
                        List.of(byNameAsc.get(0)), 1L),

                Arguments.of("", 1, 2, "id,asc",
                        List.of(byIdAsc.get(2), byIdAsc.get(3)), 4L),

                Arguments.of("", 0, 10, "name,asc",
                        byNameAsc, 4L),

                Arguments.of("", 3, 1, "name,asc",
                        List.of(byNameAsc.get(3)), 4L)
        );
    }


    private void insertTestData() {
        List<Exercise> entities = List.of(
                Exercise.builder()
                        .name("Bench Press test")
                        .muscleGroup(MuscleGroup.CHEST)
                        .description("Classic chest exercise")
                        .build(),
                Exercise.builder()
                        .name("Squat test")
                        .muscleGroup(MuscleGroup.LEGS)
                        .description("Fundamental lower body exercise")
                        .build(),
                Exercise.builder()
                        .name("Deadlift test")
                        .muscleGroup(MuscleGroup.BACK)
                        .description("Full body compound movement")
                        .build(),
                Exercise.builder()
                        .name("Bicep Curl test")
                        .muscleGroup(MuscleGroup.BICEPS)
                        .description("Isolation exercise for biceps")
                        .build()
        );

        jpaRepository.saveAll(entities);

        insertedIds.clear();
        for (Exercise e : entities){
            insertedIds.add(e.getId());
        }

    }


}
