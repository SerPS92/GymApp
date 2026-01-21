INSERT INTO users (id, name, email, password, role, created_at)
VALUES (1, 'Admin', 'admin@gymapp.com', 'dummy', 'ADMIN', CURRENT_DATE);


INSERT INTO categories (id, name) VALUES
  (1, 'Pecho'),
  (2, 'Espalda'),
  (3, 'Hombros'),
  (4, 'Brazos'),
  (5, 'Piernas'),
  (6, 'Glúteos'),
  (7, 'Core / Abdominales'),
  (8, 'Sin equipamiento'),
  (9, 'Mancuernas'),
  (10, 'Barra'),
  (11, 'Máquinas'),
  (12, 'Poleas'),
  (13, 'Cardio');



INSERT INTO exercises (id, name, description, difficulty, image_url) VALUES

-- PECHO
(1, 'Press banca', 'Press de banca plano con barra.', 'INTERMEDIATE', '/images/default.jpg'),
(2, 'Press banca inclinado', 'Press inclinado con barra.', 'INTERMEDIATE', '/images/default.jpg'),
(3, 'Press banca declinado', 'Press declinado con barra.', 'INTERMEDIATE', '/images/default.jpg'),
(4, 'Press con mancuernas', 'Press plano con mancuernas.', 'BEGINNER', '/images/default.jpg'),
(5, 'Aperturas con mancuernas', 'Aperturas planas con mancuernas.', 'BEGINNER', '/images/default.jpg'),
(6, 'Aperturas en poleas', 'Aperturas en polea para el pecho.', 'BEGINNER', '/images/default.jpg'),
(7, 'Flexiones', 'Flexiones clásicas.', 'BEGINNER', '/images/default.jpg'),
(8, 'Flexiones inclinadas', 'Flexiones con apoyo elevado.', 'BEGINNER', '/images/default.jpg'),
(9, 'Flexiones declinadas', 'Flexiones con pies elevados.', 'INTERMEDIATE', '/images/default.jpg'),
(10, 'Pullover', 'Pullover con mancuerna.', 'INTERMEDIATE', '/images/default.jpg'),

-- ESPALDA
(11, 'Dominadas', 'Dominadas con peso corporal.', 'INTERMEDIATE', '/images/default.jpg'),
(12, 'Dominadas supinas', 'Dominadas con agarre supino.', 'INTERMEDIATE', '/images/default.jpg'),
(13, 'Dominadas asistidas', 'Dominadas con asistencia.', 'BEGINNER', '/images/default.jpg'),
(14, 'Remo con barra', 'Remo inclinado con barra.', 'INTERMEDIATE', '/images/default.jpg'),
(15, 'Remo pendlay', 'Remo pendlay desde el suelo.', 'ADVANCED', '/images/default.jpg'),
(16, 'Remo con mancuernas', 'Remo unilateral con mancuerna.', 'BEGINNER', '/images/default.jpg'),
(17, 'Remo en polea baja', 'Remo sentado en polea.', 'BEGINNER', '/images/default.jpg'),
(18, 'Jalón al pecho', 'Jalón en polea frontal.', 'BEGINNER', '/images/default.jpg'),
(19, 'Jalón agarre estrecho', 'Jalón en polea agarre cerrado.', 'BEGINNER', '/images/default.jpg'),
(20, 'Peso muerto', 'Peso muerto convencional.', 'ADVANCED', '/images/default.jpg'),

-- HOMBROS
(21, 'Press militar', 'Press de hombros con barra.', 'INTERMEDIATE', '/images/default.jpg'),
(22, 'Press hombros mancuernas', 'Press sentado con mancuernas.', 'BEGINNER', '/images/default.jpg'),
(23, 'Press Arnold', 'Press Arnold para hombros.', 'INTERMEDIATE', '/images/default.jpg'),
(24, 'Elevaciones laterales', 'Elevaciones laterales con mancuernas.', 'BEGINNER', '/images/default.jpg'),
(25, 'Elevaciones laterales en polea', 'Elevaciones laterales en polea.', 'BEGINNER', '/images/default.jpg'),
(26, 'Elevaciones frontales', 'Elevaciones frontales con mancuernas.', 'BEGINNER', '/images/default.jpg'),
(27, 'Elevaciones frontales con disco', 'Elevaciones frontales con disco.', 'BEGINNER', '/images/default.jpg'),
(28, 'Pájaros', 'Elevaciones posteriores con mancuernas.', 'BEGINNER', '/images/default.jpg'),
(29, 'Pájaros en poleas', 'Deltoide posterior en polea.', 'BEGINNER', '/images/default.jpg'),
(30, 'Remo al mentón', 'Remo vertical con barra.', 'INTERMEDIATE', '/images/default.jpg'),

