package com.example.mediatracker.Repository;

import java.util.List;

public interface IRepository <T>{
    List<T> load();
    void save(List<T> data);
}
