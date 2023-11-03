package com.example.cvservice.service.Direction;

import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.service.Filter.DirectionFilter;
import com.example.cvservice.repository.Direction.DirectionsRepository;
import com.example.cvservice.service.EntityOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Direction> findAllByNames(List<String> names) {
        return repository.findAllByNameIn(names);
    }

    public Page<Direction> findDirectionsByParams(int page, int size, String name, String description, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort));
        return repository.findAll(DirectionFilter.filterDirections(DirectionFilter.generateDirectionFilterFromParams(name, description)), pageable);
    }


    public Optional<Direction> findDirectionByName(String name) {
        return repository.findByName(name);
    }

    public Optional<Direction> findDirectionByID(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public void save(Object object) {
        repository.save((Direction) object);
    }

    @Transactional
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

        repository.saveAll(directions);
    }

}
