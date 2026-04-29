package org.example;

public class EmployeeEntry {
    private Employee employee;
    private int quantity;

    public EmployeeEntry(Employee employee, int quantity) {
        setEmployee(employee);
        setQuantity(quantity);
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Працівник не може бути null");
        }
        this.employee = employee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути додатною");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return employee.toString() + " | quantity=" + quantity;
    }
}