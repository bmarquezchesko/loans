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
('Lucas', 'Lopez', 'lucaslopez@gmail.com'),
('Federico', 'Gomez', 'federicogomez@gmail.com'),
('Leandro', 'Sanchez', 'leandrosanchez@gmail.com');

INSERT INTO loans (total, user_id) VALUES
(2500, 1),
(1500, 1),
(1000, 2);