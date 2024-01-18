package com.example.firstservletproject.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Model implements Serializable {
    private static final Model instance = new Model();
    private final Map<Integer, User> model;

    public static Model getInstance() {
        return instance;
    }
    private Model() {
        model = new HashMap<>();

        model.put(1, new User("Egor", "Dementev", 111));
        model.put(2, new User("Dashina", "Vavilova", 222));
        model.put(3, new User("Artem", "Mushtukov", 333));
    }

    public void add(User user, Integer id) {
        model.put(id, user);
    }
    public boolean isUserInList(Integer id) {
        for (Map.Entry<Integer, User> entry: model.entrySet()) {
            if (Objects.equals(id, entry.getKey()))
                return true;
        }
        return false;
    }
    public void delete(Integer id) {model.remove(id); }
    public void update(Integer id, String name, String surname, Double salary) {
        model.replace(id, new User(name, surname, salary));
    }

    public Map<Integer, User> getFromList() {
        return model;
    }
}
