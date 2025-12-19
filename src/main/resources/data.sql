INSERT INTO categories (id, name) VALUES
  (1, 'Pecho'),
  (2, 'Espalda'),
  (3, 'Piernas'),
  (4, 'Hombros'),
  (5, 'Brazos'),
  (6, 'Core'),
  (7, 'Cardio'),
  (8, 'Cuerpo completo'),
  (9, 'Entrenamiento en casa'),
  (10, 'Estiramientos'),
  (11, 'Movilidad'),
  (12, 'HIIT'),
  (13, 'Powerlifting'),
  (14, 'Culturismo'),
  (15, 'Calistenia'),
  (16, 'CrossFit'),
  (17, 'Glúteos'),
  (18, 'Cuádriceps'),
  (19, 'Isquiotibiales'),
  (20, 'Gemelos'),
  (21, 'Zona lumbar'),
  (22, 'Cuello'),
  (23, 'Antebrazos'),
  (24, 'Fuerza'),
  (25, 'Resistencia'),
  (26, 'Calentamiento'),
  (27, 'Rehabilitación'),
  (28, 'Entrenamiento explosivo'),
  (29, 'Pliometría'),
  (30, 'Equilibrio y estabilidad');


INSERT INTO exercises (id, name, description, difficulty, image_url) VALUES
  (1, 'Press de banca', 'Press de banca plano con barra enfocado en el pecho.', 'INTERMEDIATE', '/images/default.jpg'),
  (2, 'Press inclinado', 'Press inclinado con barra enfatizando el pecho superior.', 'INTERMEDIATE', '/images/default.jpg'),
  (3, 'Aperturas con mancuernas', 'Aperturas planas con mancuernas para aislar el pecho.', 'BEGINNER', '/images/default.jpg'),
  (4, 'Flexiones', 'Empuje horizontal con el peso corporal.', 'BEGINNER', '/images/default.jpg'),

  (5, 'Peso muerto', 'Peso muerto convencional con barra.', 'ADVANCED', '/images/default.jpg'),
  (6, 'Remo con barra', 'Remo inclinado con barra trabajando la espalda.', 'INTERMEDIATE', '/images/default.jpg'),
  (7, 'Dominadas', 'Tracción vertical con peso corporal.', 'INTERMEDIATE', '/images/default.jpg'),
  (8, 'Jalón al pecho', 'Jalón en polea enfocado en dorsales.', 'BEGINNER', '/images/default.jpg'),

  (9, 'Press militar', 'Press de hombros de pie con barra.', 'INTERMEDIATE', '/images/default.jpg'),
  (10, 'Elevaciones laterales', 'Elevaciones laterales con mancuernas.', 'BEGINNER', '/images/default.jpg'),
  (11, 'Face pull', 'Face pull en polea para deltoides posteriores.', 'BEGINNER', '/images/default.jpg'),

  (12, 'Curl de bíceps', 'Curl de bíceps de pie con mancuernas.', 'BEGINNER', '/images/default.jpg'),
  (13, 'Curl predicador', 'Curl predicador con barra EZ.', 'INTERMEDIATE', '/images/default.jpg'),

  (14, 'Fondos en paralelas', 'Fondos en paralelas enfocados en tríceps.', 'INTERMEDIATE', '/images/default.jpg'),
  (15, 'Extensión de tríceps en polea', 'Extensión de tríceps en polea con cuerda.', 'BEGINNER', '/images/default.jpg'),

  (16, 'Curl de muñeca', 'Curl de muñeca sentado con barra.', 'BEGINNER', '/images/default.jpg'),
  (17, 'Curl inverso de muñeca', 'Curl inverso de muñeca sentado con barra.', 'BEGINNER', '/images/default.jpg'),

  (18, 'Crunch abdominal', 'Crunch en el suelo para el abdomen.', 'BEGINNER', '/images/default.jpg'),
  (19, 'Elevaciones de rodillas colgado', 'Rodillas al pecho colgado en barra.', 'INTERMEDIATE', '/images/default.jpg'),

  (20, 'Plancha', 'Ejercicio isométrico para la estabilidad del core.', 'BEGINNER', '/images/default.jpg'),
  (21, 'Press Pallof', 'Ejercicio anti-rotacional en polea.', 'INTERMEDIATE', '/images/default.jpg'),

  (22, 'Hip thrust', 'Hip thrust con barra para glúteos.', 'INTERMEDIATE', '/images/default.jpg'),
  (23, 'Puente de glúteos', 'Puente de glúteos con peso corporal.', 'BEGINNER', '/images/default.jpg'),

  (24, 'Sentadilla trasera', 'Sentadilla profunda con barra.', 'ADVANCED', '/images/default.jpg'),
  (25, 'Zancadas caminando', 'Zancadas alternas hacia delante.', 'INTERMEDIATE', '/images/default.jpg'),
  (26, 'Prensa de piernas', 'Prensa de piernas en máquina.', 'BEGINNER', '/images/default.jpg'),
  (27, 'Peso muerto rumano', 'Bisagra de cadera enfocada en isquiotibiales.', 'INTERMEDIATE', '/images/default.jpg'),

  (28, 'Elevaciones de gemelos', 'Elevaciones de gemelos de pie.', 'BEGINNER', '/images/default.jpg'),
  (29, 'Elevaciones de gemelos sentado', 'Elevaciones de gemelos en máquina sentado.', 'BEGINNER', '/images/default.jpg'),

  (30, 'Flexión de cuello', 'Flexión de cuello con resistencia.', 'BEGINNER', '/images/default.jpg'),
  (31, 'Extensión de cuello', 'Extensión de cuello con resistencia.', 'BEGINNER', '/images/default.jpg'),

  (32, 'Encogimientos con barra', 'Encogimientos con barra para trapecios.', 'INTERMEDIATE', '/images/default.jpg'),

  (33, 'Burpees', 'Ejercicio de acondicionamiento de cuerpo completo.', 'ADVANCED', '/images/default.jpg'),

  (34, 'Saltar a la comba', 'Cardio continuo con comba.', 'BEGINNER', '/images/default.jpg'),
  (35, 'Remo en máquina', 'Cardio en máquina de remo.', 'INTERMEDIATE', '/images/default.jpg'),

  (36, 'Escaladores', 'Ejercicio dinámico de core y cardio.', 'BEGINNER', '/images/default.jpg'),
  (37, 'Kettlebell swing', 'Movimiento completo de fuerza y cardio.', 'INTERMEDIATE', '/images/default.jpg'),
  (38, 'Flujo de yoga', 'Secuencia de movilidad y estiramientos.', 'BEGINNER', '/images/default.jpg');


