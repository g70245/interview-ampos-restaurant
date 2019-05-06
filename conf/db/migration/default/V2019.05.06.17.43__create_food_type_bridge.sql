CREATE TABLE food_type (
  `food_id` INT NOT NULL,
  `type_id` INT NOT NULL,
  PRIMARY KEY (food_id, type_id),
  FOREIGN KEY (food_id) REFERENCES food(id) ON DELETE CASCADE,
  FOREIGN KEY (type_id) REFERENCES type(id) ON DELETE CASCADE
);