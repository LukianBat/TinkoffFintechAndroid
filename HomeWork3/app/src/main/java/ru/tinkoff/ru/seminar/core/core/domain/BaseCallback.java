package ru.tinkoff.ru.seminar.core.core.domain;

public interface BaseCallback<T> {
    void onSuccess(T data);
}
