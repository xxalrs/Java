package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Company company;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Введіть назву компанії: ");
        String companyName = scanner.nextLine();
        try {
            company = new Company(companyName);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage() + ". Використано назву за замовчуванням 'Невідома компанія'");
            company = new Company("Невідома компанія");
        }

        while (true) {
            printMenu();
            int choice = readIntInput("Ваш вибір: ");
            switch (choice) {
                case 1:
                    createNewEmployee();
                    break;
                case 2:
                    company.displayAllEmployees();
                    break;
                case 3:
                    demonstrateCopyConstructor();
                    break;
                case 4:
                    showStaticCounter();
                    break;
                case 5:
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== МЕНЮ =====");
        System.out.println("1. Створити нового працівника і додати в компанію");
        System.out.println("2. Вивести всіх працівників компанії");
        System.out.println("3. Демонстрація конструктора копіювання");
        System.out.println("4. Показати загальну кількість створених працівників (статичний лічильник)");
        System.out.println("5. Вийти");
    }

    private static void createNewEmployee() {
        System.out.println("\nСтворення нового працівника");
        String name = readNonEmptyString("Ім'я: ");
        int id = readPositiveInt("ID (додатне число): ");
        double salary = readNonNegativeDouble("Зарплата (>=0): ");
        Position position = readPosition("Посада (DEVELOPER, MANAGER, ANALYST, HR, ADMINISTRATOR, INTERN): ");
        String email = readEmail("Email (має містити '@'): ");
        int age = readAge("Вік (1-99): ");

        try {
            Employee emp = new Employee(name, id, salary, position, email, age);
            company.addEmployee(emp);
            System.out.println("Працівника успішно додано до компанії!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка при створенні: " + e.getMessage());
        }
    }

    private static void demonstrateCopyConstructor() {
        System.out.println("\nДемонстрація конструктора копіювання");
        ArrayList<Employee> employees = company.getEmployees();
        if (employees.isEmpty()) {
            System.out.println("Спочатку створіть хоча б одного працівника.");
            return;
        }
        Employee original = employees.get(0);
        Employee copy = new Employee(original);
        System.out.println("Оригінал: " + original);
        System.out.println("Копія:    " + copy);
    }

    private static void showStaticCounter() {
        System.out.println("\nСтатичний лічильник");
        System.out.println("Всього створено об'єктів Employee: " + Employee.getTotalEmployees());
    }

    //Допоміжні методи для безпечного введення
    private static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Помилка: введіть ціле число.");
                scanner.nextLine();
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Помилка: поле не може бути порожнім.");
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) return value;
                System.out.println("Помилка: число має бути додатним.");
            } catch (InputMismatchException e) {
                System.out.println("Помилка: введіть ціле число.");
                scanner.nextLine();
            }
        }
    }

    private static double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value >= 0) return value;
                System.out.println("Помилка: зарплата не може бути від'ємною.");
            } catch (InputMismatchException e) {
                System.out.println("Помилка: введіть число.");
                scanner.nextLine();
            }
        }
    }

    private static String readEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String email = scanner.nextLine().trim();
            if (email.contains("@")) {
                return email;
            }
            System.out.println("Помилка: email має містити '@'.");
        }
    }

    private static int readAge(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int age = scanner.nextInt();
                scanner.nextLine();
                if (age > 0 && age < 100) return age;
                System.out.println("Помилка: вік має бути від 1 до 99.");
            } catch (InputMismatchException e) {
                System.out.println("Помилка: введіть ціле число.");
                scanner.nextLine();
            }
        }
    }

    private static Position readPosition(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return Position.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: невідома посада. Доступні: DEVELOPER, MANAGER, ANALYST, HR, ADMINISTRATOR, INTERN");
            }
        }
    }
}