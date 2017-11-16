


DROP DATABASE stocks;
CREATE DATABASE stocks;
USE stocks;

DROP TABLE IF EXISTS quotes CASCADE;
DROP TABLE IF EXISTS person_stocks CASCADE;
DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS stock_symbols CASCADE;

CREATE TABLE stock_symbols(
  id INT NOT NULL AUTO_INCREMENT,
  symbol VARCHAR(4) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE quotes(
  id INT NOT NULL AUTO_INCREMENT,
  symbol_id INT NOT NULL,
  time TIMESTAMP NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (symbol_id) REFERENCES stock_symbols (id)
);

CREATE TABLE person(
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(256) NOT NULL,
  last_name VARCHAR(256) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE person_stocks(
  id INT NOT NULL AUTO_INCREMENT,
  person_id INT NOT NULL,
  symbol_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (person_id) REFERENCES person (id),
  FOREIGN KEY (symbol_id) REFERENCES stock_symbols (id)
);

INSERT INTO stock_symbols (symbol) VALUES ('GOOG');
SET @stock_symbols_last_value = LAST_INSERT_ID();
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-09 17:00:01','728.58');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-10 17:00:01','719.41');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-13 17:00:01','718.36');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-14 17:00:01','718.27');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-15 17:00:01','718.92');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-16 17:00:01','710.36');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-17 17:00:01','691.72');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-20 17:00:01','693.71');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-21 17:00:01','695.94');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-22 17:00:01','697.46');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-23 17:00:01','701.87');

INSERT INTO person (first_name,last_name) VALUES ('Jane','Doe');
SET @person_last_value = LAST_INSERT_ID();
INSERT INTO person_stocks (person_id,symbol_id) VALUES (@person_last_value,@stock_symbols_last_value);

INSERT INTO stock_symbols (symbol) VALUES ('AAPL');
SET @stock_symbols_last_value = LAST_INSERT_ID();
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-09 17:00:01','99.65');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-10 17:00:01','98.83');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-13 17:00:01','97.34');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-14 17:00:01','97.46');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-15 17:00:01','97.14');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-16 17:00:01','97.55');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-17 17:00:01','95.33');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-20 17:00:01','95.10');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-21 17:00:01','95.91');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-22 17:00:01','95.55');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-23 17:00:01','96.10');

INSERT INTO person_stocks (person_id,symbol_id) VALUES (@person_last_value,@stock_symbols_last_value);

INSERT INTO person (first_name,last_name) VALUES ('John','Doe');
SET @person_last_value = LAST_INSERT_ID();
INSERT INTO person_stocks (person_id,symbol_id) VALUES (@person_last_value,@stock_symbols_last_value);

INSERT INTO stock_symbols (symbol) VALUES ('AMZN');
SET @stock_symbols_last_value = LAST_INSERT_ID();
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-09 17:00:01','727.65');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-10 17:00:01','717.91');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-13 17:00:01','715.24');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-14 17:00:01','719.30');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-15 17:00:01','714.26');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-16 17:00:01','717.51');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-17 17:00:01','706.39');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-20 17:00:01','714.01');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-21 17:00:01','715.82');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-22 17:00:01','710.60');
INSERT INTO quotes (symbol_id,time,price) VALUES (@stock_symbols_last_value,'2016-06-23 17:00:01','722.08');

INSERT INTO person_stocks (person_id,symbol_id) VALUES (@person_last_value,@stock_symbols_last_value);