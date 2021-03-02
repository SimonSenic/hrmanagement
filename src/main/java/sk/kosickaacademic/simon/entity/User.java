package sk.kosickaacademic.simon.entity;

public class User {
    private int id, age;
    private String fName, lName;
    private boolean gender;

    public User(int id, int age, String fName, String lName, boolean gender) {
        this.id = id;
        this.age = age;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
    }

    public User(int age, String fName, String lName, boolean gender) {
        this.age = age;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
    }
}
