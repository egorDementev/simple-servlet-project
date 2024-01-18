package com.example.firstservletproject;

import com.example.firstservletproject.logic.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Model model = Model.getInstance();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        model.delete(id);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(gson.toJson(model.getFromList()));
    }
}
