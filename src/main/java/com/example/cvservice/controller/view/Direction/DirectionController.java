package com.example.cvservice.controller.view.Direction;

import com.example.cvservice.dto.Direction.NewDirectionDTO;
import com.example.cvservice.dto.Direction.UpdateDirectionDTO;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.service.Direction.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/task/directions")
public class DirectionController {
    @Autowired
    private DirectionService directionService;

    @GetMapping("/view/all")
    public String displayAllDirectionsPage(@RequestParam(required = false) Optional<Integer> page,
                                           @RequestParam(required = false) Optional<Integer> size,
                                           @RequestParam(required = false, defaultValue = "name") String sort,
                                           @RequestParam(required = false, defaultValue = "ASC") String direction,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String description,
                                           Model model) {

        Page<Direction> directionsByParams = directionService.findDirectionsByParams(page.orElse(0), size.orElse(10), name, description, sort, direction);


        model.addAttribute("directions", directionsByParams);
        model.addAttribute("filterName", name);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("filterDesc", description);
        model.addAttribute("pageSize", size.orElse(10));
        model.addAttribute("pages", directionsByParams.getTotalPages());

//        model.addAttribute("directions", directionService.findAll());
        return "/directions/all_directions_page";
    }


    @GetMapping("/add")
    public String displayAddNewDirectionsPage(Model model) {
        model.addAttribute("newDirection", new NewDirectionDTO());
        return "/directions/add_new_direction_page";
    }

    @GetMapping("/direction/{id}")
    public String displayDirectionPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
        Direction direction = null;
        if (optionalDirection.isPresent()) {
            direction = optionalDirection.get();
        }
        model.addAttribute("direction", direction);
        return "/directions/direction_page";

    }

    @GetMapping("/direction/edit/{id}")
    public String editDirectionsByID(@PathVariable(value = "id") Long id, Model model) {
        Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
        UpdateDirectionDTO updateDirectionDTO = new UpdateDirectionDTO();
        Direction direction = null;
        if (optionalDirection.isPresent()) {
            direction = optionalDirection.get();
            updateDirectionDTO.setId(direction.getId());
            updateDirectionDTO.setName(direction.getName());
            updateDirectionDTO.setDescription(direction.getDescription());
            model.addAttribute("updateDirection", updateDirectionDTO);
        } else {
            model.addAttribute("updateDirection", null);
        }
        return "/directions/edit_direction_page";
    }
}
