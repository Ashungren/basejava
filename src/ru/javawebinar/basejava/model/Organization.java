package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private final Link homePage;

    private final List<Period> periods;

    public Organization(String name, String url, ArrayList<Period> periods) {
        this.homePage = new Link(name, url);
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return periods != null ? periods.equals(that.periods) : that.periods == null;
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage + periods + '}';
    }
}