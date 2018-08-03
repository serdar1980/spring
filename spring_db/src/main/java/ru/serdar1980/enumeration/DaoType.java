package ru.serdar1980.enumeration;

public enum DaoType {
    JDBC("jdbc"),
    JPA("jpa"),
    REPOSITORY("repository");

    private String text;

    DaoType(String text) {
        this.text = text;
    }

    public static DaoType fromString(String text) {
        for (DaoType b : DaoType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public String getText() {
        return this.text;
    }
}