-- BRAZOS
(31, 'Curl bíceps con barra', 'Curl de bíceps con barra.', 'BEGINNER', '/images/default.jpg'),
(32, 'Curl bíceps con mancuernas', 'Curl alterno con mancuernas.', 'BEGINNER', '/images/default.jpg'),
(33, 'Curl martillo', 'Curl martillo con mancuernas.', 'BEGINNER', '/images/default.jpg'),
(34, 'Curl concentrado', 'Curl concentrado sentado.', 'INTERMEDIATE', '/images/default.jpg'),
(35, 'Curl en polea', 'Curl de bíceps en polea.', 'BEGINNER', '/images/default.jpg'),
(36, 'Fondos en paralelas', 'Fondos enfocados en tríceps.', 'INTERMEDIATE', '/images/default.jpg'),
(37, 'Fondos asistidos', 'Fondos con asistencia.', 'BEGINNER', '/images/default.jpg'),
(38, 'Extensión tríceps polea', 'Extensión de tríceps en polea.', 'BEGINNER', '/images/default.jpg'),
(39, 'Press francés', 'Extensión de tríceps con barra.', 'INTERMEDIATE', '/images/default.jpg'),
(40, 'Patada de tríceps', 'Extensión de tríceps con mancuerna.', 'BEGINNER', '/images/default.jpg'),

-- PIERNAS
(41, 'Sentadilla', 'Sentadilla trasera con barra.', 'ADVANCED', '/images/default.jpg'),
(42, 'Sentadilla frontal', 'Sentadilla frontal con barra.', 'ADVANCED', '/images/default.jpg'),
(43, 'Sentadilla búlgara', 'Sentadilla unilateral.', 'INTERMEDIATE', '/images/default.jpg'),
(44, 'Prensa de piernas', 'Prensa en máquina.', 'BEGINNER', '/images/default.jpg'),
(45, 'Zancadas', 'Zancadas hacia delante.', 'BEGINNER', '/images/default.jpg'),
(46, 'Zancadas caminando', 'Zancadas alternas.', 'INTERMEDIATE', '/images/default.jpg'),
(47, 'Peso muerto rumano', 'Bisagra de cadera.', 'INTERMEDIATE', '/images/default.jpg'),
(48, 'Extensiones de cuádriceps', 'Extensión de piernas en máquina.', 'BEGINNER', '/images/default.jpg'),
(49, 'Curl femoral tumbado', 'Curl femoral en máquina.', 'BEGINNER', '/images/default.jpg'),
(50, 'Curl femoral sentado', 'Curl femoral sentado.', 'BEGINNER', '/images/default.jpg'),

-- GLÚTEOS
(51, 'Hip thrust', 'Hip thrust con barra.', 'INTERMEDIATE', '/images/default.jpg'),
(52, 'Puente de glúteos', 'Puente con peso corporal.', 'BEGINNER', '/images/default.jpg'),
(53, 'Patada de glúteo polea', 'Extensión de cadera en polea.', 'BEGINNER', '/images/default.jpg'),
(54, 'Abducciones de cadera', 'Abducción de cadera en máquina.', 'BEGINNER', '/images/default.jpg'),
(55, 'Sentadilla sumo', 'Sentadilla con apertura amplia.', 'BEGINNER', '/images/default.jpg'),
(56, 'Peso muerto sumo', 'Peso muerto con apertura amplia.', 'INTERMEDIATE', '/images/default.jpg'),
(57, 'Step ups', 'Subidas a banco.', 'BEGINNER', '/images/default.jpg'),
(58, 'Zancadas laterales', 'Zancadas hacia el lado.', 'BEGINNER', '/images/default.jpg'),
(59, 'Pull through', 'Extensión de cadera en polea.', 'BEGINNER', '/images/default.jpg'),
(60, 'Hip thrust unilateral', 'Hip thrust a una pierna.', 'INTERMEDIATE', '/images/default.jpg'),

