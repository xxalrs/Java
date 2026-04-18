package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldThrowExceptionWhenInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("", 1, 1000.0, Position.DEVELOPER, "test@example.com", 25);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidId() {
        Employee emp = new Employee("John", 1, 1000.0, Position.MANAGER, "john@example.com", 30);
        assertThrows(IllegalArgumentException.class, () -> emp.setId(-5));
    }

    @Test
    void testCopyConstructor() {
        Employee original = new Employee("Alice", 10, 2500.0, Position.ANALYST, "alice@company.com", 29);
        Employee copy = new Employee(original);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getSalary(), copy.getSalary());
        assertEquals(original.getPosition(), copy.getPosition());
        assertEquals(original.getEmail(), copy.getEmail());
        assertEquals(original.getAge(), copy.getAge());
        assertNotSame(original, copy);
    }

    @Test
    void testStaticCounter() {
        int before = Employee.getTotalEmployees();
        Employee e1 = new Employee("Bob", 20, 3000.0, Position.HR, "bob@work.com", 35);
        Employee e2 = new Employee("Eve", 21, 3200.0, Position.DEVELOPER, "eve@work.com", 28);
        assertEquals(before + 2, Employee.getTotalEmployees());
    }

    @Test
    void testCompanyAggregation() {
        Company comp = new Company("TestCompany");
        Employee emp = new Employee("Tom", 30, 4000.0, Position.ADMINISTRATOR, "tom@test.com", 40);
        comp.addEmployee(emp);
        assertEquals(1, comp.getEmployeeCount());
        assertEquals("Tom", comp.getEmployees().get(0).getName());
    }

    @Test
    void testInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Jane", 5, 2000.0, null, "jane@test.com", 28);
        });
    }
}