package com.gymapp.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.GymappApplication;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.MuscleGroup;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = GymappApplication.class)
@AutoConfigureMockMvc
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



    private void insertTestData() {
        List<Exercise> entities = List.of(
                Exercise.builder()
                        .id(1L)
                        .name("Bench Press")
                        .muscleGroup(MuscleGroup.CHEST)
                        .description("Classic chest exercise")
                        .build(),
                Exercise.builder()
                        .id(2L)
                        .name("Squat")
                        .muscleGroup(MuscleGroup.LEGS)
                        .description("Fundamental lower body exercise")
                        .build(),
                Exercise.builder()
                        .id(3L)
                        .name("Deadlift")
                        .muscleGroup(MuscleGroup.BACK)
                        .description("Full body compound movement")
                        .build(),
                Exercise.builder()
                        .id(4L)
                        .name("Bicep Curl")
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
