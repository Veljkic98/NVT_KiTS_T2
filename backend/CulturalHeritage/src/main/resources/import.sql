--TYPES
INSERT INTO chtype (id, name) VALUES  (1, 'manifestation');
INSERT INTO chtype (id, name) VALUES (2, 'institution');
INSERT INTO chtype (id, name) VALUES (3, 'cultural heritage');


-- SUBTYPES
INSERT INTO chsubtype (id, name, chtype_id) VALUES (1, 'festival', 1);
INSERT INTO chsubtype (id, name, chtype_id) VALUES (2, 'fair', 1);
INSERT INTO chsubtype (id, name, chtype_id) VALUES(3, 'museum', 2);
INSERT INTO chsubtype (id, name, chtype_id) VALUES (4, 'monument', 3);
INSERT INTO chsubtype (id, name, chtype_id) VALUES (5, 'landmark', 3);


--ADMIN
INSERT INTO admin (id, first_name, last_name, email, password, approved) VALUES (1, 'John', 'Smith', 'admin@gmail.com', 'admin', 'true');


--USERS
INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (1,'Helen', 'York', 'helen@hotmail.com', '123', 'true');
INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (2,'Sima', 'Matas', 'sima12@hotmail.com', '123', 'true');
INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (3,'Margene', 'Weatherwax', 'Weatherwax12@gmail.com', '123', 'true');
INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (4,'Everette', 'Desch', 'heldesch123en@hotmail.com', '123', 'true');
INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (5,'Doyle', 'Mormon', 'doyle@hotmail.com', '123', 'true');
INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (6,'Krysta', 'Brooking', 'krystbrooking@hotmail.com', '123', 'true');
INSERT INTO authenticated_user (id, first_name, last_name, email, password) VALUES (7,'Ossie', 'Dudas', 'ossie12@hotmail.com', '123');
INSERT INTO authenticated_user (id, first_name, last_name, email, password) VALUES (8,'Hilario', 'Elliot', 'elliorhilario@hotmail.com', '123');
INSERT INTO authenticated_user (id, first_name, last_name, email, password, approved) VALUES (9,'Inell', 'Becton', 'Inell@hotmail.com', '123', 'false');


--LOCATIONS 
    --FESTIVALS
INSERT INTO location (id, city, country, latitude, longitude) VALUES (1, 'Venice', 'Italy', '45.438759', '12.327145');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (2, 'Valencia', 'Spain', '39.466667', '-0.375000');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (3, 'New Orleans', ' Louisiana', '29.951065', '-90.071533');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (4, 'Park City', 'Utah', '40.646061', '-111.497971');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (5, 'Toronto', 'Canada', '43.653225', '-79.383186');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (6, 'Amsterdam', 'Netherlands', '52.370216', '4.895168');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (7, 'Oxford', 'England', '51.752022', '-1.257726');
INSERT INTO location (id, city, country, latitude, longitude) VALUES  (8, 'Bordeaux', 'France', '44.836151', '-0.580816');
    --MUSEUMS
INSERT INTO location (id, city, country, latitude, longitude) VALUES (9, 'Paris', 'France', '48.864716', '2.349014');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (10, 'Beijing', 'China', '39.916668', '116.383331');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (11, 'Washington', 'US', '47.751076', ' -120.740135');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (12, 'Washington', 'US', '47.751076', ' -120.740135');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (13, 'London', 'England', '51.509865', '-0.118092');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (14, 'London', 'England', '51.509865', '-0.118092');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (15, 'New York', 'US', '40.730610', '-73.935242');
INSERT INTO location (id, city, country, latitude, longitude) VALUES (16, 'Taipei', 'Taiwan', '25.105497', '121.597366');

