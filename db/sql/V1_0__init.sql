USE back;

CREATE TABLE IF NOT EXISTS `back`.`category` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    active BOOLEAN NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS `back`.`event` (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    address VARCHAR(200) NOT NULL,
    description VARCHAR(400) NOT NULL,
    latitude VARCHAR(30) NOT NULL,
    longitude VARCHAR(30) NOT NULL,
    category_id INT UNSIGNED NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT `fk_event_category`
        FOREIGN KEY(category_id)
        REFERENCES back.category(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO back.category (name, created_at, active)
    VALUES ("Corte de agua", NOW(), 1), ("Corte de luz;", NOW(), 1);

INSERT INTO back.event(name, address, description, latitude, longitude, category_id)
    VALUES ("Mantenimiento", "Zona 12", "", "14.622784025555436", "-90.55425550456252", 1);