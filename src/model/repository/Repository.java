package model.repository;

import java.util.List;

public interface Repository<T> {
    boolean deleteById(int id);
    List<T> findAll();
    List<T> searchByName(String name);
}
