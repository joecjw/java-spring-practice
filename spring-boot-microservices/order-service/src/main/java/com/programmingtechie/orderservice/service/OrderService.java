package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.event.OrderPlacedEvent;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    private final Tracer tracer;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
        try (Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())) {
            //call inventory service and place order if product is in stock
            InventoryResponse[] inventoryResponsesArray = webClientBuilder.build()
                    .get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder
                                    .queryParam("skuCodes", skuCodes)
                                    .build()
                    )
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            Boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                    .allMatch(inventoryResponse -> inventoryResponse.getIsInStock().booleanValue());

            if(allProductsInStock.booleanValue()){
                orderRepository.save(order);
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return  "Order Placed Successfully!"
                        +"An Confirmation Email Has Been Sent To Your Email Address.";
            }else {
                throw new IllegalArgumentException("Product Is NOT In Stock Now, Please Try Again Later");
            }
        }finally {
            inventoryServiceLookup.end();
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