-- CORE
(61, 'Crunch', 'Crunch abdominal clásico.', 'BEGINNER', '/images/default.jpg'),
(62, 'Crunch en polea', 'Crunch con resistencia.', 'BEGINNER', '/images/default.jpg'),
(63, 'Elevaciones de piernas', 'Elevaciones en el suelo.', 'BEGINNER', '/images/default.jpg'),
(64, 'Elevaciones colgado', 'Elevaciones colgado en barra.', 'INTERMEDIATE', '/images/default.jpg'),
(65, 'Plancha', 'Plancha isométrica.', 'BEGINNER', '/images/default.jpg'),
(66, 'Plancha lateral', 'Plancha lateral.', 'BEGINNER', '/images/default.jpg'),
(67, 'Russian twist', 'Rotaciones de tronco.', 'BEGINNER', '/images/default.jpg'),
(68, 'Mountain climbers', 'Escaladores dinámicos.', 'BEGINNER', '/images/default.jpg'),
(69, 'Ab wheel', 'Rueda abdominal.', 'ADVANCED', '/images/default.jpg'),
(70, 'Press Pallof', 'Ejercicio anti-rotacional.', 'INTERMEDIATE', '/images/default.jpg'),

-- CARDIO
(71, 'Cinta de correr', 'Carrera continua.', 'BEGINNER', '/images/default.jpg'),
(72, 'Bicicleta estática', 'Cardio en bicicleta.', 'BEGINNER', '/images/default.jpg'),
(73, 'Elíptica', 'Cardio de bajo impacto.', 'BEGINNER', '/images/default.jpg'),
(74, 'Remo ergómetro', 'Cardio en máquina de remo.', 'INTERMEDIATE', '/images/default.jpg'),
(75, 'Assault bike', 'Bicicleta de alta intensidad.', 'ADVANCED', '/images/default.jpg'),
(76, 'Saltar a la comba', 'Cardio con cuerda.', 'BEGINNER', '/images/default.jpg'),
(77, 'Burpees', 'Ejercicio metabólico.', 'ADVANCED', '/images/default.jpg'),
(78, 'Escaladora', 'Subida continua.', 'BEGINNER', '/images/default.jpg'),
(79, 'Sprint intervalado', 'Sprints por intervalos.', 'ADVANCED', '/images/default.jpg'),
(80, 'Jumping jacks', 'Saltos coordinativos.', 'BEGINNER', '/images/default.jpg');



INSERT INTO exercise_categories (exercise_id, category_id) VALUES

-- PECHO
(1, 1), (1, 10),                         -- Press banca
(2, 1), (2, 10),
(3, 1), (3, 10),
(4, 1), (4, 9),
(5, 1), (5, 9),
(6, 1), (6, 12),
(7, 1), (7, 8),
(8, 1), (8, 8),
(9, 1), (9, 8),
(10, 1), (10, 9),

-- ESPALDA
(11, 2), (11, 8),
(12, 2), (12, 8),
(13, 2), (13, 8),
(14, 2), (14, 10),
(15, 2), (15, 10),
(16, 2), (16, 9),
(17, 2), (17, 12),
(18, 2), (18, 12),
(19, 2), (19, 12),
(20, 2), (20, 10),

-- HOMBROS
(21, 3), (21, 10),
(22, 3), (22, 9),
(23, 3), (23, 9),
(24, 3), (24, 9),
(25, 3), (25, 12),
(26, 3), (26, 9),
(27, 3), (27, 10),
(28, 3), (28, 9),
(29, 3), (29, 12),
(30, 3), (30, 10),

