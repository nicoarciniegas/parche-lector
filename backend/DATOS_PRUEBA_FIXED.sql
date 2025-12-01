-- ============================================
-- SCRIPT DE DATOS DUMMY PARA PARCHE LECTOR (FIXED)
-- ============================================

-- ============================================
-- PASO 1: TABLAS INDEPENDIENTES
-- ============================================

-- USUARIOS (contraseña: "password123" encriptada con BCrypt)
INSERT INTO users (username, email, password_hash, avatar_url, bio, is_active, created_at) VALUES
('ana_lector', 'ana@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Ana&backgroundColor=F9C846', 'Apasionada lectora. Me gusta descubrir autores clásicos y nuevas voces.', true, CURRENT_TIMESTAMP),
('carlos_book', 'carlos@email.com', '$2a$12$3hVS1g0.4Y013UMNKOG4Uec1QHFjo65hK26i2cCbVPMstfdDDhnVa', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Carlos&backgroundColor=6B9080', 'Amante de la ciencia ficción y fantasía.', true, CURRENT_TIMESTAMP),
('maria_reads', 'maria@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Maria&backgroundColor=2E5266', 'Leo de todo un poco. Fan de los clásicos.', true, CURRENT_TIMESTAMP),
('pedro_writer', 'pedro@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Pedro&backgroundColor=A4B494', 'Escritor aficionado y lector empedernido.', true, CURRENT_TIMESTAMP),
('lucia_pages', 'lucia@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Lucia&backgroundColor=E8DCC8', 'Lectora nocturna. Romance y misterio.', true, CURRENT_TIMESTAMP);

-- AUTORES
INSERT INTO authors (name, sort_name, created_at, updated_at) VALUES
('Gabriel García Márquez', 'García Márquez, Gabriel', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Julio Cortázar', 'Cortázar, Julio', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Isabel Allende', 'Allende, Isabel', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Jorge Luis Borges', 'Borges, Jorge Luis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pablo Neruda', 'Neruda, Pablo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Laura Esquivel', 'Esquivel, Laura', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Carlos Ruiz Zafón', 'Ruiz Zafón, Carlos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mario Vargas Llosa', 'Vargas Llosa, Mario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Octavio Paz', 'Paz, Octavio', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Elena Poniatowska', 'Poniatowska, Elena', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Juan Rulfo', 'Rulfo, Juan', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ángeles Mastretta', 'Mastretta, Ángeles', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Roberto Bolaño', 'Bolaño, Roberto', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Alejo Carpentier', 'Carpentier, Alejo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Rosario Castellanos', 'Castellanos, Rosario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- GÉNEROS
INSERT INTO genres (name) VALUES
('Ficción'),
('Realismo Mágico'),
('Romance'),
('Misterio'),
('Ciencia Ficción'),
('Fantasía'),
('Historia'),
('Poesía'),
('Drama'),
('Suspenso'),
('Aventura'),
('Contemporáneo'),
('Clásico'),
('Histórico'),
('Biografía');

-- LIBROS
INSERT INTO books (title, description, isbn13, published_year, cover_url, language, page_count, created_at, updated_at) VALUES
('Cien años de soledad', 'La historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.', '9780307474728', 1967, 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400', 'es', 417, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Rayuela', 'Una novela experimental que puede leerse de múltiples formas, explorando la vida de Horacio Oliveira.', '9788420407548', 1963, 'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400', 'es', 635, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('La casa de los espíritus', 'La saga de la familia Trueba a través de cuatro generaciones, mezclando lo político con lo mágico.', '9780525433446', 1982, 'https://images.unsplash.com/photo-1495446815901-a7297e633e8d?w=400', 'es', 448, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('El laberinto de la soledad', 'Ensayo filosófico sobre la identidad del mexicano y la cultura latinoamericana.', '9789681601072', 1950, 'https://images.unsplash.com/photo-1497633762265-9d179a990aa6?w=400', 'es', 214, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Como agua para chocolate', 'Una historia de amor prohibido contada a través de recetas de cocina.', '9780385721233', 1989, 'https://images.unsplash.com/photo-1589998059171-988d887df646?w=400', 'es', 246, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('La sombra del viento', 'Un joven descubre un libro que cambiará su vida en el misterioso Cementerio de los Libros Olvidados.', '9780143034902', 2001, 'https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400', 'es', 487, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pedro Páramo', 'Un hombre busca a su padre en un pueblo fantasmal lleno de susurros y recuerdos.', '9780802133908', 1955, 'https://images.unsplash.com/photo-1519682337058-a94d519337bc?w=400', 'es', 124, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('La ciudad y los perros', 'La vida en un colegio militar de Lima revela las tensiones sociales del Perú.', '9788420410746', 1963, 'https://images.unsplash.com/photo-1524578271613-d550eacf6090?w=400', 'es', 408, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ficciones', 'Colección de cuentos que exploran la filosofía, el tiempo y la realidad.', '9780802130303', 1944, 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400', 'es', 174, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('El amor en los tiempos del cólera', 'Una historia épica de amor que dura más de cincuenta años.', '9780307389732', 1985, 'https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=400', 'es', 368, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2666', 'Una novela ambiciosa que conecta múltiples historias a través del tiempo y el espacio.', '9780312429218', 2004, 'https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400', 'es', 912, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Arráncame la vida', 'La historia de Catalina, quien se casa con un general corrupto durante el México posrevolucionario.', '9780307391278', 1985, 'https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=400', 'es', 304, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Los detectives salvajes', 'Dos poetas buscan a una misteriosa escritora en el México de los años 70.', '9780312427481', 1998, 'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400', 'es', 609, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('El reino de este mundo', 'Novela histórica sobre la Revolución Haitiana y el concepto de lo real maravilloso.', '9780374530273', 1949, 'https://images.unsplash.com/photo-1524578271613-d550eacf6090?w=400', 'es', 185, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Veinte poemas de amor y una canción desesperada', 'Una de las colecciones de poesía más icónicas del siglo XX.', '9780140187700', 1924, 'https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?w=400', 'es', 127, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- PASO 2: TABLAS DE RELACIÓN (Muchos a Muchos)
-- ============================================

-- BOOK_AUTHORS (Relacionar libros con autores) - Sin role y position
INSERT INTO book_authors (book_id, author_id) VALUES
(1, 1),   -- Cien años de soledad - García Márquez
(2, 2),   -- Rayuela - Cortázar
(3, 3),   -- La casa de los espíritus - Allende
(4, 9),   -- El laberinto de la soledad - Octavio Paz
(5, 6),   -- Como agua para chocolate - Esquivel
(6, 7),   -- La sombra del viento - Ruiz Zafón
(7, 11),  -- Pedro Páramo - Juan Rulfo
(8, 8),   -- La ciudad y los perros - Vargas Llosa
(9, 4),   -- Ficciones - Borges
(10, 1),  -- El amor en los tiempos del cólera - García Márquez
(11, 13), -- 2666 - Bolaño
(12, 12), -- Arráncame la vida - Mastretta
(13, 13), -- Los detectives salvajes - Bolaño
(14, 14), -- El reino de este mundo - Carpentier
(15, 5);  -- Veinte poemas - Neruda

-- BOOK_GENRES (Relacionar libros con géneros)
INSERT INTO book_genres (book_id, genre_id) VALUES
-- Cien años de soledad
(1, 2), (1, 13),
-- Rayuela
(2, 1), (2, 12),
-- La casa de los espíritus
(3, 2), (3, 7), (3, 13),
-- El laberinto de la soledad
(4, 7), (4, 15),
-- Como agua para chocolate
(5, 2), (5, 3),
-- La sombra del viento
(6, 4), (6, 10), (6, 14),
-- Pedro Páramo
(7, 2), (7, 13),
-- La ciudad y los perros
(8, 9), (8, 12),
-- Ficciones
(9, 1), (9, 13),
-- El amor en los tiempos del cólera
(10, 2), (10, 3),
-- 2666
(11, 1), (11, 4),
-- Arráncame la vida
(12, 3), (12, 14),
-- Los detectives salvajes
(13, 1), (13, 4),
-- El reino de este mundo
(14, 2), (14, 14),
-- Veinte poemas
(15, 8), (15, 13);

-- FOLLOWS (Relaciones de seguimiento entre usuarios)
INSERT INTO follows (follower_id, followed_id, created_at) VALUES
(1, 2, CURRENT_TIMESTAMP), (1, 3, CURRENT_TIMESTAMP), (1, 4, CURRENT_TIMESTAMP),
(2, 1, CURRENT_TIMESTAMP), (2, 3, CURRENT_TIMESTAMP), (2, 5, CURRENT_TIMESTAMP),
(3, 1, CURRENT_TIMESTAMP), (3, 2, CURRENT_TIMESTAMP), (3, 4, CURRENT_TIMESTAMP), (3, 5, CURRENT_TIMESTAMP),
(4, 1, CURRENT_TIMESTAMP), (4, 2, CURRENT_TIMESTAMP),
(5, 1, CURRENT_TIMESTAMP), (5, 3, CURRENT_TIMESTAMP);

-- ============================================
-- PASO 3: TABLAS DEPENDIENTES DE USUARIOS Y LIBROS
-- ============================================

-- READING_STATUS (Estado de lectura de usuarios)
INSERT INTO reading_status (user_id, book_id, status, progress_percent, started_at, finished_at, created_at, updated_at) VALUES
-- Ana (user 1)
(1, 1, 'READ', 100, '2024-01-15', '2024-02-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'READING', 45, '2024-11-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 'WANT_TO_READ', 0, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 6, 'READ', 100, '2024-09-01', '2024-09-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 10, 'READ', 100, '2024-03-15', '2024-04-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Carlos (user 2)
(2, 2, 'READ', 100, '2024-05-01', '2024-06-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 6, 'READING', 60, '2024-10-15', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 9, 'READ', 100, '2024-07-01', '2024-07-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 11, 'WANT_TO_READ', 0, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 13, 'READING', 30, '2024-11-10', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Maria (user 3)
(3, 1, 'READ', 100, '2024-02-01', '2024-03-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 'READ', 100, '2024-04-01', '2024-05-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 5, 'READING', 70, '2024-10-20', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 7, 'WANT_TO_READ', 0, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 15, 'READ', 100, '2024-08-01', '2024-08-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Pedro (user 4)
(4, 4, 'READ', 100, '2024-06-01', '2024-06-25', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 8, 'READING', 55, '2024-11-05', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 12, 'WANT_TO_READ', 0, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Lucia (user 5)
(5, 3, 'READ', 100, '2024-07-01', '2024-08-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 5, 'READ', 100, '2024-05-15', '2024-06-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 6, 'READING', 80, '2024-11-01', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 12, 'READ', 100, '2024-09-01', '2024-09-25', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 14, 'WANT_TO_READ', 0, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- REVIEWS (Reseñas y calificaciones)
INSERT INTO reviews (user_id, book_id, rating, title, body, created_at, updated_at, is_deleted) VALUES
-- Ana
(1, 1, 4.9, 'Una obra maestra del realismo mágico', 'García Márquez teje una historia extraordinaria que atrapa desde la primera página. La familia Buendía es inolvidable.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
(1, 6, 4.7, 'Cautivador y misterioso', 'Una novela que te mantiene enganchado hasta el final. El Barcelona de posguerra cobra vida.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
(1, 10, 4.5, 'Romance épico', 'Una historia de amor que trasciende el tiempo. Emotiva y bellamente escrita.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
-- Carlos
(2, 2, 4.3, 'Experimental pero fascinante', 'Cortázar desafía las convenciones narrativas. Requiere atención pero vale la pena.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
(2, 9, 5.0, 'Genio literario puro', 'Borges es simplemente brillante. Cada cuento es una joya filosófica.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
(2, 6, 4.8, 'No puedo dejarlo', 'Voy por la mitad y ya es uno de mis favoritos. La trama es adictiva.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
-- Maria
(3, 1, 5.0, 'Inolvidable', 'Un clásico por una razón. La narrativa es perfecta.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
(3, 3, 4.6, 'Poderosa historia familiar', 'Isabel Allende cuenta historias que tocan el alma. Muy recomendado.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
(3, 15, 4.8, 'Poesía del corazón', 'Neruda captura la pasión y el dolor del amor como nadie más.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
-- Pedro
(4, 4, 4.2, 'Ensayo profundo', 'Paz ofrece una perspectiva única sobre la identidad latinoamericana.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
-- Lucia
(5, 3, 4.5, 'Saga familiar épica', 'Me encantó cada página. Los personajes son tan reales.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
(5, 5, 4.4, 'Delicioso y emotivo', 'La combinación de recetas con romance es genial.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false),
(5, 12, 4.3, 'Perspectiva femenina poderosa', 'Mastretta retrata magistralmente la lucha de una mujer en tiempos difíciles.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);

-- LIBRARY_LISTS (Listas personalizadas de usuarios)
INSERT INTO library_lists (user_id, name, description, visibility, created_at, updated_at) VALUES
(1, 'Clásicos Latinoamericanos', 'Mis libros favoritos de autores latinoamericanos', 'PUBLIC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Por leer este año', 'Lista de libros que quiero terminar antes de 2025', 'PUBLIC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Ciencia Ficción Esencial', 'Los mejores libros de sci-fi', 'PUBLIC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Favoritos de todos los tiempos', 'Libros que he releído múltiples veces', 'PUBLIC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Lista de verano', 'Lecturas para las vacaciones', 'PRIVATE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Romance y Drama', 'Historias que tocan el corazón', 'PUBLIC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- LIST_BOOKS (Libros en las listas)
INSERT INTO list_books (list_id, book_id, position, note, added_at) VALUES
-- Lista 1 de Ana: Clásicos Latinoamericanos
(1, 1, 1, 'El mejor de García Márquez', CURRENT_TIMESTAMP),
(1, 3, 2, 'Allende en su mejor momento', CURRENT_TIMESTAMP),
(1, 9, 3, 'Borges es único', CURRENT_TIMESTAMP),
-- Lista 2 de Ana: Por leer este año
(2, 3, 1, 'Pendiente desde hace meses', CURRENT_TIMESTAMP),
-- Lista 3 de Carlos: Ciencia Ficción
(3, 11, 1, 'Ambiciosa y compleja', CURRENT_TIMESTAMP),
-- Lista 4 de Maria: Favoritos
(4, 1, 1, 'Lo he leído 3 veces', CURRENT_TIMESTAMP),
(4, 3, 2, 'Siempre vuelvo a esta historia', CURRENT_TIMESTAMP),
-- Lista 6 de Lucia: Romance y Drama
(6, 3, 1, 'Romance épico', CURRENT_TIMESTAMP),
(6, 5, 2, 'Amor y comida', CURRENT_TIMESTAMP),
(6, 10, 3, 'García Márquez romántico', CURRENT_TIMESTAMP),
(6, 12, 4, 'Perspectiva femenina poderosa', CURRENT_TIMESTAMP);

-- ============================================
-- PASO 4: TABLAS DE INTERACCIONES Y ACTIVIDAD
-- ============================================

-- REVIEW_LIKES (Likes en reseñas)
INSERT INTO review_likes (review_id, user_id, created_at) VALUES
(1, 2, CURRENT_TIMESTAMP), (1, 3, CURRENT_TIMESTAMP), (1, 4, CURRENT_TIMESTAMP),
(2, 3, CURRENT_TIMESTAMP), (2, 5, CURRENT_TIMESTAMP),
(4, 1, CURRENT_TIMESTAMP), (4, 3, CURRENT_TIMESTAMP),
(5, 1, CURRENT_TIMESTAMP), (5, 3, CURRENT_TIMESTAMP), (5, 4, CURRENT_TIMESTAMP),
(7, 2, CURRENT_TIMESTAMP), (7, 4, CURRENT_TIMESTAMP), (7, 5, CURRENT_TIMESTAMP),
(11, 1, CURRENT_TIMESTAMP), (11, 3, CURRENT_TIMESTAMP);

-- REVIEW_COMMENTS (Comentarios en reseñas)
INSERT INTO review_comments (review_id, user_id, body, created_at, is_deleted) VALUES
(1, 2, 'Totalmente de acuerdo! Es mi libro favorito también.', CURRENT_TIMESTAMP, false),
(1, 3, 'Tu reseña me convenció de releerlo.', CURRENT_TIMESTAMP, false),
(5, 1, 'Borges es mi autor favorito. Excelente análisis!', CURRENT_TIMESTAMP, false),
(7, 2, 'María, tu pasión por este libro es contagiosa!', CURRENT_TIMESTAMP, false),
(11, 1, 'Gran punto sobre la perspectiva femenina, Lucia!', CURRENT_TIMESTAMP, false);

-- LIST_LIKES (Likes en listas)
INSERT INTO list_likes (list_id, user_id, created_at) VALUES
(1, 2, CURRENT_TIMESTAMP), (1, 3, CURRENT_TIMESTAMP), (1, 4, CURRENT_TIMESTAMP),
(4, 1, CURRENT_TIMESTAMP), (4, 2, CURRENT_TIMESTAMP),
(6, 1, CURRENT_TIMESTAMP), (6, 2, CURRENT_TIMESTAMP), (6, 3, CURRENT_TIMESTAMP);

-- LIST_COMMENTS (Comentarios en listas)
INSERT INTO list_comments (list_id, user_id, body, created_at, is_deleted) VALUES
(1, 2, 'Gran selección de clásicos!', CURRENT_TIMESTAMP, false),
(1, 3, 'Agregaría Pedro Páramo a esta lista.', CURRENT_TIMESTAMP, false),
(4, 1, 'Me encanta que tengamos gustos similares!', CURRENT_TIMESTAMP, false),
(6, 2, 'Necesito leer más de estos!', CURRENT_TIMESTAMP, false);

-- FAVORITE_BOOKS (Libros favoritos de usuarios)
INSERT INTO favorite_books (user_id, book_id, created_at) VALUES
(1, 1, CURRENT_TIMESTAMP), (1, 10, CURRENT_TIMESTAMP),
(2, 9, CURRENT_TIMESTAMP), (2, 6, CURRENT_TIMESTAMP),
(3, 1, CURRENT_TIMESTAMP), (3, 3, CURRENT_TIMESTAMP),
(4, 4, CURRENT_TIMESTAMP),
(5, 12, CURRENT_TIMESTAMP), (5, 5, CURRENT_TIMESTAMP);

-- USER_GENRES (Géneros favoritos de usuarios)
INSERT INTO user_genres (user_id, genre_id) VALUES
(1, 2), (1, 13), (1, 3),
(2, 1), (2, 4), (2, 5),
(3, 2), (3, 13), (3, 7),
(4, 7), (4, 15),
(5, 3), (5, 14);

-- AUTHOR_FOLLOWS (Usuarios siguiendo autores)
INSERT INTO author_follows (user_id, author_id, created_at) VALUES
(1, 1, CURRENT_TIMESTAMP), (1, 3, CURRENT_TIMESTAMP), (1, 7, CURRENT_TIMESTAMP),
(2, 4, CURRENT_TIMESTAMP), (2, 13, CURRENT_TIMESTAMP),
(3, 1, CURRENT_TIMESTAMP), (3, 3, CURRENT_TIMESTAMP), (3, 5, CURRENT_TIMESTAMP),
(4, 9, CURRENT_TIMESTAMP), (4, 11, CURRENT_TIMESTAMP),
(5, 3, CURRENT_TIMESTAMP), (5, 6, CURRENT_TIMESTAMP), (5, 12, CURRENT_TIMESTAMP);

-- ACTIVITY_LOG (Actividad reciente)
INSERT INTO activity_log (user_id, type, subject_book_id, subject_review_id, subject_list_id, created_at) VALUES
(1, 'REVIEW_CREATED', 1, 1, NULL, CURRENT_TIMESTAMP),
(1, 'BOOK_ADDED', 2, NULL, NULL, CURRENT_TIMESTAMP),
(2, 'REVIEW_CREATED', 9, 5, NULL, CURRENT_TIMESTAMP),
(3, 'LIST_CREATED', NULL, NULL, 4, CURRENT_TIMESTAMP),
(4, 'BOOK_FINISHED', 4, NULL, NULL, CURRENT_TIMESTAMP),
(5, 'REVIEW_CREATED', 12, 13, NULL, CURRENT_TIMESTAMP);

-- NOTIFICATIONS (Notificaciones de usuarios)
INSERT INTO notifications (user_id, type, payload, is_read, created_at) VALUES
(1, 'NEW_FOLLOWER', '{"follower_id": 2, "follower_name": "carlos_book"}', false, CURRENT_TIMESTAMP),
(1, 'REVIEW_LIKE', '{"user_id": 3, "review_id": 1}', true, CURRENT_TIMESTAMP),
(2, 'NEW_COMMENT', '{"user_id": 1, "comment": "Borges es mi autor favorito..."}', false, CURRENT_TIMESTAMP),
(3, 'NEW_FOLLOWER', '{"follower_id": 1, "follower_name": "ana_lector"}', true, CURRENT_TIMESTAMP),
(5, 'LIST_LIKE', '{"user_id": 1, "list_id": 6}', false, CURRENT_TIMESTAMP);

-- ============================================
-- VERIFICACIÓN DE DATOS
-- ============================================

SELECT COUNT(*) as total_users FROM users;
SELECT COUNT(*) as total_books FROM books;
SELECT COUNT(*) as total_authors FROM authors;
SELECT COUNT(*) as total_genres FROM genres;
SELECT COUNT(*) as total_reading_status FROM reading_status;
SELECT COUNT(*) as total_reviews FROM reviews;
SELECT COUNT(*) as total_follows FROM follows;
SELECT COUNT(*) as total_lists FROM library_lists;
