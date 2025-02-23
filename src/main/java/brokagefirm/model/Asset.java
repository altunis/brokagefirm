package brokagefirm.model;

import brokagefirm.dto.AssetDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    private String assetName;
    private int size;
    private int usableSize;
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
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getUsableSize() {
		return usableSize;
	}
	public void setUsableSize(int usableSize) {
		this.usableSize = usableSize;
	}
    
	public AssetDto toDto() {
		return new AssetDto(getId(), getCustomer().toDto(), getAssetName(), getSize(), getUsableSize());
	}
    
}

