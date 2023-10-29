package com.example.cvservice.Service;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortedList {

    public List<Sort.Order> getAscendingSortedList(List<String> params) {
        List<Sort.Order> orderList = new ArrayList<>();
        for (String param : params) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, param));
        }
        return orderList;
    }

}
