package org.example;

import java.util.ArrayList;

public class Company {
    private String companyName;
    private ArrayList<Employee> employees;

    public Company(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва компанії не може бути порожньою");
        }
        this.companyName = companyName;
        this.employees = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва компанії не може бути порожньою");
        }
        this.companyName = companyName;
    }

    public void addEmployee(Employee emp) {
        if (emp == null) {
            throw new IllegalArgumentException("Працівник не може бути null");
        }
        employees.add(emp);
    }

    public void removeEmployee(Employee emp) {
        employees.remove(emp);
    }

    public ArrayList<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("У компанії немає працівників.");
        } else {
            System.out.println("Компанія: " + companyName);
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}