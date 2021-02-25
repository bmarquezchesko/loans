DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                      id INT AUTO_INCREMENT  PRIMARY KEY,
                      email VARCHAR(250) DEFAULT NULL,
                      first_name VARCHAR(250) NOT NULL,
                      last_name VARCHAR(250) NOT NULL
);

CREATE TABLE loans (
                       id INT AUTO_INCREMENT  PRIMARY KEY,
                       total INTEGER DEFAULT NULL,
                       user_id INT NOT NULL,
                       FOREIGN KEY(user_id) REFERENCES users(id)
);

INSERT INTO users (first_name, last_name, email) VALUES
('Lucas', 'Lopez', 'lucaslopez@test.com'),
('Federico', 'Gomez', 'federicogomez@test.com'),
('Leandro', 'Sanchez', 'leandrosanchez@test.com'),
('Luciano', 'Perez', 'lucianoperez@test.com'),
('Emmanuel', 'Suarez', 'emmanuelsuarez@test.com'),
('Giannina', 'Rodriguez', 'gianninarodriguez@test.com'),
('Diego', 'Martinez', 'diegomartinez@test.com'),
('Franco', 'Amaya', 'francoamaya@test.com'),
('Alejandro', 'Marquez', 'alejandromarquez@test.com'),
('Agustin', 'Ibarra', 'agustinibarra@test.com');

INSERT INTO loans (total, user_id) VALUES
(2500, 1),
(1500, 1),
(1000, 1),
(3000, 1),
(5000, 1),
(7000, 1),
(10000, 1),
(1000, 2),
(1500, 2),
(2000, 2),
(100, 3),
(200, 3),
(15000, 4),
(1000, 6),
(10000, 7),
(20000, 7),
(30000, 7),
(500, 8),
(15000, 10),
(15000, 10);