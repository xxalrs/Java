package org.example;

public class InternEmployee extends Employee {
    private String university;          // університет
    private int internshipDuration;     // тривалість стажування

    public InternEmployee(String name, int id, double salary, Position position, String email, int age,
                          String university, int internshipDuration) {
        super(name, id, salary, position, email, age);
        setUniversity(university);
        setInternshipDuration(internshipDuration);
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        if (university == null || university.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва університету не може бути порожньою");
        }
        this.university = university;
    }

    public int getInternshipDuration() {
        return internshipDuration;
    }

    public void setInternshipDuration(int internshipDuration) {
        if (internshipDuration <= 0) {
            throw new IllegalArgumentException("Тривалість стажування має бути додатною");
        }
        this.internshipDuration = internshipDuration;
    }

    @Override
    public String toString() {
        return "InternEmployee{" +
                "name='" + getName() + '\'' +
                ", id=" + getId() +
                ", salary=" + getSalary() +
                ", position=" + getPosition() +
                ", email='" + getEmail() + '\'' +
                ", age=" + getAge() +
                ", university='" + university + '\'' +
                ", internshipDuration=" + internshipDuration +
                "}";
    }
}