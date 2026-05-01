package com.hamza.ordermanagementsystem.dto.projection;

import java.math.BigDecimal;

public interface OrderSummaryDTO {

    Long getId();
    BigDecimal getTotalAmount();
    Long getItemCount();
}
