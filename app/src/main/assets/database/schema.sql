DROP TABLE IF EXISTS usuaris;
DROP TABLE IF EXISTS compra;
DROP TABLE IF EXISTS producte;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS producte_tag;
DROP TABLE IF EXISTS historialProducte;
DROP TABLE IF EXISTS compra_historialProducte;
DROP TABLE IF EXISTS ws_data;

CREATE TABLE usuaris(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	email TEXT NOT NULL,
	hash_password TEXT NOT NULL,
	genere INTEGER,
	nom TEXT,
	edat INTEGER,
	rol INTEGER,
	logged_in INTEGER
);

CREATE TABLE compra(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	id_usuari INTEGER,
	data INTEGER,
	FOREIGN KEY (id_usuari) REFERENCES usuaris(id)
);

CREATE TABLE producte(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	id_remot INTEGER,
	nom TEXT,
	descripcio TEXT,
	preu REAL,
	actiu INTEGER,
	stock INTEGER,
	imatge TEXT
);

CREATE TABLE tag(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	nom TEXT
);

CREATE TABLE historialProducte(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	nom TEXT,
	preu REAL
);

CREATE TABLE compra_historialProducte(
	id_compra INTEGER NOT NULL,
	id_historialProducte INTEGER NOT NULL,
	quantitat INTEGER,
	preu REAL,
	FOREIGN KEY (id_compra) REFERENCES compra(id),
	FOREIGN KEY (id_historialProducte) REFERENCES historialProducte(id)
); 

CREATE TABLE producte_tag(
	id_tag INTEGER NOT NULL,
	id_producte INTEGER NOT NULL,
	FOREIGN KEY (id_tag) REFERENCES tag(id),
	FOREIGN KEY (id_producte) REFERENCES producte(id)
);

CREATE TABLE ws_data(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	data INTEGER,
	nomTaula TEXT
);
