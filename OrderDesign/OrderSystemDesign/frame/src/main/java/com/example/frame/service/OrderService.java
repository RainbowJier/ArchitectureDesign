package com.example.frame.service;


import com.example.frame.controller.request.ProductRequest;
import com.example.frame.model.JsonData;
import com.example.frame.model.entity.EventMessage;

public interface OrderService {
    JsonData confirmOrder(ProductRequest productRequest);

    void handleCloseOrder(EventMessage eventMessage);
}
