CREATE TABLE COMPONENT (
	modelno INTEGER Primary key, 
	kind CHARACTER(20), 
	price FLOAT, 
	title CHARACTER(50), 
	currentstock INTEGER, 
	minimuminventory INTEGER, 
	prefamtafterrestock INTEGER
);

CREATE TABLE MAINBOARD(
	modelno INTEGER Primary key REFERENCES Component(modelno), 
	formfactor CHARACTER(20),
	price DOUBLE PRECISION, 
	onboardgraphics BOOLEAN,
	ramtype CHARACTER(20), 
	cpusocket CHARACTER(20)
);

CREATE TABLE CASES(
	modelno INTEGER Primary key REFERENCES Component(modelno), 
	formfactor CHARACTER(20)
);

CREATE TABLE CPU(
	modelno INTEGER Primary key REFERENCES Component(modelno), 
	socket CHARACTER(20),
	busspeed INTEGER
);

CREATE TABLE RAM(
	modelno INTEGER Primary key REFERENCES Component(modelno), 
	RamType CHARACTER(20), 
	busspeed INTEGER 
	
);

CREATE TABLE GraphicsCards(
	modelno INTEGER Primary key REFERENCES Component(modelno)
);

CREATE TABLE ComputerSystems(
	title CHARACTER(30) Primary key,
	MainBoard INTEGER,
	CPU INTEGER, 
	RAM INTEGER, 
	GPU INTEGER,
	CASES INTEGER
);

INSERT INTO Component (modelno, kind, price, title, currentstock, minimuminventory, prefamtafterrestock)
VALUES 
(1001, 'mainboard', 790.50, 'MaDaBard', 3, 10, 20),
(1002, 'mainboard', 916.67, 'Silver Surfer', 25, 5, 5),
(1003, 'mainboard', 1047.12, 'Silver Surfer', 0, 3, 5),
(1004, 'mainboard', 865.8, 'MSI 970 GAMING', 15, 7, 15),
(1005, 'mainboard', 1684.33, 'SUPER MEGA BYTE MF-1337', 1, 1, 1),
(1006, 'mainboard', 999.95, 'ASRock H97M PRO4', 4, 5, 8),
(1007, 'mainboard', 1200.41, 'TRUMP Motherboard', 3, 5, 8),
(1008, 'mainboard', 790.50, 'MSI H81M-P33', 7, 5, 8),
(2000, 'Case', 150, 'CompCase', 20, 15, 20),
(2001, 'Case', 165, 'CaseCase', 7, 5, 12),
(2002, 'Case', 120, 'Black Box', 1, 0, 5),
(2003, 'Case', 185, 'Box Deluxe', 12, 2, 20),
(2004, 'Case', 250, 'MoneyMaker', 34, 15, 55),
(2005, 'Case', 303, 'CrapCase', 9, 10, 18),
(2006, 'Case', 304, 'Box of Case', 3, 10, 20),
(2007, 'Case', 666, 'PCmasterCase', 1, 1, 1),
(3001, 'CPU', 2799.95, 'CAMEL ELEKTRA', 12, 10, 20),
(3002, 'CPU', 1599.95, 'ALLAHU CPU', 5, 5, 10),
(3003, 'CPU', 6999.95, 'UNLIMITED POWER!', 1, 3, 5),
(3004, 'CPU', 3199.95, 'SUPA LEGEND CPU', 0, 8, 15),
(3005, 'CPU', 1999.95, 'TYRIONS D', 1, 5, 10),
(3006, 'CPU', 999.95, 'CrapCPU', 4, 5, 10),
(3007, 'CPU', 1199.95, 'TRUMP PROCESS0R', 3, 10, 20),
(3008, 'CPU', 4399.95, 'EXCCCPCPU', 7, 5, 10),
(5005, 'ram', 90.21, 'Oldram 2gb', 17, 5, 10),
(5006, 'ram', 160.40, 'Oldram 4gb', 13, 5, 10),
(5007, 'ram', 100.40, 'goodoldram 2gb', 14, 5, 15),
(5008, 'ram', 180.30, 'goodoldram 4gb', 11, 5, 15),
(5009, 'ram', 190.50, 'Kingston Valueram 4gb', 13, 5, 12),
(5010, 'ram', 240.12, 'Kingston Valueram 8gb', 7, 5, 12),
(5011, 'ram', 210.13, 'Kingston  KTA 4gb', 9, 5, 15),
(5012, 'ram', 286.50, 'Kingston KTA 8gb', 15, 10, 15),
(5013, 'ram', 280.96, 'Crucial Ballistix 8gb', 13, 10, 20),
(5014, 'ram', 520.11, 'Crucial Ballistix 16gb', 10, 10, 20),
(5015, 'ram', 300.19, 'Crucial Balls 8gb', 17, 10, 25),
(5016, 'ram', 540.97, 'Crucial Balls 16gb', 19, 10, 25),
(5017, 'ram', 340.78, 'Crucial Super-Hype 8gb', 13, 15, 30),
(5018, 'ram', 600.00, 'Crucial Super-Hype 16gb', 25, 15, 30),
(5019, 'ram', 630.12, 'Crucial Mega-Hype 16gb', 29, 15, 30),
(5020, 'ram', 900.12, 'Crucial Mega-Hype 32gb', 16, 15, 30),
(5021, 'ram', 286.50, 'Trumpram 4gb', 13, 5, 10),
(5022, 'ram', 600.12, 'Trumpram 8gb', 11, 5, 10),
(5023, 'ram', 1200.22, 'Trumpram 16gb', 14, 5, 10),
(5024, 'ram', 2200.67, 'Trumpram 32gb', 15, 5, 10),
(5025, 'ram', 4000.90, 'Trumpram 64gb', 12, 5, 10),
(5026, 'ram', 1300.12, 'Batteringram 16gb', 11, 5, 10),
(5027, 'ram', 2400.78, 'Batteringram 32gb', 15, 5, 10),
(5028, 'ram', 5000.00, 'Batteringram 64gb', 11, 5, 10),
(6001, 'Graphics Card', 2699.95, 'MSI GeForce GTX 970 Gaming 4GB', 17, 10, 25),
(6002, 'Graphics Card', 1899.95, 'MSI GeForce GTX 960 Gaming 4GB', 19, 10, 25),
(6003, 'Graphics Card', 4099.95, 'MSI GeForce GTX 980 Gaming 4GB', 15, 10, 25),
(6004, 'Graphics Card', 5599.95, 'MSI GeForce GTX 980 Ti Gaming 6GB', 12, 10, 25),
(6005, 'Graphics Card', 1299.95, 'MSI Radeon R7 370 Gaming 2GB GDDR5', 14, 10, 20),
(6006, 'Graphics Card', 1749.95, 'XFX Radeon R9 380 2GB GDDR5', 16, 10, 20),
(6007, 'Graphics Card', 3599.95, 'MSI Radeon R9 390X Gaming 8GB GDDR5', 11, 10, 20),
(6008, 'Graphics Card', 5499.95, 'XFX Radeon R9 FURY X 4GB HBM', 9, 10, 20);

