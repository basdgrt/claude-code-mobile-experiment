-- Create teams table
CREATE TABLE teams (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    base VARCHAR(100) NOT NULL,
    team_chief VARCHAR(100) NOT NULL,
    power_unit VARCHAR(50) NOT NULL,
    first_entry INTEGER,
    championships INTEGER,
    version BIGINT DEFAULT 0,
    CONSTRAINT uk_team_name UNIQUE (name)
);

-- Create drivers table
CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    driver_number INTEGER NOT NULL UNIQUE,
    nationality VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    team_id BIGINT NOT NULL,
    version BIGINT DEFAULT 0,
    CONSTRAINT uk_driver_number UNIQUE (driver_number),
    CONSTRAINT fk_driver_team FOREIGN KEY (team_id) REFERENCES teams(id)
);

-- Create indexes for better query performance
CREATE INDEX idx_drivers_team_id ON drivers(team_id);
CREATE INDEX idx_drivers_last_name ON drivers(last_name);
CREATE INDEX idx_teams_name ON teams(name);