-- BRAZOS
(31, 4), (31, 10),
(32, 4), (32, 9),
(33, 4), (33, 9),
(34, 4), (34, 9),
(35, 4), (35, 12),
(36, 4), (36, 8),
(37, 4), (37, 8),
(38, 4), (38, 12),
(39, 4), (39, 10),
(40, 4), (40, 9),

-- PIERNAS
(41, 5), (41, 10),
(42, 5), (42, 10),
(43, 5), (43, 8),
(44, 5), (44, 11),
(45, 5), (45, 8),
(46, 5), (46, 8),
(47, 5), (47, 10),
(48, 5), (48, 11),
(49, 5), (49, 11),
(50, 5), (50, 11),

-- GLÚTEOS
(51, 6), (51, 10),
(52, 6), (52, 8),
(53, 6), (53, 12),
(54, 6), (54, 11),
(55, 6), (55, 10),
(56, 6), (56, 10),
(57, 6), (57, 8),
(58, 6), (58, 8),
(59, 6), (59, 12),
(60, 6), (60, 10),

-- CORE
(61, 7), (61, 8),
(62, 7), (62, 12),
(63, 7), (63, 8),
(64, 7), (64, 8),
(65, 7), (65, 8),
(66, 7), (66, 8),
(67, 7), (67, 8),
(68, 7), (68, 8), (68, 13),
(69, 7),
(70, 7), (70, 12),

-- CARDIO
(71, 13), (71, 11),
(72, 13), (72, 11),
(73, 13), (73, 11),
(74, 13), (74, 11),
(75, 13), (75, 11),
(76, 13), (76, 8),
(77, 13), (77, 8),
(78, 13), (78, 11),
(79, 13),
(80, 13), (80, 8);



INSERT INTO programs (
    id,
    start_date,
    end_date,
    title,
    client_name,
    owner_id
) VALUES (
    1,
    '2026-01-07',
    '2026-02-07',
    'Rutina de fuerza torso/pierna',
    'Manuel López',
    1
);

INSERT INTO program_notes (program_id, note) VALUES
(1, 'Rutina orientada a fuerza e hipertrofia'),
(1, 'Descansar 60–90s entre series'),
(1, 'Priorizar técnica antes que carga'),
(1, 'Progresión semanal de peso'),
(1, 'Calentar antes de cada sesión'),
(1, 'Estirar al finalizar');

INSERT INTO program_day_labels (program_id, day_key, label) VALUES
(1, 'Day 1', 'Torso'),
(1, 'Day 2', 'Pierna'),
(1, 'Day 3', 'Torso'),
(1, 'Day 4', 'Pierna'),
(1, 'Day 5', 'Core');

INSERT INTO program_exercises (
    program_id,
    exercise_id,
    sets,
    reps,
    rest_time,
    workout_day,
    notes,
    intensity,
    tempo,
    position
) VALUES

-- DAY 1 · TORSO
(1, 1,  '4', '6-8',   '90', 'Day 1', '',        '1RM', '3-1-1-0', 1),
(1, 14, '4', '8-10',  '90', 'Day 1', '',        '1RM', '3-1-1-0', 2),
(1, 21, '3', '8-10',  '75', 'Day 1', '',        '1RM', '3-1-1-0', 3),
(1, 6,  '3', '10-12', '60', 'Day 1', '',        '1RM', '3-1-1-0', 4),
(1, 24, '3', '12-15', '60', 'Day 1', '',        '1RM', '2-1-2-0', 5),
(1, 31, '3', '10-12', '60', 'Day 1', '',        '1RM', '2-1-2-0', 6),
(1, 38, '3', '10-12', '60', 'Day 1', '',        '1RM', '2-1-2-0', 7),

-- DAY 2 · PIERNA
(1, 41, '4', '6-8',   '120','Day 2', '',        '1RM', '3-1-1-0', 1),
(1, 47, '3', '8-10',  '90', 'Day 2', '',        '1RM', '3-1-1-0', 2),
(1, 44, '3', '10-12', '75', 'Day 2', '',        '1RM', '2-1-2-0', 3),
(1, 48, '3', '12-15', '60', 'Day 2', '',        '1RM', '2-1-2-0', 4),
(1, 49, '3', '12-15', '60', 'Day 2', '',        '1RM', '2-1-2-0', 5),
(1, 52, '3', '12-15', '60', 'Day 2', '',        '1RM', '2-1-2-0', 6),

