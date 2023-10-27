package com.example.cvservice.Service.Direction;

import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Repository.Direction.DirectionsRepository;
import com.example.cvservice.Service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DirectionService implements EntityOperations {

    @Autowired
    private DirectionsRepository repository;


    public List<Direction> findAll() {
//        preLoad();
        return repository.findAll();
    }

    public Optional<Direction> findDirectionByName(String name) {
        return repository.findByName(name);
    }

    public Optional<Direction> findDirectionByID(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Object object) {
        repository.save((Direction) object);
    }

    @Override
    public void update(Object object) {
        repository.save((Direction) object);

    }

    @Override
    public void delete(Object object) {
        repository.delete((Direction) object);
    }

    public void preLoad() {
        Direction direction = new Direction();
        ArrayList<Direction> directions = new ArrayList<>();
        direction.setName("test");
        direction.setDescription("testDesc");

        directions.add(direction);
        directions.add(direction);
        directions.add(direction);

        repository.saveAll(directions);
    }

}
