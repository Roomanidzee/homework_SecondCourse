CREATE TABLE users(
  id SERIAL PRIMARY KEY,
  username_or_email VARCHAR(250),
  password TEXT
);

CREATE TABLE profile(
  id SERIAL PRIMARY KEY,
  person_name VARCHAR(100),
  person_surname VARCHAR(100),
  user_id INTEGER,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);