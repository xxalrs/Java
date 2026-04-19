package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class ExtendedHierarchyTest {

    @Test
    void testInternEmployeeCreation() {
        InternEmployee intern = new InternEmployee(
                "Anna", 101, 5000.0, Position.INTERN, "anna@test.com", 20,
                "KPI", 6
        );
        assertEquals("KPI", intern.getUniversity());
        assertEquals(6, intern.getInternshipDuration());
        assertTrue(intern.toString().contains("InternEmployee"));
    }

    @Test
    void testRemoteEmployeeCreation() {
        RemoteEmployee remote = new RemoteEmployee(
                "Oleg", 102, 7000.0, Position.DEVELOPER, "oleg@test.com", 28,
                "Slack, Zoom, Jira", 25.5
        );
        assertEquals("Slack, Zoom, Jira", remote.getRemoteTools());
        assertEquals(25.5, remote.getHourlyRate());
        assertTrue(remote.toString().contains("RemoteEmployee"));
    }

    @Test
    void testAllTypesInOneList() {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("Base", 1, 1000, Position.HR, "base@test.com", 30));
        list.add(new ContractEmployee("Contract", 2, 2000, Position.DEVELOPER, "con@test.com", 25, 12));
        list.add(new FullTimeEmployee("Full", 3, 3000, Position.MANAGER, "full@test.com", 40, 500));
        list.add(new InternEmployee("Intern", 4, 400, Position.INTERN, "int@test.com", 19, "LNU", 3));
        list.add(new RemoteEmployee("Remote", 5, 5000, Position.ANALYST, "rem@test.com", 35, "Teams", 30.0));
        assertEquals(5, list.size());
        assertTrue(list.get(3) instanceof InternEmployee);
        assertTrue(list.get(4) instanceof RemoteEmployee);
    }
}