-- DAY 3 · TORSO
(1, 2,  '4', '8-10',  '90', 'Day 3', '',        'Max', '3-1-1-0', 1),
(1, 18, '4', '8-10',  '90', 'Day 3', '',        'Max', '3-1-1-0', 2),
(1, 22, '3', '10-12', '75', 'Day 3', '',        'Max', '3-1-1-0', 3),
(1, 28, '3', '12-15', '60', 'Day 3', '',        'Max', '2-1-2-0', 4),
(1, 32, '3', '10-12', '60', 'Day 3', '',        'Max', '2-1-2-0', 5),
(1, 40, '3', '10-12', '60', 'Day 3', '',        'Max', '2-1-2-0', 6),

-- DAY 4 · PIERNA
(1, 42, '4', '6-8',   '120','Day 4', '',        '1RM', '3-1-1-0', 1),
(1, 56, '3', '8-10',  '90', 'Day 4', '',        '1RM', '3-1-1-0', 2),
(1, 43, '3', '10-12', '75', 'Day 4', '',        '1RM', '2-1-2-0', 3),
(1, 54, '3', '12-15', '60', 'Day 4', '',        '1RM', '2-1-2-0', 4),
(1, 57, '3', '12-15', '60', 'Day 4', '',        '1RM', '2-1-2-0', 5),

-- DAY 5 · CORE
(1, 61, '3', '15-20', '60', 'Day 5', '',        '1RM', '2-1-2-0', 1),
(1, 65, '3', '45s',   '60', 'Day 5', '',        '1RM', '—',        2),
(1, 66, '3', '30s',   '60', 'Day 5', '',        '1RM', '—',        3),
(1, 70, '3', '12-15', '60', 'Day 5', '',        '1RM', '2-1-2-0', 4),
(1, 69, '3', '8-10',  '90', 'Day 5', '',        '1RM', '3-1-1-0', 5);

INSERT INTO programs (
    id,
    start_date,
    end_date,
    title,
    client_name,
    owner_id
) VALUES (
    2,
    '2026-01-10',
    '2026-02-10',
    'Full body + acondicionamiento',
    'Laura Martínez',
    1
);

INSERT INTO program_notes (program_id, note) VALUES
(2, 'Rutina orientada a fuerza general y resistencia'),
(2, 'Descansos cortos para mantener ritmo'),
(2, 'Priorizar técnica y control'),
(2, 'Añadir cardio suave en días de descanso'),
(2, 'Progresar reps antes que peso');

INSERT INTO program_day_labels (program_id, day_key, label) VALUES
(2, 'Day 1', 'Full body'),
(2, 'Day 2', 'Full body'),
(2, 'Day 3', 'Cardio + Core'),
(2, 'Day 4', 'Full body');

INSERT INTO program_exercises (
    program_id,
    exercise_id,
    sets,
    reps,
    rest_time,
    workout_day,
    notes,
    intensity,
    tempo,
    position
) VALUES

-- DAY 1 · FULL BODY
(2, 41, '3', '8-10',  '90', 'Day 1', '', '1RM', '3-1-1-0', 1),
(2, 1,  '3', '8-10',  '75', 'Day 1', '', '1RM', '3-1-1-0', 2),
(2, 18, '3', '10-12', '75', 'Day 1', '', '1RM', '3-1-1-0', 3),
(2, 24, '3', '12-15', '60', 'Day 1', '', '1RM', '2-1-2-0', 4),
(2, 61, '3', '15-20', '60', 'Day 1', '', '1RM', '2-1-2-0', 5),

-- DAY 2 · FULL BODY
(2, 47, '3', '8-10',  '90', 'Day 2', '', '1RM', '3-1-1-0', 1),
(2, 2,  '3', '8-10',  '75', 'Day 2', '', '1RM', '3-1-1-0', 2),
(2, 14, '3', '10-12', '75', 'Day 2', '', '1RM', '3-1-1-0', 3),
(2, 32, '3', '10-12', '60', 'Day 2', '', '1RM', '2-1-2-0', 4),
(2, 38, '3', '10-12', '60', 'Day 2', '', '1RM', '2-1-2-0', 5),

