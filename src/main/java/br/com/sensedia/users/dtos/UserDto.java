package br.com.sensedia.users.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Objects;

public class UserDto {

    private String id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String firstName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Calendar birthDate;
    private String email;

    public UserDto() {
        super();
    }

    public UserDto(String id, String firstName, String lastName, Calendar birthDate, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public UserDto(String firstName, String lastName, Calendar birthDate, String email) {
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFullName() {
        if (Objects.nonNull(this.firstName) && Objects.nonNull(this.lastName)) {
            return firstName + " " + lastName;
        } else if (Objects.nonNull(this.firstName) && Objects.isNull(this.lastName)) {
            return firstName;
        } else if (Objects.isNull(this.firstName) && Objects.nonNull(this.lastName)) {
            return lastName;
        } else {
            return null;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getAge() {
        if (Objects.nonNull(birthDate)) {
            return Calendar.getInstance().get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id.equals(userDto.id) &&
                firstName.equals(userDto.firstName) &&
                lastName.equals(userDto.lastName) &&
                birthDate.equals(userDto.birthDate) &&
                email.equals(userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, email);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                '}';
    }
}
