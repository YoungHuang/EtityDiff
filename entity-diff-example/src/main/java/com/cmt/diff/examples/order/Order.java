package com.cmt.diff.examples.order;

import com.cmt.diff.Entity;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(of = {"id"})
public class Order implements Entity<Long> {
    private Long id;
    private String orderNo;
    private List<OrderItem> orderItems;
    private Date updateTime;
}
