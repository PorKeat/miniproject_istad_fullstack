package model.repository;

import model.entities.User;

import java.util.List;

public interface Repository <T , K>{
    T save(T t);

    K delete(K id);
}