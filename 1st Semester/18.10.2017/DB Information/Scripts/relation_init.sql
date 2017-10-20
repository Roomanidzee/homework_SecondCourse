CREATE TABLE profile_info(
  id SERIAL PRIMARY KEY,
  user_id INTEGER,
  country VARCHAR(200),
  postal_code SMALLINT,
  city VARCHAR(200),
  street VARCHAR(200),
  home_number SMALLINT,
  FOREIGN KEY(user_id) REFERENCES profile(id)
);

ALTER TABLE profile ADD COLUMN address_id INTEGER;
ALTER TABLE profile ADD FOREIGN KEY (address_id) REFERENCES profile_info(id) ON DELETE CASCADE