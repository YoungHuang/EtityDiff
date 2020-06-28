package com.cmt.diff.examples.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        OrderRepositry orderRepositry = new OrderRepositoryImpl();
        updateOrderItem(orderRepositry);
    }

    public static void createOrder(OrderRepositry orderRepositry) {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = OrderItem.builder()
                .productName("macbook pro")
                .build();
        orderItems.add(item);

        item = OrderItem.builder()
                .productName("mac air")
                .build();
        orderItems.add(item);

        Order order = Order.builder()
                .orderNo("abc")
                .orderItems(orderItems)
                .build();

        orderRepositry.save(order);
    }

    public static void findAndUpdateOrder(OrderRepositry orderRepositry) {
        Order order = orderRepositry.find(111L);

        order.setOrderNo("xxx");
        orderRepositry.save(order);
    }

    public static void deleteOrderItem(OrderRepositry orderRepositry) {
        Order order = orderRepositry.find(111L);
        System.out.println(order.getId());

        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.remove(0);
        orderRepositry.save(order);
    }

    public static void addAndDeleteOrderItem(OrderRepositry orderRepositry) {
        Order order = orderRepositry.find(111L);

        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.remove(0);

        OrderItem item = OrderItem.builder()
                .productName("new item")
                .build();
        orderItems.add(item);
        orderRepositry.save(order);
    }

    public static void updateOrderItem(OrderRepositry orderRepositry) {
        Order order = orderRepositry.find(111L);

        List<OrderItem> orderItems = order.getOrderItems();

        OrderItem item = orderItems.get(0);
        item.setProductName("updated item");
        item.setUpdateTime(new Date());
        orderRepositry.save(order);

        item.setProductName("updated2 item");
        orderRepositry.save(order);
    }
}
