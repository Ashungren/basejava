package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String uuid = request.getParameter("uuid");
        writer.write("<html>\n" +
                "<head>\n" +
                "<title> Список резюме </title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border = \"1\">\n" +
                "<tr>\n" +
                "<td>uuid</td>\n" +
                "<td>full name</td>\n" +
                "<td>contacts</td>\n" +
                "<td>sections</td>\n" +
                "</tr>\n");
        if (uuid == null) {
            List<Resume> list = storage.getAllSorted();
            for (Resume r : list) {
                printResume(r, writer);
            }
        } else {
            Resume r = storage.get(uuid);
            printResume(r, writer);
        }
        writer.write("</table>\n" +
                "</body>\n" +
                "</html>");
    }

    private void printResume(Resume r, PrintWriter writer) throws IOException {
        List<String> contacts = new ArrayList<>(r.getContacts().values());
        int sizeContacts = contacts.size();
        List<Section> sections = new ArrayList<>(r.getSections().values());
        int sizeSections = sections.size();
        int sizeMax = sizeContacts > sizeSections ? sizeContacts : sizeSections;
        writer.write("<tr>\n");
        if (sizeMax > 1) {
            writer.write("<td rowspan = \"" + sizeMax + "\">" + r.getUuid() + "</td>\n" +
                    "<td rowspan = \"" + sizeMax + "\">" + r.getFullName() + "</td>\n");
        } else {
            writer.write("<td>" + r.getUuid() + "</td>\n" +
                    "<td>" + r.getFullName() + "</td>\n" +
                    "<td> - </td>\n" +
                    "<td> - </td>\n");
        }
        for (int i = 0; i < sizeMax; i++) {
            if (i + 1 == sizeContacts) {
                writer.write("<td rowspan = \"" + (sizeMax + 1 - sizeContacts) + "\">" + contacts.get(i) + "</td>\n");
            } else if (i < sizeContacts) {
                writer.write("<td>" + contacts.get(i) + "</td>\n");
            } else if (sizeContacts == 0) {
                writer.write("<td rowspan = \"" + sizeMax + "\">" + " - " + "</td>\n");
                sizeContacts = sizeContacts + 1;
            }
            if (i + 1 == sizeSections) {
                writer.write("<td rowspan = \"" + (sizeMax + 1 - sizeSections) + "\">" + sections.get(i) + "</td>\n");
            } else if (i < sizeSections) {
                writer.write("<td>" + sections.get(i).toString() + "</td>\n");
            } else if (sizeSections == 0) {
                writer.write("<td rowspan = \"" + sizeMax + "\">" + " - " + "</td>\n");
                sizeSections = sizeSections + 1;
            }
            writer.write("</tr>\n");
            if (i + 1 < sizeMax) {
                writer.write("<tr>\n");
            }
        }
    }
}