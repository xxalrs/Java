package org.example;

public class RemoteEmployee extends Employee {
    private String remoteTools;     // інструменти для віддаленої роботи
    private double hourlyRate;      // погодинна ставка

    public RemoteEmployee(String name, int id, double salary, Position position, String email, int age,
                          String remoteTools, double hourlyRate) {
        super(name, id, salary, position, email, age);
        setRemoteTools(remoteTools);
        setHourlyRate(hourlyRate);
    }

    public String getRemoteTools() {
        return remoteTools;
    }

    public void setRemoteTools(String remoteTools) {
        if (remoteTools == null || remoteTools.trim().isEmpty()) {
            throw new IllegalArgumentException("Інструменти віддаленої роботи не можуть бути порожніми");
        }
        this.remoteTools = remoteTools;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Погодинна ставка не може бути від'ємною");
        }
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "RemoteEmployee{" +
                "name='" + getName() + '\'' +
                ", id=" + getId() +
                ", salary=" + getSalary() +
                ", position=" + getPosition() +
                ", email='" + getEmail() + '\'' +
                ", age=" + getAge() +
                ", remoteTools='" + remoteTools + '\'' +
                ", hourlyRate=" + hourlyRate +
                "}";
    }
}