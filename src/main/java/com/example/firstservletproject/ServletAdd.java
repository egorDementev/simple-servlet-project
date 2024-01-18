package com.example.firstservletproject;

import com.example.firstservletproject.logic.Model;
import com.example.firstservletproject.logic.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {
    private final AtomicInteger counter = new AtomicInteger(4);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        request.setCharacterEncoding("UTF-8");

        String strContentType = request.getContentType();
        if (Objects.equals(strContentType, "application/json")) {
            response.setContentType("application/json;charset=utf-8");

            StringBuffer jb = new StringBuffer();
            String line;

            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) {
                System.out.println("Error");
            }

            JsonObject jsonObject = gson.fromJson(String.valueOf(jb), JsonObject.class);

            String name = jsonObject.get("name").getAsString();
            String surname = jsonObject.get("surname").getAsString();
            double salary = jsonObject.get("salary").getAsDouble();

            User user = new User(name, surname, salary);
            model.add(user, counter.getAndIncrement());

            pw.print(gson.toJson(model.getFromList()));

        }
        else {
            response.setContentType("text/html;charset=utf-8");

            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            double salary = Double.parseDouble(request.getParameter("salary"));

            User user = new User(name, surname, salary);
            model.add(user, counter.getAndIncrement());

            pw.println("<html>" +
                    "<h3>Пользователь " + name + " " + surname + " с зарплатой " + salary + " успешно создан!</h3>" +
                    "<a href=\"addUser.html\">Создать нового пользователя</a><br/>" +
                    "<a href=\"index.jsp\">Домой</a>" +
                    "</html>");
        }
    }
    }
