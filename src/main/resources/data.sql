INSERT INTO users (id, name, email, password, role, created_at)
VALUES (1, 'Admin', 'admin@gymapp.com', 'dummy', 'ADMIN', CURRENT_DATE);


INSERT INTO categories (id, name) VALUES
  (1, 'Pecho'),
  (2, 'Espalda'),
  (3, 'Hombro'),
  (4, 'Tríceps'),
  (5, 'Bíceps'),
  (6, 'Cuádriceps'),
  (7, 'Pierna posterior / glúteo'),
  (8, 'Aductores / abductores'),
  (9, 'Gemelos'),
  (10, 'Abdomen / core'),
  (11, 'Cardio');


INSERT INTO exercises (id, name, description, difficulty, image_url) VALUES

(1, 'Press banca', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(2, 'Press banca inclinado', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(3, 'Press banca plano con barra', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(4, 'Press plano en máquina', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(5, 'Press banca máquina', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(6, 'Aperturas en polea', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(7, 'Press pecho máquina', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(8, 'Press inclinado en máquina', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(9, 'Press inclinado con mancuernas', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(10, 'Press inclinado multipower', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(11, 'Cruce polea desde abajo', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(12, 'Cruce cables desde arriba', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(13, 'Peck deck', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(14, 'Remo con barra', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(15, 'Bayesian fly', 'Ejercicio de pecho.', 'BEGINNER', '/images/default.jpg'),
(16, 'Jalón al pecho agarre neutro', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(17, 'Remo polea baja', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(18, 'Jalón al pecho', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(19, 'Jalón polea agarre supino', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(20, 'Jalón al pecho unilateral', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(21, 'Press militar con barra', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(22, 'Press militar con mancuernas', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(23, 'Jalón unilateral', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(24, 'Elevaciones laterales con mancuernas sentado', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(25, 'Remo en máquina', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(26, 'Remo polea baja unilateral', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(27, 'Remo con mancuerna', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(28, 'Pájaros', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(29, 'Remo con mancuerna pecho apoyado', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(30, 'Remo alto con mancuernas en banco', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(31, 'Curl con barra', 'Ejercicio de bíceps.', 'BEGINNER', '/images/default.jpg'),
(32, 'Curl bíceps mancuernas', 'Ejercicio de bíceps.', 'BEGINNER', '/images/default.jpg'),
(33, 'Pull over', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(34, 'Dominadas asistidas', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(35, 'Dominadas supinas', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(36, 'Dominadas lastradas', 'Ejercicio de espalda.', 'BEGINNER', '/images/default.jpg'),
(37, 'Press militar en máquina', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(38, 'Extensión tríceps en polea', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(39, 'Press francés', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(40, 'Patada de tríceps', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(41, 'Sentadilla trasera', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(42, 'Sentadilla frontal', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(43, 'Sentadilla búlgara', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(44, 'Prensa', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(45, 'Press hombro con mancuernas', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(46, 'Elevaciones laterales máquina', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(47, 'Peso muerto rumano', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(48, 'Extensión cuádriceps', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(49, 'Curl femoral tumbado', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(50, 'Elevaciones laterales polea', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(51, 'Elevaciones laterales polea baja', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(52, 'Puente de glúteos', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(53, 'Elevaciones laterales por detrás en polea', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(54, 'Abducción', 'Ejercicio de aductores / abductores.', 'BEGINNER', '/images/default.jpg'),
(55, 'Pájaros en polea', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(56, 'Peso muerto sumo', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(57, 'Step ups', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(58, 'Zancadas laterales', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(59, 'Peck deck inverso', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(60, 'Hombro posterior en máquina', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(61, 'Crunch abdominal', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(62, 'Posterior en polea alta', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(63, 'Face pull', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(64, 'Face pull sentado en polea baja', 'Ejercicio de hombro.', 'BEGINNER', '/images/default.jpg'),
(65, 'Plancha', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(66, 'Plancha lateral', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(67, 'Giros rusos con barra', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(68, 'Fondos para tríceps en máquina', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(69, 'Rodillo / ab wheel', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(70, 'Press Pallof', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(71, 'Cinta de correr', 'Ejercicio de cardio.', 'BEGINNER', '/images/default.jpg'),
(72, 'Fondos en paralelas lastrados', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(73, 'Fondos en banco con peso', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(74, 'Press francés en polea', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(75, 'Extensión tríceps unilateral en polea', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(76, 'Saltar a la comba', 'Ejercicio de cardio.', 'BEGINNER', '/images/default.jpg'),
(77, 'Burpees', 'Ejercicio de cardio.', 'BEGINNER', '/images/default.jpg'),
(78, 'Extensión tríceps cuerda', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(79, 'Extensiones por detrás de la cabeza en polea', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(80, 'Jalón tríceps unilateral', 'Ejercicio de tríceps.', 'BEGINNER', '/images/default.jpg'),
(81, 'Curl barra Z', 'Ejercicio de bíceps.', 'BEGINNER', '/images/default.jpg'),
(82, 'Curl bíceps banco inclinado', 'Ejercicio de bíceps.', 'BEGINNER', '/images/default.jpg'),
(83, 'Curl bíceps banco Scott', 'Ejercicio de bíceps.', 'BEGINNER', '/images/default.jpg'),
(84, 'Curl martillo', 'Ejercicio de bíceps.', 'BEGINNER', '/images/default.jpg'),
(85, 'Curl martillo en polea', 'Ejercicio de bíceps.', 'BEGINNER', '/images/default.jpg'),
(86, 'Curl Bayesian', 'Ejercicio de bíceps.', 'BEGINNER', '/images/default.jpg'),
(87, 'Sentadilla hack', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(88, 'Sentadilla multipower pies adelantados', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(89, 'Prensa vertical', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(90, 'Prensa unilateral', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(91, 'Prensa inclinada', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(92, 'Extensión cuádriceps unilateral', 'Ejercicio de cuádriceps.', 'BEGINNER', '/images/default.jpg'),
(93, 'Peso muerto rumano con barra', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(94, 'Peso muerto rumano con mancuernas', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(95, 'Peso muerto convencional', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(96, 'Curl femoral', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(97, 'Curl femoral sentado', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(98, 'Curl femoral unilateral', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(99, 'Curl femoral a una pierna', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(100, 'Hiperextensiones para femoral', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(101, 'Hiperextensiones para glúteo', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(102, 'Hip thrust', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(103, 'Pull through', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(104, 'Patada en polea baja', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(105, 'Zancadas caminando', 'Ejercicio de pierna posterior / glúteo.', 'BEGINNER', '/images/default.jpg'),
(106, 'Aducción en máquina', 'Ejercicio de aductores / abductores.', 'BEGINNER', '/images/default.jpg'),
(107, 'Aducción + abducción en superserie', 'Ejercicio de aductores / abductores.', 'BEGINNER', '/images/default.jpg'),
(108, 'Aducción en polea + abducción en superserie', 'Ejercicio de aductores / abductores.', 'BEGINNER', '/images/default.jpg'),
(109, 'Gemelo de pie', 'Ejercicio de gemelos.', 'BEGINNER', '/images/default.jpg'),
(110, 'Gemelo sentado', 'Ejercicio de gemelos.', 'BEGINNER', '/images/default.jpg'),
(111, 'Elevación de gemelos en prensa', 'Ejercicio de gemelos.', 'BEGINNER', '/images/default.jpg'),
(112, 'Plancha isométrica', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(113, 'Plancha con lastre', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(114, 'Crunch inverso', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(115, 'Crunch en polea alta', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(116, 'Crunch en máquina', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(117, 'Elevación de piernas', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg'),
(118, 'Hanging leg raises', 'Ejercicio de abdomen / core.', 'BEGINNER', '/images/default.jpg');


INSERT INTO exercise_categories (exercise_id, category_id) VALUES

(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 2),
(15, 1),
(16, 2),
(17, 2),
(18, 2),
(19, 2),
(20, 2),
(21, 3),
(22, 3),
(23, 2),
(24, 3),
(25, 2),
(26, 2),
(27, 2),
(28, 3),
(29, 2),
(30, 2),
(31, 5),
(32, 5),
(33, 2),
(34, 2),
(35, 2),
(36, 2),
(37, 3),
(38, 4),
(39, 4),
(40, 4),
(41, 6),
(42, 6),
(43, 6),
(44, 6),
(45, 3),
(46, 3),
(47, 7),
(48, 6),
(49, 7),
(50, 3),
(51, 3),
(52, 7),
(53, 3),
(54, 8),
(55, 3),
(56, 7),
(57, 6),
(58, 7),
(59, 3),
(60, 3),
(61, 10),
(62, 3),
(63, 3),
(64, 3),
(65, 10),
(66, 10),
(67, 10),
(68, 4),
(69, 10),
(70, 10),
(71, 11),
(72, 4),
(73, 4),
(74, 4),
(75, 4),
(76, 11),
(77, 11),
(78, 4),
(79, 4),
(80, 4),
(81, 5),
(82, 5),
(83, 5),
(84, 5),
(85, 5),
(86, 5),
(87, 6),
(88, 6),
(89, 6),
(90, 6),
(91, 6),
(92, 6),
(93, 7),
(94, 7),
(95, 7),
(96, 7),
(97, 7),
(98, 7),
(99, 7),
(100, 7),
(101, 7),
(102, 7),
(103, 7),
(104, 7),
(105, 7),
(106, 8),
(107, 8),
(108, 8),
(109, 9),
(110, 9),
(111, 9),
(112, 10),
(113, 10),
(114, 10),
(115, 10),
(116, 10),
(117, 10),
(118, 10);


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
ALTER TABLE exercises ALTER COLUMN id RESTART WITH 200;
ALTER TABLE categories ALTER COLUMN id RESTART WITH 100;