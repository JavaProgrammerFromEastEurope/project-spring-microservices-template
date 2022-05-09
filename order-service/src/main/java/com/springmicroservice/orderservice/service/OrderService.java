package com.springmicroservice.orderservice.service;

import java.util.List;
import java.util.UUID;

import com.springmicroservice.orderservice.dto.OrderLineItemsDto;
import com.springmicroservice.orderservice.dto.OrderRequest;
import com.springmicroservice.orderservice.model.Order;
import com.springmicroservice.orderservice.model.OrderLineItems;
import com.springmicroservice.orderservice.repository.OrderRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		var orderLineItems = orderRequest.getOrderLineItemsDtoList()
				.stream()
				.map(this::mapToDto).toList();

		order.setOrderLineItemsList(orderLineItems);
		orderRepository.save(order);
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		var orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
