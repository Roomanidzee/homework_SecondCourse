CREATE TABLE user_info(
  id SERIAL PRIMARY KEY ,
  user_id INTEGER NOT NULL,
  user_reservation_id INTEGER NOT NULL,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (user_reservation_id) REFERENCES reservation(id) ON DELETE CASCADE
);

CREATE TABLE reservation_info(
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  reservation_id INTEGER NOT NULL,
  reservation_product_id INTEGER NOT NULL,
  CONSTRAINT fk_user_profile_id FOREIGN KEY (user_id) REFERENCES profile(id) ON DELETE CASCADE,
  CONSTRAINT fk_reservation_id FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE cascade,
  FOREIGN KEY (reservation_product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE busket(
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  reservation_product_id INTEGER NOT NULL,
  CONSTRAINT fk_user_profile_id FOREIGN KEY (user_id) REFERENCES profile(id) ON DELETE CASCADE,
  FOREIGN KEY (reservation_product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE product_info(
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  product_id INTEGER NOT NULL,
  CONSTRAINT fk_profile_id FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE profile_info(
  id SERIAL PRIMARY KEY,
  user_id INTEGER,
  country VARCHAR(255),
  post_index INTEGER,
  city VARCHAR(255),
  street VARCHAR(255),
  home_number SMALLINT,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE profile ADD FOREIGN KEY (address_id) REFERENCES profile_info(id);