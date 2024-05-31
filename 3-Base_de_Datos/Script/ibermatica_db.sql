DROP DATABASE IF EXISTS ibermatica_db;
CREATE DATABASE IF NOT EXISTS ibermatica_db;

USE ibermatica_db;

CREATE TABLE IF NOT EXISTS role (
    role_id INT(1),
    name ENUM('Oficinista', 'Obrero'),
    PRIMARY KEY(role_id)
);

INSERT INTO role (role_id, name)
VALUES (0, 'Oficinista'),
        (1, 'Obrero');

CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(10),
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    tlf_num INT(9) NOT NULL, -- All +34
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    register_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    type INT(1) NOT NULL, -- 0 Oficinista / 1 Obrero
    PRIMARY KEY(user_id),
    FOREIGN KEY(type) REFERENCES role(role_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO users (user_id, name, surname, email, tlf_num, username, password,  type)
VALUES ('78565234T', 'Test_employee', 'Test_employee', 'test_employee@gmail.com', 123456789, 'test_employee', 'Admin123',  1),
        ('68565324T', 'Admin', 'Admin', 'admin@gmail.com', 987654321, 'admin', 'Admin123',  0);

CREATE TABLE IF NOT EXISTS machines (
    serial_num VARCHAR(50),
    name VARCHAR(50) NOT NULL,
    adquisition_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status ENUM('operational', 'not_operational') NOT NULL,
    PRIMARY KEY(serial_num)
);

INSERT INTO machines (serial_num, name, adquisition_date, status)
VALUES ('637714620T', 'Tornos', '1995-10-10', 'operational'),
        ('184444622E', 'Embalaje', '2000-02-02', 'operational'),
        ('294677233P', 'Procesados', '2023-06-12',  'operational'),
        ('102457896L', 'Fresadoras', '2012-03-09', 'operational'),
        ('478965321N', 'Soldadura', '2008-01-04', 'operational'),
        ('960120450J', 'Lijadoras', '2004-08-20', 'operational'),
        ('002369017C', 'Fresadoras', '2017-11-30', 'operational'),
        ('789657890A', 'Hornos', '2013-03-20', 'operational');

CREATE TABLE IF NOT EXISTS reservation_machines (
    user_id VARCHAR(10) NOT NULL,
    serial_num VARCHAR(50),
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL ,
    end_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    reservation_id INT AUTO_INCREMENT NOT NULL,
    PRIMARY KEY(reservation_id),
    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY(serial_num) REFERENCES machines(serial_num) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO reservation_machines (user_id, serial_num, end_date)
VALUES ('78565234T', '637714620T', '2024-05-14 08:30:00');

CREATE TABLE IF NOT EXISTS reservation_machines_cancelled (
    user_id VARCHAR(10) NOT NULL,
    serial_num VARCHAR(50),
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    end_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL,
    reservation_id INT AUTO_INCREMENT NOT NULL,
    cancelled_date DATE NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY(reservation_id)
);

CREATE TABLE IF NOT EXISTS users_deleted (
    user_id VARCHAR(10),
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    tlf_num INT(9) NOT NULL, -- All +34
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    type INT(1) NOT NULL,
    deleted_date DATE NOT NULL DEFAULT current_timestamp(), -- 0 admin / 1 occasional_employee
    PRIMARY KEY(user_id),
    FOREIGN KEY(type) REFERENCES role(role_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS breakdowns (
    serial_num VARCHAR(50),
    name VARCHAR(50) NOT NULL,
    breakdown_day DATE NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY(serial_num)
);

-----------------------------------Trigger----------------------------------------------------------
DELIMITER $$

CREATE TRIGGER `resservation_cancelled`
AFTER DELETE ON `reservation_machines`
FOR EACH ROW
BEGIN
    INSERT INTO reservation_machines_cancelled (user_id,serial_num,start_date,end_date,reservation_id) VALUES (OLD.user_id,OLD.serial_num,OLD.start_date,OLD.end_date,OLD.reservation_id);
END
$$
DELIMITER ;

DELIMITER $$

CREATE TRIGGER `user_deleted`
AFTER DELETE ON `users`
FOR EACH ROW
BEGIN
    INSERT INTO users_deleted (user_id,name,surname,email,tlf_num,username,password,register_date,type) VALUES (OLD.user_id,OLD.name,OLD.surname,OLD.email,OLD.tlf_num,OLD.username,OLD.password,OLD.register_date,OLD.type);
END
$$
DELIMITER ;

DELIMITER $$

CREATE TRIGGER `machines_breakdowns`
AFTER UPDATE ON `machines`
FOR EACH ROW
BEGIN
    iF OLD.status <> NEW.status THEN
        iF NEW.status="not_operational" THEN
            INSERT INTO breakdowns (serial_num,name) VALUES (OLD.serial_num,OLD.name);
            Delete FROM reservation_machines WHERE serial_num=OLD.serial_num;
        END IF;
        iF NEW.status="operational" THEN
            Delete from breakdowns where serial_num=NEW.serial_num;
        END IF;
    END IF;
END
$$
DELIMITER ;



DROP USER IF EXISTS 'ibermaticaAdmin'@'localhost';
CREATE USER IF NOT EXISTS  'ibermaticaAdmin'@'localhost' IDENTIFIED BY 'Pa$$W0rd';
GRANT ALL PRIVILEGES ON ibermatica_db.* TO 'ibermaticaAdmin'@'localhost';

