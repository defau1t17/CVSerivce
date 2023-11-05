package com.example.cvservice.service.Direction;

import com.example.cvservice.dto.Direction.NewDirectionDTO;
import com.example.cvservice.dto.Direction.UpdateDirectionDTO;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.exception.ObjectAlreadyExistsException;
import com.example.cvservice.exception.ObjectIsEmptyException;
import com.example.cvservice.exception.ObjectNotFoundException;
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
import java.util.List;
import java.util.Optional;


@Service
public class DirectionService implements EntityOperations {

    @Autowired
    private DirectionsRepository repository;


    public List<Direction> findAll() {
        return repository.findAll();
    }

    public List<Direction> findAllByNames(List<String> names) {
        return repository.findAllByNameIn(names);
    }

    public Page<Direction> findDirectionsByParams(int page, int size, String name, String description, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort));
        return repository.findAll(new DirectionFilter().filterDirections(new DirectionFilter().generateDirectionFilterFromParams(name, description)), pageable);
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

    public Direction validateBeforeSave(NewDirectionDTO newDirectionDTO) {
        if (!findDirectionByName(newDirectionDTO.getName()).isPresent()) {
            if (newDirectionDTO.isValid()) {
                Direction direction = Direction.builder().name(newDirectionDTO.getName()).description(newDirectionDTO.getDescription()).build();
                save(direction);
                return direction;
            } else throw new ObjectIsEmptyException();
        }
        throw new ObjectAlreadyExistsException("Direction", newDirectionDTO.getName());
    }


    public Direction validateBeforeUpdate(Long directionID, UpdateDirectionDTO updateDirectionDTO) {
        if (findDirectionByID(directionID).isPresent()) {
            if (updateDirectionDTO.isValid()) {
                Direction updatedDirection = findDirectionByID(directionID).get().update(updateDirectionDTO);
                update(updatedDirection);
                return updatedDirection;
            } else throw new ObjectIsEmptyException();
        } else throw new ObjectNotFoundException("Direction", directionID);

    }


}
