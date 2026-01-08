package com.easydiet.backend.controller;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.domain.diet.DietType;
import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.dto.DietTypeRequest;
import com.easydiet.backend.service.DietTypeService;
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

@WebMvcTest(DietTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class DietTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DietTypeService service;

    @Test
    @DisplayName("Should return all diet types")
    void shouldReturnAllDietTypes() throws Exception {
        var dietType = buildDietType();
        when(service.findAll()).thenReturn(List.of(dietType));

        mockMvc.perform(get("/api/diet-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(dietType.getId().toString()))
                .andExpect(jsonPath("$[0].code").value("KETO"))
                .andExpect(jsonPath("$[0].name").value("Ketogenic"));
    }

    @Test
    @DisplayName("Should return diet type by id")
    void shouldReturnDietTypeById() throws Exception {
        var dietType = buildDietType();
        when(service.findById(UUID.fromString(dietType.getId()))).thenReturn(dietType);

        mockMvc.perform(get("/api/diet-types/{id}", dietType.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dietType.getId().toString()))
                .andExpect(jsonPath("$.name").value("Ketogenic"));
    }

    @Test
    @DisplayName("Should create diet type")
    void shouldCreateDietType() throws Exception {
        var request = DietTypeRequest.builder()
                .code(DietCode.KETO)
                .name("Ketogenic")
                .active(true)
                .build();
        var dietType = buildDietType();
        
        when(service.create(any(DietTypeRequest.class))).thenReturn(dietType);

        mockMvc.perform(post("/api/diet-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ketogenic"));
    }

    @Test
    @DisplayName("Should update diet type")
    void shouldUpdateDietType() throws Exception {
        var id = UUID.randomUUID();
        var request = DietTypeRequest.builder()
                .code(DietCode.KETO)
                .name("Updated Keto")
                .active(true)
                .build();
        var dietType = DietType.builder()
                .id(id.toString())
                .code(DietCode.KETO)
                .name("Updated Keto")
                .active(true)
                .build();

        when(service.update(eq(id), any(DietTypeRequest.class))).thenReturn(dietType);

        mockMvc.perform(put("/api/diet-types/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Keto"));
    }

    @Test
    @DisplayName("Should delete diet type")
    void shouldDeleteDietType() throws Exception {
        var id = UUID.randomUUID();
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/api/diet-types/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 400 when request is invalid")
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        var request = DietTypeRequest.builder()
                .name("") // Invalid: blank
                .build();

        mockMvc.perform(post("/api/diet-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    private DietType buildDietType() {
        return DietType.builder()
                .id(UUID.randomUUID().toString())
                .code(DietCode.KETO)
                .name("Ketogenic")
                .active(true)
                .build();
    }
}
