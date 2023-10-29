package com.example.cvservice.Controller.View.Direction;

import com.example.cvservice.DTO.Direction.NewDirectionDTO;
import com.example.cvservice.DTO.Direction.UpdateDirectionDTO;
import com.example.cvservice.Entity.Main.Direction;
import com.example.cvservice.Service.Direction.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/task/directions")
public class DirectionController {
    @Autowired
    private DirectionService directionService;

    @GetMapping("/view/all")
    public String displayAllDirectionsPage(Model model) {
        model.addAttribute("directions", directionService.findAll());
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
