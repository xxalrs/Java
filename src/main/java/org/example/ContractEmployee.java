package org.example;

public class ContractEmployee extends Employee {
    private int contractDuration; // тривалість контракту в місяцях

    public ContractEmployee(String name, int id, double salary, Position position, String email, int age, int contractDuration) {
        super(name, id, salary, position, email, age);
        setContractDuration(contractDuration);
    }

    public int getContractDuration() {
        return contractDuration;
    }

    public void setContractDuration(int contractDuration) {
        if (contractDuration <= 0) {
            throw new IllegalArgumentException("Тривалість контракту має бути додатною");
        }
        this.contractDuration = contractDuration;
    }

    @Override
    public String toString() {
        return "ContractEmployee{" +
                "name='" + getName() + '\'' +
                ", id=" + getId() +
                ", salary=" + getSalary() +
                ", position=" + getPosition() +
                ", email='" + getEmail() + '\'' +
                ", age=" + getAge() +
                ", contractDuration=" + contractDuration +
                "}";
    }
}