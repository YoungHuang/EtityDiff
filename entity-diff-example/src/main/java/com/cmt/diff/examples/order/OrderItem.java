package com.cmt.diff.examples.order;

import com.cmt.diff.Entity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(of = {"id"})
public class OrderItem implements Entity<Long> {
    private Long id;
    private String productName;
    private Date updateTime;
}
