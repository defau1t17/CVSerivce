package com.example.cvservice.controller.view.Direction;

import com.example.cvservice.controller.view.Candidate.CandidatesController;
import com.example.cvservice.dto.Direction.NewDirectionDTO;
import com.example.cvservice.dto.Direction.UpdateDirectionDTO;
import com.example.cvservice.entity.PageConstants;
import com.example.cvservice.entity.main.Direction;
import com.example.cvservice.service.Direction.DirectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/directions")
public class DirectionController {
    @Autowired
    private DirectionService directionService;

    Logger logger = LoggerFactory.getLogger(DirectionController.class);

    @GetMapping("")
    public String displayAllDirectionsPage(@RequestParam(required = false) Optional<Integer> page,
                                           @RequestParam(required = false) Optional<Integer> size,
                                           @RequestParam(required = false, defaultValue = "name") String sort,
                                           @RequestParam(required = false, defaultValue = "ASC") String direction,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String description,
                                           Model model) {
        Page<Direction> directionsByParams = directionService.findDirectionsByParams(page.orElse(PageConstants.DEFAULT_PAGE_NUMBER), size.orElse(PageConstants.DEFAULT_PAGE_SIZE), name, description, sort, direction);
        model.addAttribute("directions", directionsByParams);
        model.addAttribute("filterName", name);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("filterDesc", description);
        model.addAttribute("pageSize", size.orElse(PageConstants.DEFAULT_PAGE_SIZE));
        model.addAttribute("pages", directionsByParams.getTotalPages());
        logger.info("all directions page works");
        return "/directions/all_directions_page";
    }

    @GetMapping("/add")
    public String displayAddNewDirectionsPage(Model model) {
        model.addAttribute("newDirection", new NewDirectionDTO());
        logger.info("add new direction page works");
        return "/directions/add_new_direction_page";
    }

    @GetMapping("/{id}")
    public String displayDirectionPage(@PathVariable(value = "id") Long id, Model model) {
        Optional<Direction> optionalDirection = directionService.findDirectionByID(id);
        Direction direction = null;
        if (optionalDirection.isPresent()) {
            direction = optionalDirection.get();
        }
        model.addAttribute("direction", direction);
        logger.info("direction by id page works");

        return "/directions/direction_page";

    }

    @GetMapping("/edit/{id}")
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
            logger.info("edit entity created");

        } else {
            model.addAttribute("updateDirection", null);
        }
        logger.info("edit direction by id page works");

        return "/directions/edit_direction_page";
    }
}