-- DAY 3 · CARDIO + CORE
(2, 71, '1', '20min', '50', 'Day 3', '', '',  '',        1),
(2, 76, '3', '1min',  '60', 'Day 3', '', '',  '',        2),
(2, 77, '3', '10-12',  '90', 'Day 3', '', '',  '',        3),
(2, 65, '3', '45s',    '60', 'Day 3', '', '',  '',        4),
(2, 66, '3', '30s',    '60', 'Day 3', '', '',  '',        5),

-- DAY 4 · FULL BODY
(2, 42, '3', '6-8',   '120','Day 4', '', '1RM', '3-1-1-0', 1),
(2, 22, '3', '8-10',  '75', 'Day 4', '', '1RM', '3-1-1-0', 2),
(2, 17, '3', '10-12', '75', 'Day 4', '', '1RM', '3-1-1-0', 3),
(2, 57, '3', '12-15', '60', 'Day 4', '', '1RM', '2-1-2-0', 4),
(2, 70, '3', '12-15', '60', 'Day 4', '', '1RM', '2-1-2-0', 5);

INSERT INTO programs (
    id,
    start_date,
    end_date,
    title,
    client_name,
    owner_id
) VALUES (
    3,
    '2026-01-15',
    '2026-02-15',
    'Hipertrofia clásica 5 días',
    'Carlos Gómez',
    1
);

INSERT INTO program_notes (program_id, note) VALUES
(3, 'Rutina orientada a hipertrofia'),
(3, 'Volumen moderado-alto'),
(3, 'Descansos entre 60 y 90 segundos'),
(3, 'Progresar repeticiones antes que carga'),
(3, 'Cuidar la técnica en cada ejercicio');

INSERT INTO program_day_labels (program_id, day_key, label) VALUES
(3, 'Day 1', 'Pecho'),
(3, 'Day 2', 'Espalda'),
(3, 'Day 3', 'Piernas'),
(3, 'Day 4', 'Hombros'),
(3, 'Day 5', 'Brazos');

INSERT INTO program_exercises (
    program_id,
    exercise_id,
    sets,
    reps,
    rest_time,
    workout_day,
    notes,
    intensity,
    tempo,
    position
) VALUES

-- DAY 1 · PECHO
(3, 1,  '4', '8-10',  '90', 'Day 1', '', '1RM', '3-1-1-0', 1),
(3, 2,  '3', '10-12', '75', 'Day 1', '', '1RM', '3-1-1-0', 2),
(3, 5,  '3', '12-15', '60', 'Day 1', '', '1RM', '2-1-2-0', 3),
(3, 6,  '3', '12-15', '60', 'Day 1', '', '1RM', '2-1-2-0', 4),

-- DAY 2 · ESPALDA
(3, 14, '4', '8-10',  '90', 'Day 2', '', '1RM', '3-1-1-0', 1),
(3, 18, '3', '10-12', '75', 'Day 2', '', '1RM', '3-1-1-0', 2),
(3, 16, '3', '10-12', '75', 'Day 2', '', '1RM', '3-1-1-0', 3),
(3, 20, '3', '6-8',   '120','Day 2', '', '1RM', '3-1-1-0', 4),

-- DAY 3 · PIERNAS
(3, 41, '4', '6-8',   '120','Day 3', '', '1RM', '3-1-1-0', 1),
(3, 44, '3', '10-12', '75', 'Day 3', '', '1RM', '2-1-2-0', 2),
(3, 47, '3', '8-10',  '90', 'Day 3', '', '1RM', '3-1-1-0', 3),
(3, 49, '3', '12-15', '60', 'Day 3', '', '1RM', '2-1-2-0', 4),

