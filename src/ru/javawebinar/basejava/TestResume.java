package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.util.ArrayList;
import java.util.List;

public class TestResume {
    public static void main(String[] args) {
        Resume testResume = new Resume("Bilbo");
        testResume.setContacts(ContactType.PHONE, "Test Тел.");
        testResume.setContacts(ContactType.SKYPE, "Test Skype");
        testResume.setContacts(ContactType.MAIL, "Test Почта");
        testResume.setContacts(ContactType.LINKEDIN, "Test Профиль LinkedIn");
        testResume.setContacts(ContactType.GITHUB, "Test Профиль GitHub");
        testResume.setContacts(ContactType.STACKOVERFLOW, "Test Профиль Stackoverflow");
        testResume.setContacts(ContactType.HOME_PAGE, "Test Домашняя страница");
        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + " : " + testResume.getContact(type));
        }
        testResume.setSections(SectionType.PERSONAL, new TextContent("Test Личные качества"));
        testResume.setSections(SectionType.OBJECTIVE, new TextContent("Test Позиция"));
        List<String> achievements = new ArrayList<>();
        achievements.add("Достижение 1");
        achievements.add("Достижение 2");
        achievements.add("Достижение 3");
        testResume.setSections(SectionType.ACHIEVEMENT, new ListContent(achievements));
        List<String> qualifications = new ArrayList<>();
        qualifications.add("Квалификация 1");
        qualifications.add("Квалификация 2");
        qualifications.add("Квалификация 3");
        testResume.setSections(SectionType.QUALIFICATIONS, new ListContent(qualifications));
        List<WorkStudyInfo> experience = new ArrayList<>();
        experience.add(new WorkStudyInfo("website 1", "1/1/2000", "2/1/2000",
                "position 1", "info 1"));
        experience.add(new WorkStudyInfo("website 2", "1/1/2000", "2/1/2000",
                "position 2", "info 2"));
        experience.add(new WorkStudyInfo("website 3", "1/1/2000", "2/1/2000",
                "position 3", "info 3"));
        testResume.setSections(SectionType.EXPERIENCE, new WorkStudyContent(experience));
        List<WorkStudyInfo> education = new ArrayList<>();
        education.add(new WorkStudyInfo("website 1", "1/1/2000", "2/1/2000",
                "position 1", null));
        education.add(new WorkStudyInfo("website 2", "1/1/2000", "2/1/2000",
                "position 2", null));
        education.add(new WorkStudyInfo("website 3", "1/1/2000", "2/1/2000",
                "position 3", null));
        testResume.setSections(SectionType.EDUCATION, new WorkStudyContent(education));
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + " : " + testResume.getSection(type).toString());
        }
    }
}