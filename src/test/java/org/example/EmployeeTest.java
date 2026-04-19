package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class InheritanceTest {

    @Test
    void testContractEmployeeCreation() {
        ContractEmployee emp = new ContractEmployee("John", 10, 2000.0, Position.DEVELOPER, "john@test.com", 30, 12);
        assertEquals(12, emp.getContractDuration());
        assertTrue(emp.toString().contains("contractDuration=12"));
    }

    @Test
    void testFullTimeEmployeeCreation() {
        FullTimeEmployee emp = new FullTimeEmployee("Jane", 20, 3000.0, Position.MANAGER, "jane@test.com", 35, 500.0);
        assertEquals(500.0, emp.getBonus());
        assertTrue(emp.toString().contains("bonus=500.0"));
    }

    @Test
    void testPolymorphismInArrayList() {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("Base", 1, 1000.0, Position.INTERN, "base@test.com", 20));
        list.add(new ContractEmployee("Contract", 2, 1500.0, Position.DEVELOPER, "contract@test.com", 25, 6));
        list.add(new FullTimeEmployee("Full", 3, 2000.0, Position.ANALYST, "full@test.com", 30, 200.0));
        assertEquals(3, list.size());
        assertTrue(list.get(1).toString().contains("ContractEmployee"));
        assertTrue(list.get(2).toString().contains("FullTimeEmployee"));
    }
}