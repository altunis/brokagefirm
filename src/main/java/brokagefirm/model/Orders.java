package brokagefirm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import brokagefirm.dto.OrdersDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    private String assetName;
    private String orderSide;
    private int size;
    private BigDecimal price;
    private String status;
    private LocalDateTime createDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getOrderSide() {
		return orderSide;
	}
	public void setOrderSide(String orderSide) {
		this.orderSide = orderSide;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
    
	public OrdersDto toDto() {
		return new OrdersDto(getId(), getCustomer().toDto(), getAssetName(), getOrderSide(), getSize(), getPrice(), getStatus(), getCreateDate());
	}
    
}