INSERT INTO chtype (name) VALUES  ('masd');
INSERT INTO chtype (name) VALUES  ('CH Type without subtypes, please dont add subtypes with this type.ty');

INSERT INTO chsubtype (name, chtype_id) VALUES ('festival', 1);

INSERT INTO location (city, country, latitude, longitude) VALUES ('Louisville', 'Kentucky, USA', '38.256111', '-85.751389');
INSERT INTO location (city, country, latitude, longitude) VALUES ('asd', 'dsa', '18.256111', '-88.751389');

INSERT INTO cultural_heritage (name, location_id, chsubtype_id, description) VALUES ('CH1', 1, 1, 'The Carnival...');
INSERT INTO cultural_heritage (name, location_id, chsubtype_id, description) VALUES ('CH2', 1, 1, 'The Carnival...');

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

INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (5,'Everette', 'Desch', 'heldesch123en@hotmail.com', '$2y$12$.PWVvztLIA1VBvOcpykig.CZPr78dwT3mVrtCda8YjjyNOas4P6j2', 'false');
insert into user_authority (user_id, authority_id) values (5, 2)


insert into news (content, heading, cultural_heritage_id, admin_id) values ('sadrzajj', 'Naslov1', 1, 1);
insert into news (content, heading, cultural_heritage_id, admin_id) values ('sadrzajj2', 'Naslov2', 1, 1);
insert into news (content, heading, cultural_heritage_id, admin_id) values ('sadrzajj3', 'Naslov3', 1, 1);

--RATINGS  SIZE = 3  (2 grades for same ch, one for another)
INSERT INTO rating (grade, authenticated_user_id, cultural_heritage_id) VALUES (3, 3, 1);
INSERT INTO rating (grade, authenticated_user_id, cultural_heritage_id) VALUES (5, 4, 1);
INSERT INTO rating (grade, authenticated_user_id, cultural_heritage_id) VALUES (5, 2, 2);

--COMMENTS
insert into comment (cultural_heritage_id, authenticated_user_id, content) values (1, 4, 'Duis at velit eu est congue elementum. In hac habitasse platea dictumst.');
insert into comment (cultural_heritage_id, authenticated_user_id, content) values (1, 3, 'Duis bibendum.');
