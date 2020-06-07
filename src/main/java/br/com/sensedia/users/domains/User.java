package br.com.sensedia.users.domains;

import java.util.Calendar;
import java.util.Objects;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private Calendar birthDate;
    private String email;

    public User() {
        super();
    }

    public User(String id, String firstName, String lastName, Calendar birthDate, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public User(String firstName, String lastName, Calendar birthDate, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        if (Objects.nonNull(firstName) && Objects.nonNull(lastName)) {
            return firstName + " " + lastName;
        } else if (Objects.nonNull(firstName) && Objects.isNull(lastName)) {
            return firstName;
        } else if (Objects.isNull(firstName) && Objects.nonNull(lastName)) {
            return lastName;
        } else {
            return null;
        }
    }

    public int getAge() {
        if (Objects.nonNull(birthDate)) {
            return Calendar.getInstance().get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                birthDate.equals(user.birthDate) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                '}';
    }
}