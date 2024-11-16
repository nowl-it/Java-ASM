DROP DATABASE IF EXISTS programming_asm;

CREATE DATABASE IF NOT EXISTS programming_asm;

USE programming_asm;

CREATE TABLE
	IF NOT EXISTS employees (
		ID INT PRIMARY KEY AUTO_INCREMENT,
		employeeID VARCHAR(255) NOT NULL,
		firstName VARCHAR(255) NOT NULL,
		lastName VARCHAR(255) NOT NULL,
		gender ENUM ('M', 'F') NOT NULL,
		address VARCHAR(255) NOT NULL,
		email VARCHAR(255) NOT NULL,
		department VARCHAR(255) NOT NULL,
		birthDate DATE NOT NULL,
		status ENUM ('Working', 'Resigned') NOT NULL,
		salaryCoefficient DOUBLE NOT NULL,
		salary DOUBLE NOT NULL
	);
