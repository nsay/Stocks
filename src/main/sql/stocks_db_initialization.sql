/** create the stocks database */

DROP TABLE IF EXISTS quotes CASCADE;
DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS person_stocks CASCADE;

CREATE TABLE quotes(
   id INT NOT NULL AUTO_INCREMENT,
   symbol VARCHAR(4) NOT NULL,
   time DATETIME NOT NULL,
   price DECIMAL NOT NULL,
   PRIMARY KEY ( id )
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
   stock_symbol VARCHAR(4) NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (person_id) REFERENCES person (id)
);

INSERT INTO quotes (symbol,time,price) VALUES ('GOOG','2004-08-19 00:00:01','85.00');
INSERT INTO quotes (symbol,time,price) VALUES ('GOOG','2015-02-03 00:00:01','527.35');
INSERT INTO quotes (symbol,time,price) VALUES ('APPL','2000-01-01 00:00:01','118.27');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-10 00:00:01','363.21');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-11 00:01:01','465.21');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-12 00:02:01','250.21');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-13 00:03:01','251.21');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-14 00:04:01','253.21');

INSERT INTO person (id,first_name,last_name) VALUES ('0001','Monty','Python');
INSERT INTO person (id,first_name,last_name) VALUES ('0002','Monte','Cristo');

INSERT INTO person_stocks (id,person_id,stock_symbol) VALUES ('0001','0002','AAPL');
INSERT INTO person_stocks (id,person_id,stock_symbol) VALUES ('0002','0001','GOOG');
INSERT INTO person_stocks (id,person_id,stock_symbol) VALUES ('0003','0002','GOOG');
INSERT INTO person_stocks (id,person_id,stock_symbol) VALUES ('0004','0001','AMZN');