-- DAY 4 · HOMBROS
(3, 21, '4', '8-10',  '90', 'Day 4', '', '1RM', '3-1-1-0', 1),
(3, 24, '3', '12-15', '60', 'Day 4', '', '1RM', '2-1-2-0', 2),
(3, 28, '3', '12-15', '60', 'Day 4', '', '1RM', '2-1-2-0', 3),
(3, 30, '3', '10-12', '75', 'Day 4', '', '1RM', '3-1-1-0', 4),

-- DAY 5 · BRAZOS
(3, 31, '4', '10-12', '60', 'Day 5', '', '1RM', '2-1-2-0', 1),
(3, 32, '3', '12-15', '60', 'Day 5', '', '1RM', '2-1-2-0', 2),
(3, 39, '3', '10-12', '60', 'Day 5', '', '1RM', '2-1-2-0', 3),
(3, 38, '3', '12-15', '60', 'Day 5', '', '1RM', '2-1-2-0', 4);

INSERT INTO programs (
    id,
    start_date,
    end_date,
    title,
    client_name,
    owner_id
) VALUES (
    4,
    '2026-01-20',
    '2026-02-20',
    'Fuerza con accesorios',
    'Daniel Ruiz',
    1
);

INSERT INTO program_notes (program_id, note) VALUES
(4, 'Rutina orientada a fuerza general'),
(4, 'Priorizar ejercicios compuestos'),
(4, 'Descansos largos en básicos'),
(4, 'Accesorios con control');

INSERT INTO program_day_labels (program_id, day_key, label) VALUES
(4, 'Day 1', 'Torso'),
(4, 'Day 2', 'Pierna'),
(4, 'Day 3', 'Torso'),
(4, 'Day 4', 'Pierna');

INSERT INTO program_exercises (
    program_id,
    exercise_id,
    sets,
    reps,
    rest_time,
    workout_day,
    notes,
    intensity,
    tempo,
    position
) VALUES

-- DAY 1 · TORSO
(4, 1,  '5', '5-8',  '120', 'Day 1', '', '1RM', '3010', 1),
(4, 14, '5', '5-8',  '120', 'Day 1', '', '1RM', '3010', 2),
(4, 21, '3', '8-10', '90',  'Day 1', '', '1RM', '2110', 3),
(4, 24, '3', '12-15','60',  'Day 1', '', '1RM', '2110', 4),
(4, 38, '3', '10-12','60',  'Day 1', '', '1RM', '2110', 5),

-- DAY 2 · PIERNA
(4, 41, '5', '5-8',  '120', 'Day 2', '', '1RM', '3010', 1),
(4, 47, '4', '8-10', '90',  'Day 2', '', '1RM', '3010', 2),
(4, 44, '3', '10-12','75',  'Day 2', '', '1RM', '2110', 3),
(4, 49, '3', '12-15','60',  'Day 2', '', '1RM', '2110', 4),
(4, 52, '3', '12-15','60',  'Day 2', '', '1RM', '2110', 5),

-- DAY 3 · TORSO
(4, 2,  '4', '6-8',  '120', 'Day 3', '', '1RM', '3010', 1),
(4, 18, '4', '8-10', '90',  'Day 3', '', '1RM', '3010', 2),
(4, 22, '3', '10-12','75',  'Day 3', '', '1RM', '2110', 3),
(4, 32, '3', '12-15','60',  'Day 3', '', '1RM', '2110', 4),
(4, 40, '3', '12-15','60',  'Day 3', '', '1RM', '2110', 5),

-- DAY 4 · PIERNA
(4, 42, '4', '6-8',  '120', 'Day 4', '', '1RM', '3010', 1),
(4, 56, '4', '8-10', '90',  'Day 4', '', '1RM', '3010', 2),
(4, 43, '3', '10-12','75',  'Day 4', '', '1RM', '2110', 3),
(4, 54, '3', '12-15','60',  'Day 4', '', '1RM', '2110', 4),
(4, 57, '3', '12-15','60',  'Day 4', '', '1RM', '2110', 5);

INSERT INTO programs (
    id,
    start_date,
    end_date,
    title,
    client_name,
    owner_id
) VALUES (
    5,
    '2026-01-22',
    '2026-02-22',
    'Hipertrofia torso pierna volumen',
    'Miguel Ortega',
    1
);

