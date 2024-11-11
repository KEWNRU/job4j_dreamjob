package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.repository.CityRepository;
import ru.job4j.dreamjob.repository.Sql2oCityRepository;

import java.util.Collection;

@Service
public class SimpleCityService implements CityService {

    private final Sql2oCityRepository sql2oCityRepository;

    public SimpleCityService(Sql2oCityRepository sql2oCityRepository) {
        this.sql2oCityRepository = sql2oCityRepository;
    }

    @Override
    public Collection<City> findAll() {
        return sql2oCityRepository.findAll();
    }
}