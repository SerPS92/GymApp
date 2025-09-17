package com.gymapp.shared.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorConstants {

    public static final String THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST = "The exercise with id %d does not exist";
    public static final String CANNOT_DELETE_THE_EXERCISE_IS_REFERENCED = "Cannot delete: the exercise is referenced.";
    public static final String THE_EXERCISE_ALREADY_EXISTS = "The exercise already exists";
    public static final String SEND_EXACTLY_ONE_FILTER_NAME_OR_MUSCLE_GROUP = "Send exactly one filter: name OR muscleGroup.";
}
