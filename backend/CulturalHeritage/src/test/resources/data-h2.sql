INSERT INTO chtype (name) VALUES  ('masd');

INSERT INTO chsubtype (name, chtype_id) VALUES ('festival', 1);

INSERT INTO location (city, country, latitude, longitude) VALUES ('Louisville', 'Kentucky, USA', '38.256111', '-85.751389');

INSERT INTO cultural_heritage (name, location_id, chsubtype_id, description) VALUES ('CH1', 1, 1, 'The Carnival...');
INSERT INTO cultural_heritage (name, location_id, chsubtype_id, description) VALUES ('CH2', 1, 1, 'The Carnival...');

INSERT INTO admin (id, first_name, last_name, email, password, approved) VALUES (1,'John', 'Smith', 'admin@gmail.com', '$2y$12$.PWVvztLIA1VBvOcpykig.CZPr78dwT3mVrtCda8YjjyNOas4P6j2', 'true');

insert into news (content, heading, cultural_heritage_id, admin_id) values ('sadrzaj1', 'Naslov1', 1, 1);
insert into news (content, heading, cultural_heritage_id, admin_id) values ('sadrzaj2', 'Naslov2', 2, 1);
