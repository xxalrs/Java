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

    private static Employee parseEmployeeFromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 2) return null;
        String type = parts[0];
        try {
            switch (type) {
                case "Employee":
                    if (parts.length != 7) return null;
                    return new Employee(
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]),
                            parts[5],
                            Integer.parseInt(parts[6])
                    );
                case "Contract":
                    if (parts.length != 8) return null;
                    return new ContractEmployee(
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]),
                            parts[5],
                            Integer.parseInt(parts[6]),
                            Integer.parseInt(parts[7])
                    );
                case "FullTime":
                    if (parts.length != 8) return null;
                    return new FullTimeEmployee(
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]),
                            parts[5],
                            Integer.parseInt(parts[6]),
                            Double.parseDouble(parts[7])
                    );
                case "Intern":
                    if (parts.length != 9) return null;
                    return new InternEmployee(
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]),
                            parts[5],
                            Integer.parseInt(parts[6]),
                            parts[7],
                            Integer.parseInt(parts[8])
                    );
                case "Remote":
                    if (parts.length != 9) return null;
                    return new RemoteEmployee(
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]),
                            parts[5],
                            Integer.parseInt(parts[6]),
                            parts[7],
                            Double.parseDouble(parts[8])
                    );
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
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

    private static String serializeEmployee(Employee emp) {
        if (emp instanceof ContractEmployee) {
            ContractEmployee c = (ContractEmployee) emp;
            return String.format("Contract|%s|%d|%.2f|%s|%s|%d|%d",
                    c.getName(), c.getId(), c.getSalary(), c.getPosition(),
                    c.getEmail(), c.getAge(), c.getContractDuration());
        } else if (emp instanceof FullTimeEmployee) {
            FullTimeEmployee f = (FullTimeEmployee) emp;
            return String.format("FullTime|%s|%d|%.2f|%s|%s|%d|%.2f",
                    f.getName(), f.getId(), f.getSalary(), f.getPosition(),
                    f.getEmail(), f.getAge(), f.getBonus());
        } else if (emp instanceof InternEmployee) {
            InternEmployee i = (InternEmployee) emp;
            return String.format("Intern|%s|%d|%.2f|%s|%s|%d|%s|%d",
                    i.getName(), i.getId(), i.getSalary(), i.getPosition(),
                    i.getEmail(), i.getAge(), i.getUniversity(), i.getInternshipDuration());
        } else if (emp instanceof RemoteEmployee) {
            RemoteEmployee r = (RemoteEmployee) emp;
            return String.format("Remote|%s|%d|%.2f|%s|%s|%d|%s|%.2f",
                    r.getName(), r.getId(), r.getSalary(), r.getPosition(),
                    r.getEmail(), r.getAge(), r.getRemoteTools(), r.getHourlyRate());
        } else {
            // базовий Employee
            return String.format("Employee|%s|%d|%.2f|%s|%s|%d",
                    emp.getName(), emp.getId(), emp.getSalary(), emp.getPosition(),
                    emp.getEmail(), emp.getAge());
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