package com.quantum.apps.simplynotes;

import java.util.List;

public interface Repository<T> {
    T item(long id);
    T add(T note);
    List<T> list();
    void destroy();
}