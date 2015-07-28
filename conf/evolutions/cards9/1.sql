# --- !Ups

CREATE TABLE card_classes (
  id serial primary key,
  name varchar(20),
  imgurl varchar(75)
);

# --- !Downs

DROP TABLE card_classes;
