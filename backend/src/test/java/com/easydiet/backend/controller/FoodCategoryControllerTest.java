package com.easydiet.backend.controller;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.dto.FoodCategoryRequest;
import com.easydiet.backend.service.food.FoodCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodCategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class FoodCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FoodCategoryService service;

    @Test
    @DisplayName("Should return all categories")
    void shouldReturnAllCategories() throws Exception {
        var category = buildCategory();
        when(service.findAll()).thenReturn(List.of(category));

        mockMvc.perform(get("/api/food-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(category.getId().toString()))
                .andExpect(jsonPath("$[0].code").value("PROTEIN"))
                .andExpect(jsonPath("$[0].name").value("Protein"))
                .andExpect(jsonPath("$[0].active").value(true));
    }

    @Test
    @DisplayName("Should return category by id")
    void shouldReturnCategoryById() throws Exception {
        var category = buildCategory();
        when(service.findById(category.getId())).thenReturn(category);

        mockMvc.perform(get("/api/food-categories/{id}", category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId().toString()))
                .andExpect(jsonPath("$.name").value("Protein"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Should create category")
    void shouldCreateCategory() throws Exception {
        var request = FoodCategoryRequest.builder()
                .code(Category.PROTEIN)
                .name("Protein")
                .active(true)
                .build();
        var category = buildCategory();
        
        when(service.create(any(FoodCategoryRequest.class))).thenReturn(category);

        mockMvc.perform(post("/api/food-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Protein"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Should update category")
    void shouldUpdateCategory() throws Exception {
        var id = UUID.randomUUID();
        var request = FoodCategoryRequest.builder()
                .code(Category.PROTEIN)
                .name("Updated Protein")
                .active(true)
                .build();
        var category = FoodCategory.builder()
                .id(id)
                .code(Category.PROTEIN)
                .name("Updated Protein")
                .active(true)
                .build();

        when(service.update(eq(id), any(FoodCategoryRequest.class))).thenReturn(category);

        mockMvc.perform(put("/api/food-categories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Protein"));
    }

    @Test
    @DisplayName("Should delete category")
    void shouldDeleteCategory() throws Exception {
        var id = UUID.randomUUID();
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/api/food-categories/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400 when request is invalid")
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        var request = FoodCategoryRequest.builder()
                .name("") // Invalid: blank
                .build();

        mockMvc.perform(post("/api/food-categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    private FoodCategory buildCategory() {
        return FoodCategory.builder()
                .id(UUID.randomUUID())
                .code(Category.PROTEIN)
                .name("Protein")
                .active(true)
                .build();
    }
}
