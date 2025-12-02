INSERT INTO categories (id, name) VALUES
  (1, 'Chest'),
  (2, 'Back'),
  (3, 'Legs'),
  (4, 'Shoulders'),
  (5, 'Arms'),
  (6, 'Core'),
  (7, 'Cardio'),
  (8, 'Full Body'),
  (9, 'Home Workout'),
  (10, 'Stretching'),
  (11, 'Mobility'),
  (12, 'HIIT'),
  (13, 'Powerlifting'),
  (14, 'Bodybuilding'),
  (15, 'Calisthenics'),
  (16, 'CrossFit'),
  (17, 'Glutes'),
  (18, 'Quads'),
  (19, 'Hamstrings'),
  (20, 'Calves'),
  (21, 'Lower Back'),
  (22, 'Neck'),
  (23, 'Forearms'),
  (24, 'Strength'),
  (25, 'Endurance'),
  (26, 'Warm-Up'),
  (27, 'Rehabilitation'),
  (28, 'Explosive Training'),
  (29, 'Plyometrics'),
  (30, 'Balance & Stability');



INSERT INTO exercises (id, name, description, difficulty, image_url) VALUES
  (1, 'Bench Press', 'Flat barbell bench press focusing on pectorals.', 'INTERMEDIATE', '/images/default.jpg'),
  (2, 'Incline Bench Press', 'Incline barbell press emphasizing upper chest.', 'INTERMEDIATE', '/images/default.jpg'),
  (3, 'Dumbbell Fly', 'Flat dumbbell fly for chest isolation.', 'BEGINNER', '/images/default.jpg'),
  (4, 'Push Up', 'Bodyweight horizontal press.', 'BEGINNER', '/images/default.jpg'),

  (5, 'Deadlift', 'Conventional barbell deadlift.', 'ADVANCED', '/images/default.jpg'),
  (6, 'Barbell Row', 'Bent-over barbell row targeting lats.', 'INTERMEDIATE', '/images/default.jpg'),
  (7, 'Pull Up', 'Bodyweight vertical pull.', 'INTERMEDIATE', '/images/default.jpg'),
  (8, 'Lat Pulldown', 'Cable pulldown targeting lats.', 'BEGINNER', '/images/default.jpg'),

  (9, 'Overhead Press', 'Standing barbell shoulder press.', 'INTERMEDIATE', '/images/default.jpg'),
  (10, 'Lateral Raise', 'Dumbbell side raise for medial delts.', 'BEGINNER', '/images/default.jpg'),
  (11, 'Face Pull', 'Cable face pull for rear delts.', 'BEGINNER', '/images/default.jpg'),

  (12, 'Biceps Curl', 'Standing dumbbell curl.', 'BEGINNER', '/images/default.jpg'),
  (13, 'Preacher Curl', 'EZ-bar preacher curl.', 'INTERMEDIATE', '/images/default.jpg'),

  (14, 'Triceps Dip', 'Parallel bar dip targeting triceps.', 'INTERMEDIATE', '/images/default.jpg'),
  (15, 'Triceps Pushdown', 'Cable pushdown with rope attachment.', 'BEGINNER', '/images/default.jpg'),

  (16, 'Wrist Curl', 'Seated barbell wrist curl.', 'BEGINNER', '/images/default.jpg'),
  (17, 'Reverse Wrist Curl', 'Seated barbell reverse wrist curl.', 'BEGINNER', '/images/default.jpg'),

  (18, 'Crunch', 'Floor crunch for abdominal flexion.', 'BEGINNER', '/images/default.jpg'),
  (19, 'Hanging Knee Raise', 'Knees-to-chest on pull-up bar.', 'INTERMEDIATE', '/images/default.jpg'),

  (20, 'Plank', 'Isometric hold for trunk stability.', 'BEGINNER', '/images/default.jpg'),
  (21, 'Pallof Press', 'Anti-rotation cable press.', 'INTERMEDIATE', '/images/default.jpg'),

  (22, 'Hip Thrust', 'Barbell hip thrust for glute hypertrophy.', 'INTERMEDIATE', '/images/default.jpg'),
  (23, 'Glute Bridge', 'Bodyweight glute bridge.', 'BEGINNER', '/images/default.jpg'),

  (24, 'Back Squat', 'Barbell squat to full depth.', 'ADVANCED', '/images/default.jpg'),
  (25, 'Walking Lunge', 'Alternating forward lunges.', 'INTERMEDIATE', '/images/default.jpg'),
  (26, 'Leg Press', 'Machine compound press.', 'BEGINNER', '/images/default.jpg'),
  (27, 'Romanian Deadlift', 'Hip hinge targeting hamstrings.', 'INTERMEDIATE', '/images/default.jpg'),

  (28, 'Calf Raise', 'Standing calf raise.', 'BEGINNER', '/images/default.jpg'),
  (29, 'Seated Calf Raise', 'Machine seated calf raise.', 'BEGINNER', '/images/default.jpg'),

  (30, 'Neck Flexion', 'Weighted neck flexion with harness.', 'BEGINNER', '/images/default.jpg'),
  (31, 'Neck Extension', 'Weighted neck extension with harness.', 'BEGINNER', '/images/default.jpg'),

  (32, 'Barbell Shrug', 'Barbell shrug for upper traps.', 'INTERMEDIATE', '/images/default.jpg'),

  (33, 'Burpee', 'Bodyweight full-body conditioning movement.', 'ADVANCED', '/images/default.jpg'),

  (34, 'Jump Rope', 'Steady jump rope cardio.', 'BEGINNER', '/images/default.jpg'),
  (35, 'Rowing Machine', 'Cardio on indoor rower.', 'INTERMEDIATE', '/images/default.jpg'),

  (36, 'Mountain Climbers', 'Dynamic core and cardio exercise.', 'BEGINNER', '/images/default.jpg'),
  (37, 'Kettlebell Swing', 'Full-body movement for strength and cardio.', 'INTERMEDIATE', '/images/default.jpg'),
  (38, 'Yoga Flow', 'Mobility and stretching sequence.', 'BEGINNER', '/images/default.jpg');



INSERT INTO exercise_categories (exercise_id, category_id) VALUES
  (1, 1), (2, 1), (3, 1), (4, 1),        -- Chest
  (5, 2), (6, 2), (7, 2), (8, 2),        -- Back
  (9, 4), (10, 4), (11, 4),              -- Shoulders
  (12, 5), (13, 5),                      -- Arms (biceps)
  (14, 5), (15, 5),                      -- Arms (triceps)
  (16, 5), (17, 5),                      -- Forearms
  (18, 6), (19, 6), (20, 6), (21, 6),    -- Core/Abs
  (22, 3), (23, 3), (24, 3), (25, 3), (26, 3), (27, 3), (28, 3), (29, 3),  -- Legs
  (30, 8), (31, 8),                      -- Neck/Full body accessory
  (32, 8), (33, 8),                      -- Full body
  (34, 7), (35, 7),                      -- Cardio

  (36, 6), (36, 7), (36, 9),             -- Mountain Climbers (Core + Cardio + Home Workout)
  (37, 2), (37, 3), (37, 8),             -- Kettlebell Swing (Back + Legs + Full Body)
  (38, 10), (38, 11);                    -- Yoga Flow (Stretching + Mobility)

ALTER TABLE exercises ALTER COLUMN id RESTART WITH 100;
ALTER TABLE categories ALTER COLUMN id RESTART WITH 100;
