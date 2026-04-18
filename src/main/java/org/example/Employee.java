package org.example;

import java.util.Objects;

public class Employee {
    private String name;
    private int id;
    private double salary;
    private String position;
    private String email;
    private int age;

    // Конструктор
    public Employee(String name, int id, double salary, String position, String email, int age) {
        setName(name);
        setId(id);
        setSalary(salary);
        setPosition(position);
        setEmail(email);
        setAge(age);
    }

    // Гетери та сетери
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ім'я не може бути порожнім");
        }
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID має бути додатним числом");
        }
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Зарплата не може бути від'ємною");
        }
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Посада не може бути порожньою");
        }
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email повинен містити символ '@'");
        }
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0 || age >= 100) {
            throw new IllegalArgumentException("Вік має бути між 1 та 99 роками");
        }
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Employee{name='%s', id=%d, salary=%.2f, position='%s', email='%s', age=%d}",
                name, id, salary, position, email, age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Double.compare(employee.salary, salary) == 0 && age == employee.age &&
                Objects.equals(name, employee.name) && Objects.equals(position, employee.position) &&
                Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, salary, position, email, age);
    }
}