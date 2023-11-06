package com.example.cvservice.service;

import com.example.cvservice.dto.NewDirectionDTO;
import com.example.cvservice.dto.UpdateDirectionDTO;
import com.example.cvservice.entity.Direction;
import com.example.cvservice.exception.ObjectAlreadyExistsException;
import com.example.cvservice.exception.ObjectIsEmptyException;
import com.example.cvservice.exception.ObjectNotFoundException;
import com.example.cvservice.service.Filter.DirectionFilter;
import com.example.cvservice.repository.DirectionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(DirectionService.class);


    public List<Direction> findAll() {
        return repository.findAll();
    }

    public List<Direction> findAllByNames(List<String> names) {
        return repository.findAllByNameIn(names);
    }

    public Page<Direction> findDirectionsByParams(int page, int size, String name, String description, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sort));
        logger.info("page with directions created");
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

    public Direction saveNewDirection(NewDirectionDTO newDirectionDTO) {
        logger.info("new direction incoming");
        if (!findDirectionByName(newDirectionDTO.getName()).isPresent()) {
            logger.info("new direction doesen't exists");
            if (newDirectionDTO.isValid()) {
                logger.info("new direction validated");
                Direction direction = Direction.builder().name(newDirectionDTO.getName()).description(newDirectionDTO.getDescription()).build();
                save(direction);
                logger.info("new direction created");
                return direction;
            } else {
                logger.warn("new direction has been rejected");
                throw new ObjectIsEmptyException();
            }
        }
        throw new ObjectAlreadyExistsException("Direction", newDirectionDTO.getName());
    }
    public Direction updateDirection(Long directionID, UpdateDirectionDTO updateDirectionDTO) {
        logger.info("updated direction incoming");
        if (findDirectionByID(directionID).isPresent()) {
            logger.info("updated direction exists");
            if (updateDirectionDTO.isValid()) {
                logger.info("updated direction validated");
                Direction updatedDirection = findDirectionByID(directionID).get().update(updateDirectionDTO);
                update(updatedDirection);
                logger.info("direction updated");
                return updatedDirection;
            } else {
                logger.warn("updated direction has been rejected");
                throw new ObjectIsEmptyException();
            }
        } else throw new ObjectNotFoundException("Direction", directionID);

    }


}
