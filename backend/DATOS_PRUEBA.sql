-- ============================================
-- SCRIPT DE DATOS DUMMY PARA PARCHE LECTOR
-- ============================================
-- EJECUTAR EN ORDEN:
-- 1. Tablas independientes (users, books, authors, genres)
-- 2. Tablas de relación (book_authors, book_genres, follows)
-- 3. Tablas dependientes de usuarios y libros (reading_status, reviews, library_lists)
-- 4. Resto de tablas
-- ============================================

-- ============================================
-- PASO 1: TABLAS INDEPENDIENTES
-- ============================================

-- USUARIOS (contraseña: "password123" encriptada con BCrypt)
INSERT INTO users (username, email, password_hash, avatar_url, bio, is_active) VALUES
('ana_lector', 'ana@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Ana&backgroundColor=F9C846', 'Apasionada lectora. Me gusta descubrir autores clásicos y nuevas voces.', true),
('carlos_book', 'carlos@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Carlos&backgroundColor=6B9080', 'Amante de la ciencia ficción y fantasía.', true),
('maria_reads', 'maria@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Maria&backgroundColor=2E5266', 'Leo de todo un poco. Fan de los clásicos.', true),
('pedro_writer', 'pedro@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Pedro&backgroundColor=A4B494', 'Escritor aficionado y lector empedernido.', true),
('lucia_pages', 'lucia@email.com', '$2a$10$Xl0R0QVFbLvqNp.kL9xKp.jYpXKPzO8YJf.ZG8xQqKQxTqKLJpJXG', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Lucia&backgroundColor=E8DCC8', 'Lectora nocturna. Romance y misterio.', true);

-- AUTORES
INSERT INTO authors (name, sort_name) VALUES
('Gabriel García Márquez', 'García Márquez, Gabriel'),
('Julio Cortázar', 'Cortázar, Julio'),
('Isabel Allende', 'Allende, Isabel'),
('Jorge Luis Borges', 'Borges, Jorge Luis'),
('Pablo Neruda', 'Neruda, Pablo'),
('Laura Esquivel', 'Esquivel, Laura'),
('Carlos Ruiz Zafón', 'Ruiz Zafón, Carlos'),
('Mario Vargas Llosa', 'Vargas Llosa, Mario'),
('Octavio Paz', 'Paz, Octavio'),
('Elena Poniatowska', 'Poniatowska, Elena'),
('Juan Rulfo', 'Rulfo, Juan'),
('Ángeles Mastretta', 'Mastretta, Ángeles'),
('Roberto Bolaño', 'Bolaño, Roberto'),
('Alejo Carpentier', 'Carpentier, Alejo'),
('Rosario Castellanos', 'Castellanos, Rosario');

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
INSERT INTO books (title, description, isbn13, published_year, cover_url, language, page_count) VALUES
('Cien años de soledad', 'La historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.', '9780307474728', 1967, 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400', 'es', 417),
('Rayuela', 'Una novela experimental que puede leerse de múltiples formas, explorando la vida de Horacio Oliveira.', '9788420407548', 1963, 'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400', 'es', 635),
('La casa de los espíritus', 'La saga de la familia Trueba a través de cuatro generaciones, mezclando lo político con lo mágico.', '9780525433446', 1982, 'https://images.unsplash.com/photo-1495446815901-a7297e633e8d?w=400', 'es', 448),
('El laberinto de la soledad', 'Ensayo filosófico sobre la identidad del mexicano y la cultura latinoamericana.', '9789681601072', 1950, 'https://images.unsplash.com/photo-1497633762265-9d179a990aa6?w=400', 'es', 214),
('Como agua para chocolate', 'Una historia de amor prohibido contada a través de recetas de cocina.', '9780385721233', 1989, 'https://images.unsplash.com/photo-1589998059171-988d887df646?w=400', 'es', 246),
('La sombra del viento', 'Un joven descubre un libro que cambiará su vida en el misterioso Cementerio de los Libros Olvidados.', '9780143034902', 2001, 'https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400', 'es', 487),
('Pedro Páramo', 'Un hombre busca a su padre en un pueblo fantasmal lleno de susurros y recuerdos.', '9780802133908', 1955, 'https://images.unsplash.com/photo-1519682337058-a94d519337bc?w=400', 'es', 124),
('La ciudad y los perros', 'La vida en un colegio militar de Lima revela las tensiones sociales del Perú.', '9788420410746', 1963, 'https://images.unsplash.com/photo-1524578271613-d550eacf6090?w=400', 'es', 408),
('Ficciones', 'Colección de cuentos que exploran la filosofía, el tiempo y la realidad.', '9780802130303', 1944, 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400', 'es', 174),
('El amor en los tiempos del cólera', 'Una historia épica de amor que dura más de cincuenta años.', '9780307389732', 1985, 'https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=400', 'es', 368),
('2666', 'Una novela ambiciosa que conecta múltiples historias a través del tiempo y el espacio.', '9780312429218', 2004, 'https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400', 'es', 912),
('Arráncame la vida', 'La historia de Catalina, quien se casa con un general corrupto durante el México posrevolucionario.', '9780307391278', 1985, 'https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=400', 'es', 304),
('Los detectives salvajes', 'Dos poetas buscan a una misteriosa escritora en el México de los años 70.', '9780312427481', 1998, 'https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400', 'es', 609),
('El reino de este mundo', 'Novela histórica sobre la Revolución Haitiana y el concepto de lo real maravilloso.', '9780374530273', 1949, 'https://images.unsplash.com/photo-1524578271613-d550eacf6090?w=400', 'es', 185),
('Veinte poemas de amor y una canción desesperada', 'Una de las colecciones de poesía más icónicas del siglo XX.', '9780140187700', 1924, 'https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?w=400', 'es', 127);

-- ============================================
-- PASO 2: TABLAS DE RELACIÓN (Muchos a Muchos)
-- ============================================

-- BOOK_AUTHORS (Relacionar libros con autores)
INSERT INTO book_authors (book_id, author_id, role, position) VALUES
(1, 1, 'author', 1),   -- Cien años de soledad - García Márquez
(2, 2, 'author', 1),   -- Rayuela - Cortázar
(3, 3, 'author', 1),   -- La casa de los espíritus - Allende
(4, 9, 'author', 1),   -- El laberinto de la soledad - Octavio Paz
(5, 6, 'author', 1),   -- Como agua para chocolate - Esquivel
(6, 7, 'author', 1),   -- La sombra del viento - Ruiz Zafón
(7, 11, 'author', 1),  -- Pedro Páramo - Juan Rulfo
(8, 8, 'author', 1),   -- La ciudad y los perros - Vargas Llosa
(9, 4, 'author', 1),   -- Ficciones - Borges
(10, 1, 'author', 1),  -- El amor en los tiempos del cólera - García Márquez
(11, 13, 'author', 1), -- 2666 - Bolaño
(12, 12, 'author', 1), -- Arráncame la vida - Mastretta
(13, 13, 'author', 1), -- Los detectives salvajes - Bolaño
(14, 14, 'author', 1), -- El reino de este mundo - Carpentier
(15, 5, 'author', 1);  -- Veinte poemas - Neruda

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
INSERT INTO follows (follower_id, followed_id) VALUES
(1, 2), (1, 3), (1, 4),  -- Ana sigue a Carlos, Maria, Pedro
(2, 1), (2, 3), (2, 5),  -- Carlos sigue a Ana, Maria, Lucia
(3, 1), (3, 2), (3, 4), (3, 5),  -- Maria sigue a todos menos a si misma
(4, 1), (4, 2),  -- Pedro sigue a Ana, Carlos
(5, 1), (5, 3);  -- Lucia sigue a Ana, Maria

-- ============================================
-- PASO 3: TABLAS DEPENDIENTES DE USUARIOS Y LIBROS
-- ============================================

-- READING_STATUS (Estado de lectura de usuarios)
INSERT INTO reading_status (user_id, book_id, status, progress_percent, started_at, finished_at) VALUES
-- Ana (user 1)
(1, 1, 'READ', 100, '2024-01-15', '2024-02-10'),
(1, 2, 'READING', 45, '2024-11-01', NULL),
(1, 3, 'WANT_TO_READ', 0, NULL, NULL),
(1, 6, 'READ', 100, '2024-09-01', '2024-09-20'),
(1, 10, 'READ', 100, '2024-03-15', '2024-04-05'),
-- Carlos (user 2)
(2, 2, 'READ', 100, '2024-05-01', '2024-06-15'),
(2, 6, 'READING', 60, '2024-10-15', NULL),
(2, 9, 'READ', 100, '2024-07-01', '2024-07-10'),
(2, 11, 'WANT_TO_READ', 0, NULL, NULL),
(2, 13, 'READING', 30, '2024-11-10', NULL),
-- Maria (user 3)
(3, 1, 'READ', 100, '2024-02-01', '2024-03-01'),
(3, 3, 'READ', 100, '2024-04-01', '2024-05-15'),
(3, 5, 'READING', 70, '2024-10-20', NULL),
(3, 7, 'WANT_TO_READ', 0, NULL, NULL),
(3, 15, 'READ', 100, '2024-08-01', '2024-08-05'),
-- Pedro (user 4)
(4, 4, 'READ', 100, '2024-06-01', '2024-06-25'),
(4, 8, 'READING', 55, '2024-11-05', NULL),
(4, 12, 'WANT_TO_READ', 0, NULL, NULL),
-- Lucia (user 5)
(5, 3, 'READ', 100, '2024-07-01', '2024-08-10'),
(5, 5, 'READ', 100, '2024-05-15', '2024-06-01'),
(5, 6, 'READING', 80, '2024-11-01', NULL),
(5, 12, 'READ', 100, '2024-09-01', '2024-09-25'),
(5, 14, 'WANT_TO_READ', 0, NULL, NULL);

-- REVIEWS (Reseñas y calificaciones)
INSERT INTO reviews (user_id, book_id, rating, title, body) VALUES
-- Ana
(1, 1, 4.9, 'Una obra maestra del realismo mágico', 'García Márquez teje una historia extraordinaria que atrapa desde la primera página. La familia Buendía es inolvidable.'),
(1, 6, 4.7, 'Cautivador y misterioso', 'Una novela que te mantiene enganchado hasta el final. El Barcelona de posguerra cobra vida.'),
(1, 10, 4.5, 'Romance épico', 'Una historia de amor que trasciende el tiempo. Emotiva y bellamente escrita.'),
-- Carlos
(2, 2, 4.3, 'Experimental pero fascinante', 'Cortázar desafía las convenciones narrativas. Requiere atención pero vale la pena.'),
(2, 9, 5.0, 'Genio literario puro', 'Borges es simplemente brillante. Cada cuento es una joya filosófica.'),
(2, 6, 4.8, 'No puedo dejarlo', 'Voy por la mitad y ya es uno de mis favoritos. La trama es adictiva.'),
-- Maria
(3, 1, 5.0, 'Inolvidable', 'Un clásico por una razón. La narrativa es perfecta.'),
(3, 3, 4.6, 'Poderosa historia familiar', 'Isabel Allende cuenta historias que tocan el alma. Muy recomendado.'),
(3, 15, 4.8, 'Poesía del corazón', 'Neruda captura la pasión y el dolor del amor como nadie más.'),
-- Pedro
(4, 4, 4.2, 'Ensayo profundo', 'Paz ofrece una perspectiva única sobre la identidad latinoamericana.'),
-- Lucia
(5, 3, 4.5, 'Saga familiar épica', 'Me encantó cada página. Los personajes son tan reales.'),
(5, 5, 4.4, 'Delicioso y emotivo', 'La combinación de recetas con romance es genial.'),
(5, 12, 4.3, 'Perspectiva femenina poderosa', 'Mastretta retrata magistralmente la lucha de una mujer en tiempos difíciles.');

-- LIBRARY_LISTS (Listas personalizadas de usuarios)
INSERT INTO library_lists (user_id, name, description, visibility) VALUES
(1, 'Clásicos Latinoamericanos', 'Mis libros favoritos de autores latinoamericanos', 'PUBLIC'),
(1, 'Por leer este año', 'Lista de libros que quiero terminar antes de 2025', 'PUBLIC'),
(2, 'Ciencia Ficción Esencial', 'Los mejores libros de sci-fi', 'PUBLIC'),
(3, 'Favoritos de todos los tiempos', 'Libros que he releído múltiples veces', 'PUBLIC'),
(4, 'Lista de verano', 'Lecturas para las vacaciones', 'PRIVATE'),
(5, 'Romance y Drama', 'Historias que tocan el corazón', 'PUBLIC');

-- LIST_BOOKS (Libros en las listas)
INSERT INTO list_books (list_id, book_id, position, note) VALUES
-- Lista 1 de Ana: Clásicos Latinoamericanos
(1, 1, 1, 'El mejor de García Márquez'),
(1, 3, 2, 'Allende en su mejor momento'),
(1, 9, 3, 'Borges es único'),
-- Lista 2 de Ana: Por leer este año
(2, 3, 1, 'Pendiente desde hace meses'),
-- Lista 3 de Carlos: Ciencia Ficción
(3, 11, 1, 'Ambiciosa y compleja'),
-- Lista 4 de Maria: Favoritos
(4, 1, 1, 'Lo he leído 3 veces'),
(4, 3, 2, 'Siempre vuelvo a esta historia'),
-- Lista 6 de Lucia: Romance y Drama
(6, 3, 1, 'Romance épico'),
(6, 5, 2, 'Amor y comida'),
(6, 10, 3, 'García Márquez romántico'),
(6, 12, 4, 'Perspectiva femenina poderosa');

-- ============================================
-- PASO 4: TABLAS DE INTERACCIONES Y ACTIVIDAD
-- ============================================

-- REVIEW_LIKES (Likes en reseñas)
INSERT INTO review_likes (review_id, user_id) VALUES
(1, 2), (1, 3), (1, 4),  -- Reseña de Ana sobre Cien años
(2, 3), (2, 5),  -- Reseña de Ana sobre La sombra
(4, 1), (4, 3),  -- Reseña de Carlos sobre Rayuela
(5, 1), (5, 3), (5, 4),  -- Reseña de Carlos sobre Ficciones
(7, 2), (7, 4), (7, 5),  -- Reseña de Maria sobre Cien años
(11, 1), (11, 3);  -- Reseña de Lucia sobre Casa de espíritus

-- REVIEW_COMMENTS (Comentarios en reseñas)
INSERT INTO review_comments (review_id, user_id, body) VALUES
(1, 2, 'Totalmente de acuerdo! Es mi libro favorito también.'),
(1, 3, 'Tu reseña me convenció de releerlo.'),
(5, 1, 'Borges es mi autor favorito. Excelente análisis!'),
(7, 2, 'María, tu pasión por este libro es contagiosa!'),
(11, 1, 'Gran punto sobre la perspectiva femenina, Lucia!');

-- LIST_LIKES (Likes en listas)
INSERT INTO list_likes (list_id, user_id) VALUES
(1, 2), (1, 3), (1, 4),  -- Lista de clásicos de Ana
(4, 1), (4, 2),  -- Lista de favoritos de Maria
(6, 1), (6, 2), (6, 3);  -- Lista de romance de Lucia

-- LIST_COMMENTS (Comentarios en listas)
INSERT INTO list_comments (list_id, user_id, body) VALUES
(1, 2, 'Gran selección de clásicos!'),
(1, 3, 'Agregaría Pedro Páramo a esta lista.'),
(4, 1, 'Me encanta que tengamos gustos similares!'),
(6, 2, 'Necesito leer más de estos!');

-- FAVORITE_BOOKS (Libros favoritos de usuarios)
INSERT INTO favorite_books (user_id, book_id) VALUES
(1, 1), (1, 10),  -- Ana: Cien años y El amor en tiempos
(2, 9), (2, 6),  -- Carlos: Ficciones y La sombra
(3, 1), (3, 3),  -- Maria: Cien años y Casa de espíritus
(4, 4),  -- Pedro: Laberinto
(5, 12), (5, 5);  -- Lucia: Arráncame y Como agua

-- USER_GENRES (Géneros favoritos de usuarios)
INSERT INTO user_genres (user_id, genre_id) VALUES
(1, 2), (1, 13), (1, 3),  -- Ana: Realismo mágico, Clásico, Romance
(2, 1), (2, 4), (2, 5),  -- Carlos: Ficción, Misterio, Sci-fi
(3, 2), (3, 13), (3, 7),  -- Maria: Realismo mágico, Clásico, Historia
(4, 7), (4, 15),  -- Pedro: Historia, Biografía
(5, 3), (5, 14);  -- Lucia: Romance, Histórico

-- AUTHOR_FOLLOWS (Usuarios siguiendo autores)
INSERT INTO author_follows (user_id, author_id) VALUES
(1, 1), (1, 3), (1, 7),  -- Ana sigue a García Márquez, Allende, Ruiz Zafón
(2, 4), (2, 13),  -- Carlos sigue a Borges, Bolaño
(3, 1), (3, 3), (3, 5),  -- Maria sigue a García Márquez, Allende, Neruda
(4, 9), (4, 11),  -- Pedro sigue a Paz, Rulfo
(5, 3), (5, 6), (5, 12);  -- Lucia sigue a Allende, Esquivel, Mastretta

-- ACTIVITY_LOG (Actividad reciente)
INSERT INTO activity_log (user_id, type, subject_book_id, subject_review_id, subject_list_id) VALUES
(1, 'REVIEW_CREATED', 1, 1, NULL),
(1, 'BOOK_ADDED', 2, NULL, NULL),
(2, 'REVIEW_CREATED', 9, 5, NULL),
(3, 'LIST_CREATED', NULL, NULL, 4),
(4, 'BOOK_FINISHED', 4, NULL, NULL),
(5, 'REVIEW_CREATED', 12, 13, NULL);

-- NOTIFICATIONS (Notificaciones de usuarios)
INSERT INTO notifications (user_id, type, payload, is_read) VALUES
(1, 'NEW_FOLLOWER', '{"follower_id": 2, "follower_name": "carlos_book"}', false),
(1, 'REVIEW_LIKE', '{"user_id": 3, "review_id": 1}', true),
(2, 'NEW_COMMENT', '{"user_id": 1, "comment": "Borges es mi autor favorito..."}', false),
(3, 'NEW_FOLLOWER', '{"follower_id": 1, "follower_name": "ana_lector"}', true),
(5, 'LIST_LIKE', '{"user_id": 1, "list_id": 6}', false);

-- ============================================
-- VERIFICACIÓN DE DATOS
-- ============================================

-- Descomentar estas queries para verificar que todo se insertó correctamente:

-- SELECT COUNT(*) as total_users FROM users;
-- SELECT COUNT(*) as total_books FROM books;
-- SELECT COUNT(*) as total_authors FROM authors;
-- SELECT COUNT(*) as total_genres FROM genres;
-- SELECT COUNT(*) as total_reading_status FROM reading_status;
-- SELECT COUNT(*) as total_reviews FROM reviews;
-- SELECT COUNT(*) as total_follows FROM follows;
-- SELECT COUNT(*) as total_lists FROM library_lists;

-- Query para ver el perfil completo de Ana (user_id = 1):
/*
SELECT 
    u.username,
    u.bio,
    (SELECT COUNT(*) FROM follows WHERE followed_id = u.id) as followers,
    (SELECT COUNT(*) FROM follows WHERE follower_id = u.id) as following,
    (SELECT COUNT(*) FROM reading_status WHERE user_id = u.id) as total_books
FROM users u
WHERE u.id = 1;
*/
