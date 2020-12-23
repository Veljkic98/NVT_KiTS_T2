-- CH TYPES  SIZE = 3
INSERT INTO chtype ( name) VALUES  ( 'manifestation');
INSERT INTO chtype ( name) VALUES ( 'institution');
INSERT INTO chtype ( name) VALUES ( 'cultural heritage');

--CH SUBTYPES  SIZE = 5
INSERT INTO chsubtype ( name, chtype_id) VALUES ( 'festival', 1);
INSERT INTO chsubtype ( name, chtype_id) VALUES ( 'fair', 1);
INSERT INTO chsubtype ( name, chtype_id) VALUES( 'museum', 2);
INSERT INTO chsubtype ( name, chtype_id) VALUES ( 'monument', 3);
INSERT INTO chsubtype ( name, chtype_id) VALUES ( 'landmark', 3);


INSERT INTO location (city, country, latitude, longitude) VALUES ( 'Louisville', 'Kentucky, USA', '38.256111', '-85.751389');

INSERT INTO cultural_heritage ( name, location_id, chsubtype_id, description) VALUES ( 'Venice Carnival', 1, 1,'The Carnival...');

INSERT INTO admin (id, first_name, last_name, email, password, approved) VALUES (1,'John', 'Smith', 'admin@gmail.com', '$2y$12$.PWVvztLIA1VBvOcpykig.CZPr78dwT3mVrtCda8YjjyNOas4P6j2', 'true');

insert into news (content, heading, cultural_heritage_id, admin_id) values ('sadrzajj', 'Naslov', 1, 1);
