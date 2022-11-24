package com.shop.dto;

// dto : 화면에 표시하기 위한 용도

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ItemDto {
    private Long id;
    private String itemName;
    private Integer price;
    private String itemDetail;
    private String sellState;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
