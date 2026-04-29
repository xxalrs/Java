package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Company company;
    private static Scanner scanner = new Scanner(System.in);
    private static final String DATA_FILE = "Java/input.txt";

    public static void main(String[] args) {
        loadFromFile(DATA_FILE);
        if (company == null) {
            System.out.println("Не вдалося завантажити компанію. Завершення роботи.");
            return;
        }

        while (true) {
            printMenu();
            int choice = readIntInput("Ваш вибір: ");
            switch (choice) {
                case 1:
                    searchMenu();
                    break;
                case 2:
                    createEmployeeAndAdd();
                    break;
                case 3:
                    displayAllEmployees();
                    break;
                case 4:
                    saveToFile(DATA_FILE);
                    System.out.println("Дані збережено. До побачення!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== МЕНЮ =====");
        System.out.println("1. Пошук об'єкта");
        System.out.println("2. Додати нового працівника (з кількістю)");
        System.out.println("3. Вивести всіх працівників компанії");
        System.out.println("4. Вийти");
    }

    // ПОШУК
    private static void searchMenu() {
        while (true) {
            System.out.println("\n--- Пошук працівників ---");
            System.out.println("1. Пошук за ім'ям (частковий збіг)");
            System.out.println("2. Пошук за ID (точний)");
            System.out.println("3. Пошук за посадою (Position)");
            System.out.println("4. Повернутися до головного меню");
            int choice = readIntInput("Оберіть критерій: ");
            switch (choice) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    searchById();
                    break;
                case 3:
                    searchByPosition();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Некоректний вибір.");
            }
        }
    }

    private static void searchByName() {
        System.out.print("Введіть ім'я (або частину): ");
        String name = scanner.nextLine().trim();
        ArrayList<EmployeeEntry> results = company.searchByName(name);
        printSearchResults(results, "ім'ям \"" + name + "\"");
    }

    private static void searchById() {
        int id = readPositiveInt("Введіть ID: ");
        ArrayList<EmployeeEntry> results = company.searchById(id);
        printSearchResults(results, "ID = " + id);
    }

    private static void searchByPosition() {
        Position pos = readPosition("Введіть посаду: ");
        ArrayList<EmployeeEntry> results = company.searchByPosition(pos);
        printSearchResults(results, "посаді " + pos);
    }

    private static void printSearchResults(ArrayList<EmployeeEntry> results, String criteria) {
        if (results.isEmpty()) {
            System.out.println("Жодного працівника не знайдено за критерієм: " + criteria);
        } else {
            System.out.println("Знайдено " + results.size() + " запис(ів) за критерієм \"" + criteria + "\":");
            for (EmployeeEntry entry : results) {
                System.out.println(entry);
            }
        }
    }

    // ДОДАВАННЯ НОВОГО ПРАЦІВНИКА
    private static void createEmployeeAndAdd() {
        System.out.println("\n--- Додавання нового працівника ---");
        try {
            System.out.println("Виберіть тип працівника:");
            System.out.println("1. Employee (базовий)");
            System.out.println("2. ContractEmployee");
            System.out.println("3. FullTimeEmployee");
            System.out.println("4. InternEmployee");
            System.out.println("5. RemoteEmployee");
            int type = readIntInput("Ваш вибір: ");
            Employee emp = null;
            switch (type) {
                case 1:
                    emp = new Employee(
                            readNonEmptyString("Ім'я: "),
                            readPositiveInt("ID: "),
                            readNonNegativeDouble("Зарплата: "),
                            readPosition("Посада: "),
                            readEmail("Email: "),
                            readAge("Вік: ")
                    );
                    break;
                case 2:
                    emp = new ContractEmployee(
                            readNonEmptyString("Ім'я: "),
                            readPositiveInt("ID: "),
                            readNonNegativeDouble("Зарплата: "),
                            readPosition("Посада: "),
                            readEmail("Email: "),
                            readAge("Вік: "),
                            readPositiveInt("Тривалість контракту (місяці): ")
                    );
                    break;
                case 3:
                    emp = new FullTimeEmployee(
                            readNonEmptyString("Ім'я: "),
                            readPositiveInt("ID: "),
                            readNonNegativeDouble("Зарплата: "),
                            readPosition("Посада: "),
                            readEmail("Email: "),
                            readAge("Вік: "),
                            readNonNegativeDouble("Річна премія: ")
                    );
                    break;
                case 4:
                    emp = new InternEmployee(
                            readNonEmptyString("Ім'я: "),
                            readPositiveInt("ID: "),
                            readNonNegativeDouble("Зарплата: "),
                            readPosition("Посада: "),
                            readEmail("Email: "),
                            readAge("Вік: "),
                            readNonEmptyString("Університет: "),
                            readPositiveInt("Тривалість стажування (місяці): ")
                    );
                    break;
                case 5:
                    emp = new RemoteEmployee(
                            readNonEmptyString("Ім'я: "),
                            readPositiveInt("ID: "),
                            readNonNegativeDouble("Зарплата: "),
                            readPosition("Посада: "),
                            readEmail("Email: "),
                            readAge("Вік: "),
                            readNonEmptyString("Інструменти: "),
                            readNonNegativeDouble("Погодинна ставка: ")
                    );
                    break;
                default:
                    System.out.println("Невірний тип.");
                    return;
            }
            int quantity = readPositiveInt("Кількість таких працівників: ");
            company.addNewEmployee(emp, quantity);
            System.out.println("Працівника додано (або оновлено кількість).");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void displayAllEmployees() {
        ArrayList<EmployeeEntry> entries = company.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("У компанії немає працівників.");
        } else {
            System.out.println("\n=== Компанія: " + company.getName() + ", " + company.getAddress() + " ===");
            System.out.println("Загальна кількість працівників (з урахуванням кількості кожного запису): " + company.getTotalEmployeesCount());
            System.out.println("Детальний список:");
            for (EmployeeEntry entry : entries) {
                System.out.println(entry);
            }
        }
    }

    // ФАЙЛОВІ ОПЕРАЦІЇ
    private static void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Файл " + filename + " не знайдено. Компанію буде створено з порожніми даними.");
            company = new Company("Невідома компанія", "Невідома адреса");
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            // Перший рядок: Company|name|address
            if (!fileScanner.hasNextLine()) {
                System.out.println("Файл порожній. Створюємо компанію за замовчуванням.");
                company = new Company("Невідома компанія", "Невідома адреса");
                return;
            }
            String companyLine = fileScanner.nextLine().trim();
            String[] companyParts = companyLine.split("\\|");
            if (!companyParts[0].equals("Company") || companyParts.length != 3) {
                System.out.println("Некоректний формат рядка компанії. Створюємо за замовчуванням.");
                company = new Company("Невідома компанія", "Невідома адреса");
            } else {
                company = new Company(companyParts[1], companyParts[2]);
            }

            int lineNum = 1;
            while (fileScanner.hasNextLine()) {
                lineNum++;
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 2 || !parts[0].equals("EmployeeEntry")) {
                    System.out.println("Попередження: рядок " + lineNum + " пропущено (не EmployeeEntry).");
                    continue;
                }

                int quantity = Integer.parseInt(parts[parts.length - 1]);
                String[] empFields = new String[parts.length - 2];
                System.arraycopy(parts, 1, empFields, 0, parts.length - 2);
                String empLine = String.join("|", empFields);
                Employee emp = parseEmployeeFromLine(empLine);
                if (emp != null) {
                    company.addNewEmployee(emp, quantity);
                } else {
                    System.out.println("Попередження: рядок " + lineNum + " не вдалося розібрати.");
                }
            }
            System.out.println("Завантажено компанію: " + company.getName() + ", працівників: " + company.getAllEntries().size());
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
            company = new Company("Невідома компанія", "Невідома адреса");
        } catch (NumberFormatException e) {
            System.out.println("Помилка числових даних у файлі.");
            company = new Company("Невідома компанія", "Невідома адреса");
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
                    return new Employee(parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]), parts[5], Integer.parseInt(parts[6]));
                case "Contract":
                    if (parts.length != 8) return null;
                    return new ContractEmployee(parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]), parts[5], Integer.parseInt(parts[6]), Integer.parseInt(parts[7]));
                case "FullTime":
                    if (parts.length != 8) return null;
                    return new FullTimeEmployee(parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]), parts[5], Integer.parseInt(parts[6]), Double.parseDouble(parts[7]));
                case "Intern":
                    if (parts.length != 9) return null;
                    return new InternEmployee(parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]), parts[5], Integer.parseInt(parts[6]), parts[7], Integer.parseInt(parts[8]));
                case "Remote":
                    if (parts.length != 9) return null;
                    return new RemoteEmployee(parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]),
                            Position.valueOf(parts[4]), parts[5], Integer.parseInt(parts[6]), parts[7], Double.parseDouble(parts[8]));
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Company|" + company.getName() + "|" + company.getAddress());
            for (EmployeeEntry entry : company.getAllEntries()) {
                Employee emp = entry.getEmployee();
                int quantity = entry.getQuantity();
                String empSerialized = serializeEmployee(emp);
                writer.println("EmployeeEntry|" + empSerialized + "|" + quantity);
            }
            System.out.println("Збережено компанію та " + company.getAllEntries().size() + " записів у файл " + filename);
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
            return String.format("Employee|%s|%d|%.2f|%s|%s|%d",
                    emp.getName(), emp.getId(), emp.getSalary(), emp.getPosition(),
                    emp.getEmail(), emp.getAge());
        }
    }

    //ДОПОМІЖНІ МЕТОДИ ВАЛІДАЦІЇ
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
            if (!input.isEmpty()) return input;
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
                System.out.println("Помилка: число не може бути від'ємним.");
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
            if (email.contains("@")) return email;
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