INSERT INTO program_notes (program_id, note) VALUES
(5, 'Rutina de hipertrofia con alto volumen'),
(5, 'Descansos cortos en accesorios'),
(5, 'Mantener repeticiones objetivo'),
(5, 'Priorizar control del movimiento');

INSERT INTO program_day_labels (program_id, day_key, label) VALUES
(5, 'Day 1', 'Torso A'),
(5, 'Day 2', 'Pierna A'),
(5, 'Day 3', 'Torso B'),
(5, 'Day 4', 'Pierna B'),
(5, 'Day 5', 'Core');

INSERT INTO program_exercises (
    program_id,
    exercise_id,
    sets,
    reps,
    rest_time,
    workout_day,
    notes,
    intensity,
    tempo,
    position
) VALUES

-- DAY 1 · TORSO A
(5, 1,  '4', '8-10', '90',  'Day 1', '', '1RM', '3010', 1),
(5, 14, '4', '8-10', '90',  'Day 1', '', '1RM', '3010', 2),
(5, 21, '3', '10-12','75',  'Day 1', '', '1RM', '2110', 3),
(5, 6,  '3', '12-15','60',  'Day 1', '', '1RM', '2110', 4),
(5, 24, '3', '12-15','60',  'Day 1', '', '1RM', '2110', 5),
(5, 31, '3', '10-12','60',  'Day 1', '', '1RM', '2110', 6),
(5, 38, '3', '10-12','60',  'Day 1', '', '1RM', '2110', 7),

-- DAY 2 · PIERNA A
(5, 41, '4', '6-8',  '120', 'Day 2', '', '1RM', '3010', 1),
(5, 47, '4', '8-10', '90',  'Day 2', '', '1RM', '3010', 2),
(5, 44, '3', '10-12','75',  'Day 2', '', '1RM', '2110', 3),
(5, 48, '3', '12-15','60',  'Day 2', '', '1RM', '2110', 4),
(5, 49, '3', '12-15','60',  'Day 2', '', '1RM', '2110', 5),
(5, 52, '3', '12-15','60',  'Day 2', '', '1RM', '2110', 6),

-- DAY 3 · TORSO B
(5, 2,  '4', '8-10', '90',  'Day 3', '', '1RM', '3010', 1),
(5, 18, '4', '8-10', '90',  'Day 3', '', '1RM', '3010', 2),
(5, 22, '3', '10-12','75',  'Day 3', '', '1RM', '2110', 3),
(5, 28, '3', '12-15','60',  'Day 3', '', '1RM', '2110', 4),
(5, 32, '3', '10-12','60',  'Day 3', '', '1RM', '2110', 5),
(5, 40, '3', '10-12','60',  'Day 3', '', '1RM', '2110', 6),

-- DAY 4 · PIERNA B
(5, 42, '4', '6-8',  '120', 'Day 4', '', '1RM', '3010', 1),
(5, 56, '4', '8-10', '90',  'Day 4', '', '1RM', '3010', 2),
(5, 43, '3', '10-12','75',  'Day 4', '', '1RM', '2110', 3),
(5, 54, '3', '12-15','60',  'Day 4', '', '1RM', '2110', 4),
(5, 57, '3', '12-15','60',  'Day 4', '', '1RM', '2110', 5),
(5, 58, '3', '12-15','60',  'Day 4', '', '1RM', '2110', 6),

-- DAY 5 · CORE
(5, 61, '4', '15-20','60',  'Day 5', '', '1RM', '2110', 1),
(5, 65, '4', '30-45','60',  'Day 5', '', '1RM', '2110', 2),
(5, 66, '4', '30-45','60',  'Day 5', '', '1RM', '2110', 3),
(5, 67, '3', '12-15','60',  'Day 5', '', '1RM', '2110', 4),
(5, 70, '3', '12-15','60',  'Day 5', '', '1RM', '2110', 5);



ALTER TABLE programs ALTER COLUMN id RESTART WITH 10;
ALTER TABLE exercises ALTER COLUMN id RESTART WITH 100;
ALTER TABLE categories ALTER COLUMN id RESTART WITH 100;
