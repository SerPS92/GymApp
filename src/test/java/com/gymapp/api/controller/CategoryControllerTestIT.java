package com.gymapp.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.GymappApplication;
import com.gymapp.api.dto.category.request.CategoryCreateRequest;
import com.gymapp.api.dto.category.request.CategoryUpdateRequest;
import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.domain.entity.Category;
import com.gymapp.infrastructure.persistence.CategoryJpaRepository;
import com.gymapp.shared.JsonTestUtils;
import com.gymapp.shared.dto.PageResponseDTO;
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

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = GymappApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryJpaRepository jpaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        jpaRepository.deleteAll();
        insertTestData();
    }

    @ParameterizedTest
    @MethodSource("provideCategoryPaginationCases")
    @DisplayName("Should return categories applying sorting and pagination")
    void getCategoriesHappyPathTestIT(int page, int size, String sort,
                                      List<CategoryResponse> expectedContent, long totalElements) throws Exception {

        int totalPages = (totalElements == 0) ? 0 : (int) Math.ceil((double) totalElements / size);
        boolean first = (page == 0);
        boolean last = (totalElements == 0) || (page == totalPages - 1);
        boolean hasNext = (totalElements != 0) && (page < totalPages - 1);
        boolean hasPrev = page > 0;

        PageResponseDTO<CategoryResponse> expected = PageResponseDTO.<CategoryResponse>builder()
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
                        get("/api/categories")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .param("sort", sort)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = objectMapper.writeValueAsString(expected);
        String actualJson = result.getResponse().getContentAsString();

        JsonTestUtils.assertJsonIgnoringFields(expectedJson, actualJson, List.of("id"));
    }

    @Test
    @DisplayName("Should return category by valid ID")
    void shouldReturnCategoryById() throws Exception {
        Long id = findIdByName("Back");

        CategoryResponse expected = CategoryResponse.builder()
                .id(id)
                .name("Back")
                .build();

        String expectedJson = objectMapper.writeValueAsString(expected);

        MvcResult result = mockMvc.perform(get("/api/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualJson = result.getResponse().getContentAsString();
        assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Should return 404 when category does not exist")
    void shouldReturnNotFoundWhenCategoryDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/categories/{id}", 9999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should create new category successfully and return 201 Created")
    void shouldCreateCategorySuccessfully() throws Exception {
        CategoryCreateRequest request = CategoryCreateRequest.builder()
                .name("Crossfit")
                .build();

        MvcResult result = mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Crossfit"))
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        CategoryResponse response = objectMapper.readValue(responseJson, CategoryResponse.class);

        assertTrue(jpaRepository.existsById(response.getId()));
    }

    @Test
    @DisplayName("Should return 409 Conflict when creating a duplicate category")
    void shouldReturnConflictWhenCreatingDuplicateCategory() throws Exception {
        CategoryCreateRequest request = CategoryCreateRequest.builder()
                .name("Back")
                .build();

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("CONFLICT"))
                .andExpect(jsonPath("$.detail").value("The category already exists"));
    }

    @Test
    @DisplayName("Should update category successfully")
    void shouldUpdateCategorySuccessfully() throws Exception {
        Long id = findIdByName("Arms");

        CategoryUpdateRequest request = CategoryUpdateRequest.builder()
                .name("Arms Updated")
                .build();

        mockMvc.perform(patch("/api/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Arms Updated"));
    }

    @Test
    @DisplayName("Should return 404 when updating non-existent category")
    void shouldReturnNotFoundWhenUpdatingNonExistentCategory() throws Exception {
        CategoryUpdateRequest request = CategoryUpdateRequest.builder()
                .name("Nonexistent")
                .build();

        mockMvc.perform(patch("/api/categories/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("Should return 409 when updating category to an existing name")
    void shouldReturnConflictWhenUpdatingToExistingName() throws Exception {
        Long id = findIdByName("Chest");

        CategoryUpdateRequest request = CategoryUpdateRequest.builder()
                .name("Back") // already exists
                .build();

        mockMvc.perform(patch("/api/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    @DisplayName("Should delete category successfully")
    void shouldDeleteCategorySuccessfully() throws Exception {
        Long id = findIdByName("Core");

        mockMvc.perform(delete("/api/categories/{id}", id))
                .andExpect(status().isNoContent());

        assertFalse(jpaRepository.existsById(id));
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existent category")
    void shouldReturnNotFoundWhenDeletingNonExistentCategory() throws Exception {
        mockMvc.perform(delete("/api/categories/{id}", 9999L))
                .andExpect(status().isNotFound());
    }

    private static Stream<Arguments> provideCategoryPaginationCases() {
        List<CategoryResponse> all = List.of(
                CategoryResponse.builder().name("Arms").build(),
                CategoryResponse.builder().name("Back").build(),
                CategoryResponse.builder().name("Chest").build(),
                CategoryResponse.builder().name("Core").build()
        );

        List<CategoryResponse> byNameAsc = List.of(all.get(0), all.get(1), all.get(2), all.get(3));
        List<CategoryResponse> byNameDesc = List.of(all.get(3), all.get(2), all.get(1), all.get(0));

        return Stream.of(
                Arguments.of(0, 2, "id,asc", List.of(byNameAsc.get(0), byNameAsc.get(1)), 4L),
                Arguments.of(0, 4, "name,desc", byNameDesc, 4L),
                Arguments.of(1, 2, "name,asc", List.of(byNameAsc.get(2), byNameAsc.get(3)), 4L)
        );
    }

    private void insertTestData() {
        List<Category> entities = List.of(
                Category.builder().name("Arms").build(),
                Category.builder().name("Back").build(),
                Category.builder().name("Chest").build(),
                Category.builder().name("Core").build()
        );

        jpaRepository.saveAll(entities);
    }

    private Long findIdByName(String name) {
        return jpaRepository.findByNameIgnoreCase(name)
                .map(Category::getId)
                .orElseThrow(() -> new IllegalStateException(name + " not found in test data"));
    }
}

