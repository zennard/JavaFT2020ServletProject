package ua.training.servlet_project.model.entity;

public class ApartmentDescription {
    private Long id;
    private Apartment apartment;
    private String description;
    private Language lang;

    public ApartmentDescription() {
    }

    public ApartmentDescription(Long id, Apartment apartment, String description, Language lang) {
        this.id = id;
        this.apartment = apartment;
        this.description = description;
        this.lang = lang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "ApartmentDescription{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", lang=" + lang +
                '}';
    }

    public static ApartmentDescriptionBuilder builder() {
        return new ApartmentDescriptionBuilder();
    }

    public static class ApartmentDescriptionBuilder {
        private Long id;
        private Apartment apartment;
        private String description;
        private Language lang;

        ApartmentDescriptionBuilder() {
        }

        public ApartmentDescriptionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ApartmentDescriptionBuilder apartment(Apartment apartment) {
            this.apartment = apartment;
            return this;
        }

        public ApartmentDescriptionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ApartmentDescriptionBuilder lang(Language lang) {
            this.lang = lang;
            return this;
        }

        public ApartmentDescription build() {
            return new ApartmentDescription(id, apartment, description, lang);
        }
    }
}
