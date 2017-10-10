CREATE TABLE users(
  id SERIAL PRIMARY KEY,
  username_or_email VARCHAR(250),
  password TEXT
);

CREATE TABLE profile(
  id SERIAL PRIMARY KEY,
  country VARCHAR(50),
  gender VARCHAR(20),
  subscription VARCHAR(30),
  user_id INTEGER,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);