package ua.training.servlet_project.model.entity;

import ua.training.servlet_project.filters.HttpMethodType;

public class Rule {
    private Long id;
    private String pattern;
    private RoleType role;
    private HttpMethodType method;

    public Rule() {
    }

    public Rule(Long id, String pattern, RoleType role, HttpMethodType method) {
        this.id = id;
        this.pattern = pattern;
        this.role = role;
        this.method = method;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public HttpMethodType getMethod() {
        return method;
    }

    public void setMethod(HttpMethodType method) {
        this.method = method;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (getRole() != rule.getRole()) return false;
        return getMethod() == rule.getMethod();
    }

    @Override
    public int hashCode() {
        int result = getRole().hashCode();
        result = 31 * result + getMethod().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "role=" + role +
                ", method=" + method +
                '}';
    }

    public static RuleBuilder builder() {
        return new RuleBuilder();
    }

    public static class RuleBuilder {
        private Long id;
        private String pattern;
        private RoleType role;
        private HttpMethodType method;

        RuleBuilder() {
        }

        public RuleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RuleBuilder pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public RuleBuilder role(RoleType role) {
            this.role = role;
            return this;
        }

        public RuleBuilder method(HttpMethodType method) {
            this.method = method;
            return this;
        }

        public Rule build() {
            return new Rule(id, pattern, role, method);
        }
    }
}
