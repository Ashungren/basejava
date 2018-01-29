package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                dos.writeUTF(type.toString());
                Section section = entry.getValue();
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = ((ListSection) section).getItems();
                        dos.writeInt(list.size());
                        for (String str : list) {
                            dos.writeUTF(str);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = ((OrganizationSection) section).getOrganizations();
                        dos.writeInt(organizationList.size());
                        for (Organization organization : organizationList) {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl());
                            List<Organization.Position> positionList = organization.getPositions();
                            dos.writeInt(positionList.size());
                            for (Organization.Position position : positionList) {
                                dos.writeInt(position.getStartDate().getYear());
                                dos.writeInt(position.getStartDate().getMonthValue());
                                dos.writeInt(position.getEndDate().getYear());
                                dos.writeInt(position.getEndDate().getMonthValue());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                int count;
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = new TextSection(dis.readUTF());
                        resume.addSection(type, textSection);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        count = dis.readInt();
                        List<String> result = new ArrayList<>(count);
                        for (int j = 0; j < count; j++) {
                            result.add(dis.readUTF());
                        }
                        ListSection listSection = new ListSection(result);
                        resume.addSection(type, listSection);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        count = dis.readInt();
                        List<Organization> organizationList = new ArrayList<>(count);
                        for (int j = 0; j < count; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            int check = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>(check);
                            for (int k = 0; k < check; k++) {
                                positions.add(new Organization.Position(dis.readInt(), Month.of(dis.readInt()),
                                        dis.readInt(), Month.of(dis.readInt()), dis.readUTF(), dis.readUTF()));
                            }
                            organizationList.add(new Organization(new Link(name, url), positions));
                        }
                        resume.addSection(type, new OrganizationSection(organizationList));
                        break;
                }
            }
            return resume;
        }
    }
}