INSERT INTO exercise_categories (exercise_id, category_id) VALUES
  (1, 1), (2, 1), (3, 1), (4, 1),        -- Pecho
  (5, 2), (6, 2), (7, 2), (8, 2),        -- Espalda
  (9, 4), (10, 4), (11, 4),              -- Hombros
  (12, 5), (13, 5),                      -- Brazos (bíceps)
  (14, 5), (15, 5),                      -- Brazos (tríceps)
  (16, 23), (17, 23),                    -- Antebrazos
  (18, 6), (19, 6), (20, 6), (21, 6),    -- Core
  (22, 17), (23, 17),                    -- Glúteos
  (24, 3), (25, 3), (26, 3), (27, 3),    -- Piernas
  (28, 20), (29, 20),                    -- Gemelos
  (30, 22), (31, 22),                    -- Cuello
  (32, 8), (33, 8),                      -- Cuerpo completo
  (34, 7), (35, 7),                      -- Cardio
  (36, 6), (36, 7), (36, 9),             -- Escaladores
  (37, 2), (37, 3), (37, 8),             -- Kettlebell swing
  (38, 10), (38, 11);                    -- Yoga

ALTER TABLE exercises ALTER COLUMN id RESTART WITH 100;
ALTER TABLE categories ALTER COLUMN id RESTART WITH 100;
