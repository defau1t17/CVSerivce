package com.example.cvservice.Service;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortedList {

    public List<Sort.Order> getAscendingSortedList(String type, String direction) {
        List<Sort.Order> orderList = new ArrayList<>();
        if (Sort.Direction.valueOf(direction).equals(Sort.Direction.ASC)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, type));
        } else if (Sort.Direction.valueOf(direction).equals(Sort.Direction.DESC)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, type));

        }

        return orderList;
    }

}
