package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldThrowExceptionWhenInvalidNameInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("", 1, 1000.0, "Developer", "test@example.com", 25);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidIdInSetter() {
        Employee emp = new Employee("John", 1, 1000.0, "Developer", "john@example.com", 30);
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setId(-5);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidSalaryInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Jane", 2, -100.0, "Manager", "jane@example.com", 35);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidEmailInSetter() {
        Employee emp = new Employee("Bob", 3, 2000.0, "Analyst", "bob@work.com", 28);
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setEmail("invalid-email");
        });
    }

    @Test
    void shouldCreateValidEmployee() {
        Employee emp = new Employee("Alice", 10, 2500.0, "Engineer", "alice@company.com", 29);
        assertEquals("Alice", emp.getName());
        assertEquals(10, emp.getId());
        assertEquals(2500.0, emp.getSalary());
        assertEquals("Engineer", emp.getPosition());
        assertEquals("alice@company.com", emp.getEmail());
        assertEquals(29, emp.getAge());
    }
}