package ua.training.servlet_project.controller.dto;

public class UserLoginDTO {
    private String email;
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
        return "UserLoginDTO{" +
                "email='" + email + '\'' +
                '}';
    }

    public static UserLoginDTOBuilder builder() {
        return new UserLoginDTOBuilder();
    }

    public static class UserLoginDTOBuilder {
        private String email;
        private String password;

        UserLoginDTOBuilder() {
        }

        public UserLoginDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserLoginDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserLoginDTO build() {
            return new UserLoginDTO(email, password);
        }
    }
}
