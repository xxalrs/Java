package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ArrayList<Employee> employees = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String DATA_FILE = "input.txt";

    public static void main(String[] args) {
        loadFromFile(DATA_FILE);
        while (true) {
            printMenu();
            int choice = readIntInput("Ваш вибір: ");
            switch (choice) {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    createContractEmployee();
                    break;
                case 3:
                    createFullTimeEmployee();
                    break;
                case 4:
                    createInternEmployee();
                    break;
                case 5:
                    createRemoteEmployee();
                    break;
                case 6:
                    displayAllEmployees();
                    break;
                case 7:
                    saveToFile(DATA_FILE);
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
        System.out.println("1. Створити базового працівника (Employee)");
        System.out.println("2. Створити контрактного працівника (ContractEmployee)");
        System.out.println("3. Створити штатного працівника (FullTimeEmployee)");
        System.out.println("4. Створити стажиста (InternEmployee)");
        System.out.println("5. Створити віддаленого працівника (RemoteEmployee)");
        System.out.println("6. Вивести всіх працівників");
        System.out.println("7. Вийти");
    }

    //Файлові операції
    private static void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Файл " + filename + " не знайдено. Розпочинаємо з порожньою колекцією.");
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            int lineNum = 0;
            while (fileScanner.hasNextLine()) {
                lineNum++;
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                Employee emp = parseEmployeeFromLine(line);
                if (emp != null) {
                    employees.add(emp);
                } else {
                    System.out.println("Попередження: рядок " + lineNum + " має некоректний формат і пропущений.");
                }
            }
            System.out.println("Завантажено " + employees.size() + " працівників з файлу " + filename);
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Employee emp : employees) {
                writer.println(serializeEmployee(emp));
            }
            System.out.println("Збережено " + employees.size() + " записів у файл " + filename);
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    private static void createEmployee() {
        System.out.println("\n--- Створення базового працівника ---");
        try {
            Employee emp = new Employee(
                    readNonEmptyString("Ім'я: "),
                    readPositiveInt("ID: "),
                    readNonNegativeDouble("Зарплата: "),
                    readPosition("Посада: "),
                    readEmail("Email: "),
                    readAge("Вік: ")
            );
            employees.add(emp);
            System.out.println("Працівника додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createContractEmployee() {
        System.out.println("\n--- Створення контрактного працівника ---");
        try {
            ContractEmployee emp = new ContractEmployee(
                    readNonEmptyString("Ім'я: "),
                    readPositiveInt("ID: "),
                    readNonNegativeDouble("Зарплата: "),
                    readPosition("Посада: "),
                    readEmail("Email: "),
                    readAge("Вік: "),
                    readPositiveInt("Тривалість контракту (місяці): ")
            );
            employees.add(emp);
            System.out.println("Контрактного працівника додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createFullTimeEmployee() {
        System.out.println("\n--- Створення штатного працівника ---");
        try {
            FullTimeEmployee emp = new FullTimeEmployee(
                    readNonEmptyString("Ім'я: "),
                    readPositiveInt("ID: "),
                    readNonNegativeDouble("Зарплата: "),
                    readPosition("Посада: "),
                    readEmail("Email: "),
                    readAge("Вік: "),
                    readNonNegativeDouble("Річна премія: ")
            );
            employees.add(emp);
            System.out.println("Штатного працівника додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createInternEmployee() {
        System.out.println("\n--- Створення стажиста ---");
        try {
            InternEmployee emp = new InternEmployee(
                    readNonEmptyString("Ім'я: "),
                    readPositiveInt("ID: "),
                    readNonNegativeDouble("Зарплата: "),
                    readPosition("Посада: "),
                    readEmail("Email: "),
                    readAge("Вік: "),
                    readNonEmptyString("Університет: "),
                    readPositiveInt("Тривалість стажування (місяці): ")
            );
            employees.add(emp);
            System.out.println("Стажиста додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createRemoteEmployee() {
        System.out.println("\n--- Створення віддаленого працівника ---");
        try {
            RemoteEmployee emp = new RemoteEmployee(
                    readNonEmptyString("Ім'я: "),
                    readPositiveInt("ID: "),
                    readNonNegativeDouble("Зарплата: "),
                    readPosition("Посада: "),
                    readEmail("Email: "),
                    readAge("Вік: "),
                    readNonEmptyString("Інструменти віддаленої роботи (через кому): "),
                    readNonNegativeDouble("Погодинна ставка: ")
            );
            employees.add(emp);
            System.out.println("Віддаленого працівника додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("Список працівників порожній.");
        } else {
            System.out.println("\n=== Список усіх працівників ===");
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
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