INSERT INTO GraphicsCards(modelno)
VALUES
(6001),
(6002),
(6003),
(6004),
(6005),
(6006),
(6007),
(6008);

INSERT INTO ComputerSystems(
VALUES
('System1',1001,3001,5005,6001,2000),
('System2',1002,3002,5006,6002,2001),
('System3',1003,3003,5007,6003,2002),
('System4',1004,3004,5008,6004,2003),
('System5',1005,3005,5009,6005,2004),
('System6',1006,3006,5010,6006,2005),
('System7',1007,3007,5011,6007,2006),
('System8',1008,3008,5012,6008,2007));

INSERT INTO Mainboard (modelno, formfactor, onboardgraphics, ramtype, cpusocket)
VALUES 
(1001, 'ATX', TRUE, 'DDR2', 'FM2'),
(1002, 'ATX', TRUE, 'DDR3', 'LGA 1150'),
(1003, 'FlexATX', FALSE, 'DDR4', 'AM3+'),
(1004, 'FlexATX', FALSE, 'DDR3', 'FM2'),
(1005, 'XL-ATX', FALSE, 'DDR2', 'LGA 1150'),
(1006, 'FlexATX', TRUE, 'DDR4', 'LGA 1150'),
(1007, 'ATX', TRUE, 'DDR3', 'FM2'),
(1008, 'XL-ATX', FALSE, 'DDR4', 'AM3+');

INSERT INTO Cases(modelno, formfactor)
Values
(2000, 'ATX'), 
(2001, 'FlexATX'),
(2002, 'FlexATX'),
(2003, 'FlexATX'),
(2004, 'XL-ATX'),
(2005, 'FlexATX'),
(2006, 'ATX'),
(2007, 'XL-ATX');

INSERT INTO CPU (modelno, socket, busspeed)
Values
(3001, 'FM2', 475), 
(3002, 'LGA 1150', 400),
(3003, 'AM3+', 625),
(3004, 'FM2', 500),
(3005, 'LGA 1150', 350),
(3006, 'LGA 1150', 250),
(3007, 'FM2', 300),
(3008, 'AM3+', 525);

INSERT INTO Ram (modelno, ramtype, busspeed)
VALUES
(5005, 'DDR2', 475),
(5006, 'DDR3', 400),
(5007, 'DDR4', 625),
(5008, 'DDR3', 500),
(5009, 'DDR2', 350),
(5010, 'DDR4', 250),
(5011, 'DDR3', 300),
(5012, 'DDR4', 525),
(5013, 'DDR3', 1600),
(5014, 'DDR3', 1600),
(5015, 'DDR3', 1866),
(5016, 'DDR3', 1866),
(5017, 'DDR3', 2133),
(5018, 'DDR3', 2133),
(5019, 'DDR3', 2400),
(5020, 'DDR3', 2400),
(5021, 'DDR4', 2666),
(5022, 'DDR4', 2666),
(5023, 'DDR4', 3000),
(5024, 'DDR4', 3000),
(5025, 'DDR4', 3200),
(5026, 'DDR4', 3200),
(5027, 'DDR4', 3500),
(5028, 'DDR4', 3500);

