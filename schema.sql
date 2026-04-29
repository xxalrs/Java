CREATE TABLE IF NOT EXISTS employees (
    employee_id INT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    salary NUMERIC(10,2) NOT NULL,
    position VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    contract_duration INT,
    bonus NUMERIC(10,2),
    university VARCHAR(100),
    internship_duration INT,
    remote_tools VARCHAR(255),
    hourly_rate NUMERIC(10,2),
    quantity INT NOT NULL
    );