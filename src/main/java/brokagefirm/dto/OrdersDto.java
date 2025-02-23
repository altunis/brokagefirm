package brokagefirm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdersDto( Long id,CustomerDto customer,String assetName,String orderSide,int size,BigDecimal price,String status, LocalDateTime createDate) {

}
