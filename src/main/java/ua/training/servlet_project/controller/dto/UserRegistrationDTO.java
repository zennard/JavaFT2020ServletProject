package ua.training.servlet_project.controller.dto;

public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static UserRegistrationDTOBuilder builder() {
        return new UserRegistrationDTO.UserRegistrationDTOBuilder();
    }

    public static class UserRegistrationDTOBuilder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;

        UserRegistrationDTOBuilder() {
        }

        public UserRegistrationDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserRegistrationDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserRegistrationDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserRegistrationDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserRegistrationDTO build() {
            return new UserRegistrationDTO(firstName, lastName, email, password);
        }
    }
}
