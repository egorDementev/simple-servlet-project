package com.example.firstservletproject;

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

@WebServlet(urlPatterns = "/calculate")
public class ServletCalculator extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
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
        double a = jsonObject.get("a").getAsDouble();
        double b = jsonObject.get("b").getAsDouble();
        String math = jsonObject.get("math").getAsString();

        PrintWriter pw = response.getWriter();
        JsonObject answer = new JsonObject();

        if (Objects.equals(math, "+"))
            answer.addProperty("result", a + b);
        else if (Objects.equals(math, "-"))
            answer.addProperty("result", a - b);
        else if (Objects.equals(math, "*"))
            answer.addProperty("result", a * b);
        else if (Objects.equals(math, "/") && b != 0)
            answer.addProperty("result", a / b);
        else if (Objects.equals(math, "/") && b == 0)
            answer.addProperty("result", "Деление на ноль!");
        else {
            answer.addProperty("result", "Вы ввели некорректную операцию, используйте +, -, *, или /");
        }

        pw.print(answer);
    }
}
