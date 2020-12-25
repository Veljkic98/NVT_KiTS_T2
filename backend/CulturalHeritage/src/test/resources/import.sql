-- CH TYPES  SIZE = 3
INSERT INTO chtype (  name) VALUES  ('manifestation');
INSERT INTO chtype (  name) VALUES ('institution');
INSERT INTO chtype ( name) VALUES ('cultural heritage');

--CH SUBTYPES  SIZE = 5
INSERT INTO chsubtype (  name, chtype_id) VALUES ( 'festival', 1);
INSERT INTO chsubtype ( name, chtype_id) VALUES ( 'fair', 1);
INSERT INTO chsubtype (  name, chtype_id) VALUES( 'museum', 2);
INSERT INTO chsubtype (  name, chtype_id) VALUES ( 'monument', 3);
INSERT INTO chsubtype (  name, chtype_id) VALUES ( 'landmark', 3);

--LOCATIONS
INSERT INTO location (city, country, latitude, longitude) VALUES (  'Louisville', 'Kentucky, USA', '38.256111', '-85.751389');
INSERT INTO location ( city, country, latitude, longitude) VALUES ( 'London', 'England', '51.509865', '-0.118092');

--CULTURAL HERITAGE
INSERT INTO cultural_heritage ( name, location_id, chsubtype_id, description) VALUES ( 'Venice Carnival', 1, 1,'The Carnival...');
INSERT INTO cultural_heritage ( name, location_id, chsubtype_id, description) VALUES ( 'La Tomatina Festival',2, 1, 'La Tomatina is a festival that is held in the Valencian town of Buñol, a town located 30 km inland from the Mediterranean Sea in which participants throw tomatoes and get involved in this tomato fight purely for fun. It is held on the last Wednesday of August, during the week of festivities of Buñol.');


--AUTHORITY
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');

--ADMIN
INSERT INTO admin (id, first_name, last_name, email, password, approved) VALUES (1,'John', 'Smith', 'admin@gmail.com', '$2y$12$.PWVvztLIA1VBvOcpykig.CZPr78dwT3mVrtCda8YjjyNOas4P6j2', 'true');
insert into user_authority (user_id, authority_id) values (1, 1);

--USERS  SIZE = 4
INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (2, 'Helen', 'York', 'helen@hotmail.com', '$2y$12$.PWVvztLIA1VBvOcpykig.CZPr78dwT3mVrtCda8YjjyNOas4P6j2', 'true');
insert into user_authority (user_id, authority_id) values (2, 2);

INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (3,'Sima', 'Matas', 'sima12@hotmail.com', '$2y$12$.PWVvztLIA1VBvOcpykig.CZPr78dwT3mVrtCda8YjjyNOas4P6j2', 'true');
insert into user_authority (user_id, authority_id) values (3, 2)

INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (4,'Margene', 'Weatherwax', 'Weatherwax12@gmail.com', '$2y$12$.PWVvztLIA1VBvOcpykig.CZPr78dwT3mVrtCda8YjjyNOas4P6j2', 'true');
insert into user_authority (user_id, authority_id) values (4, 2)

INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (5,'Everette', 'Desch', 'heldesch123en@hotmail.com', '$2y$12$.PWVvztLIA1VBvOcpykig.CZPr78dwT3mVrtCda8YjjyNOas4P6j2', 'true');
insert into user_authority (user_id, authority_id) values (5, 2)

--RATINGS  SIZE = 3  (2 grades for same ch, one for another)
INSERT INTO rating (grade, authenticated_user_id, cultural_heritage_id) VALUES (3, 3, 1);
INSERT INTO rating (grade, authenticated_user_id, cultural_heritage_id) VALUES (5, 4, 1);
INSERT INTO rating (grade, authenticated_user_id, cultural_heritage_id) VALUES (5, 2, 2);

--NEWS
insert into news ( content, heading, cultural_heritage_id, admin_id) values ('sadrzajj', 'Naslov', 1, 1);