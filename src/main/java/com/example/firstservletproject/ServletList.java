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
import java.util.Map;

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        StringBuffer stringBuffer = new StringBuffer();
        String line;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                stringBuffer.append(line);
        } catch (Exception e) {
            System.out.println("Error");
        }

        JsonObject jsonObject = gson.fromJson(String.valueOf(stringBuffer), JsonObject.class);
        int id = jsonObject.get("id").getAsInt();

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();

        if (id == 0) {
            pw.print(gson.toJson(model.getFromList()));
        }
        else if (id > 0) {
            if (model.isUserInList(id))
                pw.println(gson.toJson("Такого пользователя нет (("));

            JsonObject answer = new JsonObject();
            answer.addProperty("name", model.getFromList().get(id).getName());
            answer.addProperty("surname", model.getFromList().get(id).getSurname());
            answer.addProperty("salary", model.getFromList().get(id).getSalary());
            pw.print(answer);
        }
        else {
            pw.print(gson.toJson("ID должен быть больше нуля"));
        }
    }
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
//        PrintWriter pw = response.getWriter();
//        int id = Integer.parseInt(request.getParameter("id"));
//
//        if (id == 0) {
//            pw.println("<html>" +
//                    "<h3>Доступные пользователи: </h3><br/>" + "ID пользователя" + "<ul>");
//
//            for (Map.Entry<Integer, User> entry: model.getFromList().entrySet()) {
//                pw.print("<li>" + entry.getKey() + "</li>" +
//                        "<ul>" +
//                        "<li>Имя: " + entry.getValue().getName() + "</li>" +
//                        "<li>Фамилия: " + entry.getValue().getSurname() + "</li>" +
//                        "<li>Зарплата: " + entry.getValue().getSalary() + "</li>" +
//                        "</ul>");
//            }
//
//            pw.print("</ul>" + "<a href=\"index.jsp\">Домой</a>" + "</html>");
//        }
//        else if (id > 0) {
//            if (id > model.getFromList().size())
//                pw.print("<html>" +
//                         "<h3>Такого пользователя нет(((</h3>" +
//                        "<a href=\"index.jsp\">Домой</a>" + "</html>");
//            else {
//                pw.print("<html>" +
//                        "<h3>Запрошенный пользователь:</h3><br/>" +
//                        "Имя: " + model.getFromList().get(id).getName() + "<br/>" +
//                        "Фамилия: " + model.getFromList().get(id).getSurname() + "<br/>" +
//                        "Зарплата: " + model.getFromList().get(id).getSalary() + "<br/>" +
//                        "<a href=\"index.jsp\">Домой</a>" + "</html>");
//            }
//        }
//        else {
//            pw.print("<html>" +
//                    "<h3>ID должен быть больше нуля</h3>" +
//                    "<a href=\"index.jsp\">Домой</a>" + "</html>");
//        }
//    }
}