--CULTURAL HERITAGES 
    --FESTIVALS CHSUBTYPE_ID = 1
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (1, 'Venice Carnival', 1, 1,'The Carnival of Venice (Italian: Carnevale di Venezia) is an annual festival, held in Venice, Italy. The Carnival starts forty days before Easter and ends on Shrove Tuesday (Fat Tuesday or Martedì Grasso), the day before Ash Wednesday. Dove il gabinetto! In other words, At a carnival, every joke is disgraced!');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (2, 'La Tomatina Festival',2, 1, 'La Tomatina is a festival that is held in the Valencian town of Buñol, a town located 30 km inland from the Mediterranean Sea in which participants throw tomatoes and get involved in this tomato fight purely for fun. It is held on the last Wednesday of August, during the week of festivities of Buñol.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (3, 'New Orleans Jazz Festival', 3, 1, 'The New Orleans Jazz & Heritage Festival, often known as Jazz Fest, is an annual celebration of the music and culture of New Orleans and Louisiana. Use of the term "Jazz Fest" can also include the days surrounding the Festival and the many shows at unaffiliated New Orleans nightclubs scheduled during the Festival event weekends.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (4, 'Sundance Film Festival', 4, 1, 'The Sundance Film Festival is an American film festival that takes place annually in Utah. It is the largest independent cinema festival in the United States. Held in January in Park City, Salt Lake City, and Ogden, as well as at the Sundance Resort, the festival is a showcase for new work from American and international independent filmmakers.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (5, 'Toronto International Film Festival', 5, 1, 'The Toronto International Film Festival (TIFF) is a publicly attended film festival held each September in Toronto, Ontario, Canada.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (6, 'Amsterdam Gay Pride', 6, 1, 'The Amsterdam Pride is an annual gay festival in the centre of Amsterdam, organized in the first weekend of August. With several hundreds of thousands visitors this event is one of the largest public events of the Netherlands. The pride is organized since 1996. The peak of the festival is during the canal parade, a parade of boats of large variety on the first Saturday of August, which usually goes from Westerdok over the Prinsengracht, Amstel river, Zwanenburgwal and Oudeschans to Oosterdok.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (7, 'Oxford Literary Festival', 7, 1, 'The Sunday Times Oxford Literary Festival is an annual literary festival where visitors can meet and listen to authors and experts from a wide range of fields discussing a variety of topics from literature, politics, history, philosophy, economics, science, culinary, travel, environment and religion, to mention only a few.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (8, 'Bordeaux Wine Festival', 8, 1, 'This festival, organised by the town of Bordeaux, is an opportunity for fans of fine wine, good food and culture to enjoy a great festive moment. It will be a celebration of the senses on the largest square in Europe, right on the banks of the Garonne. The riverbanks and the Esplanade des Quinconces will welcome more than 300 000 gastronomers and culture-vultures throughout this four-day event.');


    --MUSEUMS CHSUBTYPE_ID = 3
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (9, 'The Louvre', 9, 3, 'The Louvre holds strong at number one with around 9.3 million visitors annually. Of those guests, 30% are domestic residents, typically visiting temporary exhibitions, and 70% are international attendees visiting the Paris landmark and the permanent exhibits. The Louvre houses masterpiece artworks like Leonardo Da Vinci’s ‘Mona Lisa’, the ‘Venus de Milo’ sculpture of Aphrodite, and what is considered to be the finest diamond in the world, the ‘Regent’. As one of the world’s largest museums, the Louvre houses around 70,000 pieces of art in its 650,000 square feet of gallery space.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (10, 'National Museum of China', 10, 3, 'The National Museum of China in Beijing comes in at number two, with 7.6 million visitors in 2014. The free museum was founded in 2003 when two museums (the National Museum of Chinese History and the National Museum of Chinese Revolution) were merged, and it is conveniently located in Tiananmen Square. The museum is now one of the largest in the world, covering 192,000 square meters. ');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (11, 'National Museum of Natural History', 11, 3, 'Part of the Smithsonian Institution in Washington DC, the National Museum of Natural History is the most visited museum in the United States and the most visited natural history museum in the world with 7.3 million visitors in 2014. The free museum opened in 1910 and was one of the first Smithsonian buildings. It houses more than 126 million natural science specimens and cultural artefacts, including 30 million pinned insects, 4.5 million pressed plants');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (12, 'National Air and Space Museum ', 12, 3, 'The National Air and Space Museum is the largest collection of artefacts of human flight, and also the largest of all 19 museums included in the Smithsonian Institution. In 2014 the DC museum saw 6.7 million visitors, but combined with its second location in Virginia, there were around 8 million visitors. The wonder and awe of human flight will capture anyone’s heart after wandering through this free museum. Included in the museum is the oldest known photo of an aircraft, the 1903 Wright Flyer, the space shuttle Discovery, and much more.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (13, 'British Museum', 13, 3, 'Founded in 1753, the British Museum was the first national public museum in the world, and today it is Britain’s most-visited with 6.7 million visitors in 2014. Entry has always been free at the British Museum. Some of the museum’s most important and sought-after acquisitions include the Rosetta Stone, Parthenon sculptures, and ancient Egyptian structures. ');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (14, 'The National Gallery', 14, 3, 'The National Gallery is the largest art gallery in the UK, and in 2014 6.4 million visitors came to see the collection of works from the 13th to the 19th centuries of Western European art. The gallery, which is free to the public, opened in 1838 in the center of ');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (15, 'The Metropolitan Museum of Art', 15, 3, 'The largest and most popular art gallery in the United States is The Metropolitan Museum of Art in New York City, commonly known as The Met, which had 6.3 million visitors in 2014. The Met’s permanent collection holds more than 2 million works spanning 5,000 years from ancient Egypt to Europe’s classics to American and modern work. The main building was founded in 1870 and is located on Central Park along Manhattan’s Museum Mile.');
INSERT INTO cultural_heritage (id, name, location_id, chsubtype_id, description) VALUES (16, 'National Palace Museum', 16, 3, 'The National Palace Museum in Taiwan boasted 5.4 million visitors in 2014 who came to view the collection of ancient Chinese art and artefacts. The museum’s permanent collection includes around 700,000 works of art. This museum shares a history with the Palace Museum in Beijing, the old Palace Museum split in two after the Chinese Civil War when the Republic of China of Taiwan was formed. The collection includes paintings, jades, ceramics, bronzes, rare books, calligraphy, and more.');