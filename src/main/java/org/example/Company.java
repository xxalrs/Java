package org.example;

import java.util.ArrayList;

public class Company {
    private String name;
    private String address;
    private ArrayList<EmployeeEntry> employees;

    public Company(String name, String address) {
        setName(name);
        setAddress(address);
        this.employees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва компанії не може бути порожньою");
        }
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Адреса не може бути порожньою");
        }
        this.address = address;
    }

    public void addNewEmployee(Employee emp, int quantity) {
        if (emp == null) {
            throw new IllegalArgumentException("Працівник не може бути null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути додатною");
        }
        for (EmployeeEntry entry : employees) {
            if (entry.getEmployee().equals(emp)) {
                entry.setQuantity(entry.getQuantity() + quantity);
                return;
            }
        }
        employees.add(new EmployeeEntry(emp, quantity));
    }

    public ArrayList<EmployeeEntry> searchByName(String namePart) {
        ArrayList<EmployeeEntry> result = new ArrayList<>();
        if (namePart == null) return result;
        String lowerName = namePart.toLowerCase();
        for (EmployeeEntry entry : employees) {
            if (entry.getEmployee().getName().toLowerCase().contains(lowerName)) {
                result.add(entry);
            }
        }
        return result;
    }

    public ArrayList<EmployeeEntry> searchById(int id) {
        ArrayList<EmployeeEntry> result = new ArrayList<>();
        for (EmployeeEntry entry : employees) {
            if (entry.getEmployee().getId() == id) {
                result.add(entry);
            }
        }
        return result;
    }

    public ArrayList<EmployeeEntry> searchByPosition(Position position) {
        ArrayList<EmployeeEntry> result = new ArrayList<>();
        if (position == null) return result;
        for (EmployeeEntry entry : employees) {
            if (entry.getEmployee().getPosition().equals(position)) {
                result.add(entry);
            }
        }
        return result;
    }

    public ArrayList<EmployeeEntry> getAllEntries() {
        return new ArrayList<>(employees);
    }

    public int getTotalEmployeesCount() {
        int total = 0;
        for (EmployeeEntry entry : employees) {
            total += entry.getQuantity();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Company{name='" + name + "', address='" + address + "', employees=" + employees.size() + " entries}";
    }
}