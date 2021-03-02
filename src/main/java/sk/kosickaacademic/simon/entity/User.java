package sk.kosickaacademic.simon.entity;

import sk.kosickaacademic.simon.Gender;

public class User {
    private int id, age;
    private String fName, lName;
    private Gender gender;

    public User(int id, int age, String fName, String lName, int gender) {
        this(age, fName, lName, gender);
        this.id = id;
    }

    public User(int age, String fName, String lName, int gender) {
        this.age = age;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender==0?Gender.MALE:gender==1?Gender.FEMALE:Gender.OTHER;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public Gender getGender() {
        return gender;
    }
}
