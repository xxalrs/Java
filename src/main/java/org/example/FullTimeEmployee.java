package org.example;

public class FullTimeEmployee extends Employee {
    private double bonus; // річна премія

    public FullTimeEmployee(String name, int id, double salary, Position position, String email, int age, double bonus) {
        super(name, id, salary, position, email, age);
        setBonus(bonus);
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Премія не може бути від'ємною");
        }
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "FullTimeEmployee{" +
                "name='" + getName() + '\'' +
                ", id=" + getId() +
                ", salary=" + getSalary() +
                ", position=" + getPosition() +
                ", email='" + getEmail() + '\'' +
                ", age=" + getAge() +
                ", bonus=" + bonus +
                "}";
    }
}