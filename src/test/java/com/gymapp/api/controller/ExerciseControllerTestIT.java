package com.gymapp.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.GymappApplication;
import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.request.ExerciseUpdateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.domain.entity.Category;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.Difficulty;
import com.gymapp.infrastructure.persistence.CategoryJpaRepository;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.JsonTestUtils;
import com.gymapp.shared.dto.PageResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = GymappApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExerciseControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        exerciseJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
        insertTestData();
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

        JsonTestUtils.assertJsonIgnoringFields(expectedJson, actualJson, List.of("id", "description", "categories"));
    }

    @Test
    @DisplayName("Should return exercises filtered by difficulty")
    void shouldReturnExercisesByDifficulty() throws Exception {
        mockMvc.perform(get("/api/exercises")
                        .param("difficulty", "BEGINNER")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[*].difficulty").value(
                        everyItem(is("BEGINNER"))
                ));
    }

    @Test
    @DisplayName("Should return exercises filtered by category")
    void shouldReturnExercisesByCategory() throws Exception {
        Long chestId = categoryJpaRepository.findByNameIgnoreCase("Chest").get().getId();

        mockMvc.perform(get("/api/exercises")
                        .param("categoryIds", chestId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].categories[0].id").value(chestId));
    }

    @Test
    @DisplayName("Should return exercises filtered by category and difficulty")
    void shouldReturnExercisesByCategoryAndDifficulty() throws Exception {

        Long chestId = categoryJpaRepository.findByNameIgnoreCase("Chest").get().getId();

        mockMvc.perform(get("/api/exercises")
                        .param("categoryIds", chestId.toString())
                        .param("difficulty", "BEGINNER")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[*].categories[0].id").value(chestId.intValue()))
                .andExpect(jsonPath("$.content[*].difficulty").value(everyItem(is("BEGINNER"))));
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
    @DisplayName("Should return 400 when multiple incompatible filters are sent")
    void shouldReturnBadRequestWhenMultipleFiltersPresent() throws Exception {
        mockMvc.perform(get("/api/exercises")
                        .param("name", "press")
                        .param("categoryIds", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.detail").exists());
    }


    @Test
    @DisplayName("Should return the exercise correctly when querying by a valid ID")
    void shouldReturnExerciseByIdSuccessfully() throws Exception {

        Long benchPressTestId = findIdByName("Bench Press test");

        ExerciseResponse expectedResponse = ExerciseResponse.builder()
                .id(benchPressTestId)
                .name("Bench Press test")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("Classic chest exercise")
                .categories(List.of(
                        CategoryResponse.builder().id(1L).name("Chest").build()
                ))
                .build();

        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        MvcResult result = mockMvc.perform(get("/api/exercises/{exerciseId}", benchPressTestId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJson = result.getResponse().getContentAsString();

        JsonTestUtils.assertJsonIgnoringFields(expectedJson, actualJson, List.of("id", "categories[0].id"));
    }


    @Test
    @DisplayName("Should return 404 Not Found when the exercise does not exist")
    void shouldReturnNotFoundWhenExerciseDoesNotExist() throws Exception {
        Long nonExistentId = 9999L;

        mockMvc.perform(get("/api/exercises/{exerciseId}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should create a new exercise successfully and return 201 Created")
    void shouldCreateExerciseSuccessfully() throws Exception {

        Long chestId = categoryJpaRepository.findByNameIgnoreCase("Chest")
                .map(Category::getId)
                .orElseThrow(() -> new IllegalStateException("Chest category not found"));

        ExerciseCreateRequest request = ExerciseCreateRequest.builder()
                .name("testName")
                .description("testDescription")
                .difficulty(Difficulty.BEGINNER)
                .categoryIds(List.of(chestId))
                .build();

        MvcResult result = mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.description").value("testDescription"))
                .andExpect(jsonPath("$.difficulty").value("BEGINNER"))
                .andExpect(jsonPath("$.categories[0].name").value("Chest"))
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ExerciseResponse response = objectMapper.readValue(responseJson, ExerciseResponse.class);

        assertNotNull(response.getId());
        assertTrue(exerciseJpaRepository.existsById(response.getId()));
    }



    @Test
    @DisplayName("Should return 400 Bad Request when name is null")
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        ExerciseCreateRequest invalid = ExerciseCreateRequest.builder()
                .name(null)
                .description("desc")
                .difficulty(Difficulty.BEGINNER)
                .categoryIds(List.of(1L))
                .build();

        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.errors[0].field").value("name"));
    }


    @Test
    @DisplayName("Should return 400 when request body is malformed")
    void shouldReturnBadRequestWhenJsonMalformed() throws Exception {
        String malformedJson = "{ \"name\": \"Bench Press\", ";

        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.title").value("Invalid request body"));
    }

    @Test
    @DisplayName("Should return 400 when difficulty has an invalid enum value")
    void shouldReturnBadRequestWhenEnumInvalid() throws Exception {
        String invalidEnumJson = """
        {
            "name": "Press",
            "difficulty": "EASYMODE",
            "description": "desc",
            "categoryIds": [1]
        }
        """;

        mockMvc.perform(post("/api/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEnumJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid enum value"));
    }


    @Test
    @DisplayName("Should update exercise fields successfully when valid data is provided")
    void shouldUpdateExerciseSuccessfully() throws Exception {

        ExerciseUpdateRequest request = ExerciseUpdateRequest.builder()
                .name(JsonNullable.of("updatedName"))
                .description(JsonNullable.of("updatedDescription"))
                .difficulty(JsonNullable.of(Difficulty.ADVANCED))
                .build();

        Long idToUpdate = exerciseJpaRepository.findByName("Bench Press test").get().getId();

        mockMvc.perform(patch("/api/exercises/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idToUpdate))
                .andExpect(jsonPath("$.name").value("updatedName"))
                .andExpect(jsonPath("$.description").value("updatedDescription"))
                .andExpect(jsonPath("$.difficulty").value("ADVANCED"));
    }


    @Test
    @DisplayName("Should not update any field when request body is empty")
    void shouldNotUpdateAnythingWhenEmptyRequest() throws Exception {

        Long idToUpdate = findIdByName("Bench Press test");

        ExerciseUpdateRequest request = ExerciseUpdateRequest.builder().build();

        mockMvc.perform(patch("/api/exercises/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idToUpdate))
                .andExpect(jsonPath("$.name").value("Bench Press test"))
                .andExpect(jsonPath("$.description").value("Classic chest exercise"))
                .andExpect(jsonPath("$.difficulty").value("INTERMEDIATE"))
                .andExpect(jsonPath("$.categories[0].name").value("Chest"));
    }


    @Test
    @DisplayName("Should return 404 Not Found when trying to update a non-existent exercise")
    void shouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        Long nonExistentId = 1000L;

        ExerciseUpdateRequest request = ExerciseUpdateRequest.builder().build();

        mockMvc.perform(patch("/api/exercises/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value(String.format("The exercise with id %d does not exist",
                        nonExistentId)));
    }

    @Test
    @DisplayName("Should return 409 Conflict when updating exercise with an existing name")
    void shouldReturnConflictWhenUpdatingExerciseWithExistingName() throws Exception {

        Long existingId = findIdByName("Bench Press test");
        String duplicateName = "Squat test";

        ExerciseUpdateRequest updateRequest = ExerciseUpdateRequest.builder()
                .name(JsonNullable.of(duplicateName))
                .description(JsonNullable.undefined())
                .difficulty(JsonNullable.undefined())
                .build();

        mockMvc.perform(
                        patch("/api/exercises/{id}", existingId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateRequest))
                )
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("The exercise already exists"));
    }



    @Test
    @DisplayName("Should delete exercise successfully when ID exists")
    void shouldDeleteExerciseSuccessfully() throws Exception {

        Long idToDelete = findIdByName("Bench Press test");

        mockMvc.perform(delete("/api/exercises/{id}", idToDelete))
                .andExpect(status().isNoContent());

        assertFalse(exerciseJpaRepository.existsById(idToDelete));
    }


    @Test
    @DisplayName("Should return 404 Not Found when trying to delete a non-existent exercise")
    void shouldReturnNotFoundWhenDeletingNonExistingId() throws Exception {

        Long nonExistentId = 1000L;

        mockMvc.perform(delete("/api/exercises/{id}", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value(String.format("The exercise with id %d does not exist",
                        nonExistentId)));
    }

    private static Stream<Arguments> provideExercisePaginationCases() {
        List<ExerciseResponse> all = List.of(
                ExerciseResponse.builder().name("Bench Press test").difficulty(Difficulty.INTERMEDIATE).build(),
                ExerciseResponse.builder().name("Squat test").difficulty(Difficulty.INTERMEDIATE).build(),
                ExerciseResponse.builder().name("Deadlift test").difficulty(Difficulty.ADVANCED).build(),
                ExerciseResponse.builder().name("Bicep Curl test").difficulty(Difficulty.BEGINNER).build(),
                ExerciseResponse.builder().name("Push Up test").difficulty(Difficulty.BEGINNER).build()
        );

        List<ExerciseResponse> byNameAsc = List.of(
                all.get(0),
                all.get(3),
                all.get(2),
                all.get(4),
                all.get(1)
        );

        return Stream.of(
                Arguments.of("", 0, 2, "id,asc", all.subList(0, 2), 5L),
                Arguments.of("", 0, 5, "name,asc", byNameAsc, 5L)
        );
    }




    private void insertTestData() {
        Category chest = categoryJpaRepository.save(Category.builder().name("Chest").build());
        Category legs  = categoryJpaRepository.save(Category.builder().name("Legs").build());
        Category back  = categoryJpaRepository.save(Category.builder().name("Back").build());
        Category arms  = categoryJpaRepository.save(Category.builder().name("Arms").build());

        List<Exercise> entities = List.of(
                Exercise.builder()
                        .name("Bench Press test")
                        .difficulty(Difficulty.INTERMEDIATE)
                        .description("Classic chest exercise")
                        .categories(Set.of(chest))
                        .build(),
                Exercise.builder()
                        .name("Squat test")
                        .difficulty(Difficulty.INTERMEDIATE)
                        .description("Fundamental lower body exercise")
                        .categories(Set.of(legs))
                        .build(),
                Exercise.builder()
                        .name("Deadlift test")
                        .difficulty(Difficulty.ADVANCED)
                        .description("Full body compound movement")
                        .categories(Set.of(back))
                        .build(),
                Exercise.builder()
                        .name("Bicep Curl test")
                        .difficulty(Difficulty.BEGINNER)
                        .description("Isolation exercise for biceps")
                        .categories(Set.of(arms))
                        .build(),
                Exercise.builder()
                        .name("Push Up test")
                        .difficulty(Difficulty.BEGINNER)
                        .description("Bodyweight chest exercise for beginners")
                        .categories(Set.of(chest))
                        .build()
        );

        exerciseJpaRepository.saveAll(entities);
    }

    private Long findIdByName(String name) {
        return exerciseJpaRepository.findByName(name)
                .map(Exercise::getId)
                .orElseThrow(() -> new IllegalStateException(name + " not found in test data"));
    }

}
