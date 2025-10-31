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
  (12, 'HIIT');


INSERT INTO exercises (id, name, description, difficulty) VALUES
  (1, 'Bench Press', 'Flat barbell bench press focusing on pectorals.', 'INTERMEDIATE'),
  (2, 'Incline Bench Press', 'Incline barbell press emphasizing upper chest.', 'INTERMEDIATE'),
  (3, 'Dumbbell Fly', 'Flat dumbbell fly for chest isolation.', 'BEGINNER'),
  (4, 'Push Up', 'Bodyweight horizontal press.', 'BEGINNER'),

  (5, 'Deadlift', 'Conventional barbell deadlift.', 'ADVANCED'),
  (6, 'Barbell Row', 'Bent-over barbell row targeting lats.', 'INTERMEDIATE'),
  (7, 'Pull Up', 'Bodyweight vertical pull.', 'INTERMEDIATE'),
  (8, 'Lat Pulldown', 'Cable pulldown targeting lats.', 'BEGINNER'),

  (9, 'Overhead Press', 'Standing barbell shoulder press.', 'INTERMEDIATE'),
  (10, 'Lateral Raise', 'Dumbbell side raise for medial delts.', 'BEGINNER'),
  (11, 'Face Pull', 'Cable face pull for rear delts.', 'BEGINNER'),

  (12, 'Biceps Curl', 'Standing dumbbell curl.', 'BEGINNER'),
  (13, 'Preacher Curl', 'EZ-bar preacher curl.', 'INTERMEDIATE'),

  (14, 'Triceps Dip', 'Parallel bar dip targeting triceps.', 'INTERMEDIATE'),
  (15, 'Triceps Pushdown', 'Cable pushdown with rope attachment.', 'BEGINNER'),

  (16, 'Wrist Curl', 'Seated barbell wrist curl.', 'BEGINNER'),
  (17, 'Reverse Wrist Curl', 'Seated barbell reverse wrist curl.', 'BEGINNER'),

  (18, 'Crunch', 'Floor crunch for abdominal flexion.', 'BEGINNER'),
  (19, 'Hanging Knee Raise', 'Knees-to-chest on pull-up bar.', 'INTERMEDIATE'),

  (20, 'Plank', 'Isometric hold for trunk stability.', 'BEGINNER'),
  (21, 'Pallof Press', 'Anti-rotation cable press.', 'INTERMEDIATE'),

  (22, 'Hip Thrust', 'Barbell hip thrust for glute hypertrophy.', 'INTERMEDIATE'),
  (23, 'Glute Bridge', 'Bodyweight glute bridge.', 'BEGINNER'),

  (24, 'Back Squat', 'Barbell squat to full depth.', 'ADVANCED'),
  (25, 'Walking Lunge', 'Alternating forward lunges.', 'INTERMEDIATE'),
  (26, 'Leg Press', 'Machine compound press.', 'BEGINNER'),
  (27, 'Romanian Deadlift', 'Hip hinge targeting hamstrings.', 'INTERMEDIATE'),

  (28, 'Calf Raise', 'Standing calf raise.', 'BEGINNER'),
  (29, 'Seated Calf Raise', 'Machine seated calf raise.', 'BEGINNER'),

  (30, 'Neck Flexion', 'Weighted neck flexion with harness.', 'BEGINNER'),
  (31, 'Neck Extension', 'Weighted neck extension with harness.', 'BEGINNER'),

  (32, 'Barbell Shrug', 'Barbell shrug for upper traps.', 'INTERMEDIATE'),

  (33, 'Burpee', 'Bodyweight full-body conditioning movement.', 'ADVANCED'),

  (34, 'Jump Rope', 'Steady jump rope cardio.', 'BEGINNER'),
  (35, 'Rowing Machine', 'Cardio on indoor rower.', 'INTERMEDIATE'),

  (36, 'Mountain Climbers', 'Dynamic core and cardio exercise.', 'BEGINNER'),
  (37, 'Kettlebell Swing', 'Full-body movement for strength and cardio.', 'INTERMEDIATE'),
  (38, 'Yoga Flow', 'Mobility and stretching sequence.', 'BEGINNER');


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
