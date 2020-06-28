package com.cmt.diff.examples.order;

import com.cmt.diff.ChildrenVisitor;
import com.cmt.diff.EntityDiffRepository;
import de.danielbechler.diff.node.DiffNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepositoryImpl extends EntityDiffRepository<Order, Long>  implements OrderRepositry {
    // private OrderDao orderDao;

    @Override
    protected void onInsert(Order order) {
        order.setId(222L);
        System.out.println("insert order: " + order.getId());
        order.getOrderItems().forEach(item -> item.setId(2221L));
    }

    @Override
    protected Order onSelect(Long id) {
        // orderDao.query();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = OrderItem.builder()
                .id(1111L)
                .productName("macbook pro")
                .build();
        orderItems.add(item);

        item = OrderItem.builder()
                .id(1112L)
                .productName("mac air")
                .build();
        orderItems.add(item);

        Order order = Order.builder()
                .id(111L)
                .orderNo("abc")
                .orderItems(orderItems)
                .build();
        return order;
    }

    @Override
    protected void onUpdate(Order order, Order oldOrder, DiffNode diff) {
        // update Order
        order.setUpdateTime(new Date());
        System.out.println("update order: " + order.getId());

        DiffNode itemsDiff = diff.getChild("orderItems");
        if (itemsDiff != null) {
            itemsDiff.visitChildren(new ChildrenVisitor()
            {
                @Override
                protected void child(DiffNode node) {
                    switch (node.getState()) {
                        case CHANGED:
                            OrderItem orderItem = (OrderItem) node.canonicalGet(order);
                            System.out.println("CHANGED: " + orderItem.getId());
                            break;
                        case ADDED:
                            orderItem = (OrderItem) node.canonicalGet(order);
                            System.out.println("ADDED: " + orderItem.getId());
                            break;
                        case REMOVED:
                            orderItem = (OrderItem) node.canonicalGet(oldOrder);
                            System.out.println("REMOVED: " + orderItem.getId());
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onDelete(Order order) {
        System.out.println("delete order: " + order.getId());
    }
}
