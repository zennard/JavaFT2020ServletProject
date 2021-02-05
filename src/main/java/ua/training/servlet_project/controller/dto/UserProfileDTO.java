package ua.training.servlet_project.controller.dto;

import ua.training.servlet_project.model.entity.RoleType;

public class UserProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private RoleType role;

    public UserProfileDTO() {
    }

    public UserProfileDTO(Long id, String firstName, String lastName, String email, RoleType role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public static UserProfileDTOBuilder builder() {
        return new UserProfileDTOBuilder();
    }

    public static class UserProfileDTOBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private RoleType role;

        UserProfileDTOBuilder() {
        }

        public UserProfileDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserProfileDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserProfileDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserProfileDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserProfileDTOBuilder role(RoleType role) {
            this.role = role;
            return this;
        }

        public UserProfileDTO build() {
            return new UserProfileDTO(id, firstName, lastName, email, role);
        }
    }
}
