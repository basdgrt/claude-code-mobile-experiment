-- Insert Formula 1 Teams for 2025 Season
INSERT INTO teams (name, base, team_chief, power_unit, first_entry, championships) VALUES
('Red Bull Racing', 'Milton Keynes, United Kingdom', 'Christian Horner', 'Honda RBPT', 2005, 6),
('Mercedes', 'Brackley, United Kingdom', 'Toto Wolff', 'Mercedes', 1970, 8),
('Ferrari', 'Maranello, Italy', 'Frédéric Vasseur', 'Ferrari', 1950, 16),
('McLaren', 'Woking, United Kingdom', 'Andrea Stella', 'Mercedes', 1966, 8),
('Aston Martin', 'Silverstone, United Kingdom', 'Mike Krack', 'Mercedes', 2021, 0),
('Alpine', 'Enstone, United Kingdom', 'Oliver Oakes', 'Renault', 1986, 2),
('Williams', 'Grove, United Kingdom', 'James Vowles', 'Mercedes', 1978, 9),
('RB', 'Faenza, Italy', 'Laurent Mekies', 'Honda RBPT', 2020, 0),
('Kick Sauber', 'Hinwil, Switzerland', 'Alessandro Alunni Bravi', 'Ferrari', 1993, 0),
('Haas F1 Team', 'Kannapolis, United States', 'Ayao Komatsu', 'Ferrari', 2016, 0);

-- Insert Formula 1 Drivers for 2025 Season
INSERT INTO drivers (first_name, last_name, driver_number, nationality, date_of_birth, team_id) VALUES
-- Red Bull Racing
('Max', 'Verstappen', 1, 'Dutch', '1997-09-30', (SELECT id FROM teams WHERE name = 'Red Bull Racing')),
('Liam', 'Lawson', 30, 'New Zealander', '2002-02-11', (SELECT id FROM teams WHERE name = 'Red Bull Racing')),

-- Mercedes
('George', 'Russell', 63, 'British', '1998-02-15', (SELECT id FROM teams WHERE name = 'Mercedes')),
('Andrea', 'Kimi Antonelli', 12, 'Italian', '2006-08-25', (SELECT id FROM teams WHERE name = 'Mercedes')),

-- Ferrari
('Charles', 'Leclerc', 16, 'Monégasque', '1997-10-16', (SELECT id FROM teams WHERE name = 'Ferrari')),
('Lewis', 'Hamilton', 44, 'British', '1985-01-07', (SELECT id FROM teams WHERE name = 'Ferrari')),

-- McLaren
('Lando', 'Norris', 4, 'British', '1999-11-13', (SELECT id FROM teams WHERE name = 'McLaren')),
('Oscar', 'Piastri', 81, 'Australian', '2001-04-06', (SELECT id FROM teams WHERE name = 'McLaren')),

-- Aston Martin
('Fernando', 'Alonso', 14, 'Spanish', '1981-07-29', (SELECT id FROM teams WHERE name = 'Aston Martin')),
('Lance', 'Stroll', 18, 'Canadian', '1998-10-29', (SELECT id FROM teams WHERE name = 'Aston Martin')),

-- Alpine
('Pierre', 'Gasly', 10, 'French', '1996-02-07', (SELECT id FROM teams WHERE name = 'Alpine')),
('Jack', 'Doohan', 7, 'Australian', '2003-01-20', (SELECT id FROM teams WHERE name = 'Alpine')),

-- Williams
('Alex', 'Albon', 23, 'Thai', '1996-03-23', (SELECT id FROM teams WHERE name = 'Williams')),
('Carlos', 'Sainz', 55, 'Spanish', '1994-09-01', (SELECT id FROM teams WHERE name = 'Williams')),

-- RB
('Yuki', 'Tsunoda', 22, 'Japanese', '2000-05-11', (SELECT id FROM teams WHERE name = 'RB')),
('Isack', 'Hadjar', 6, 'French', '2004-09-28', (SELECT id FROM teams WHERE name = 'RB')),

-- Kick Sauber
('Nico', 'Hulkenberg', 27, 'German', '1987-08-19', (SELECT id FROM teams WHERE name = 'Kick Sauber')),
('Gabriel', 'Bortoleto', 5, 'Brazilian', '2004-10-14', (SELECT id FROM teams WHERE name = 'Kick Sauber')),

-- Haas F1 Team
('Esteban', 'Ocon', 31, 'French', '1996-09-17', (SELECT id FROM teams WHERE name = 'Haas F1 Team')),
('Oliver', 'Bearman', 87, 'British', '2005-05-08', (SELECT id FROM teams WHERE name = 'Haas F1 Team'));
