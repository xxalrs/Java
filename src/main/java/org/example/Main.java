package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість працівників: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            System.out.println("\nПрацівник №" + (i + 1));
            System.out.print("Ім'я: ");
            String name = scanner.nextLine();
            System.out.print("ID: ");
            int id = scanner.nextInt();
            System.out.print("Зарплата: ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            Employee emp = new Employee(name, id, salary);
            employees.add(emp);
        }

        System.out.println("\nСписок працівників");
        for (Employee emp : employees) {
            System.out.println(emp);
        }

        scanner.close();
    }
}