package brokagefirm.dto;

public record AssetDto(Long id,CustomerDto customer, String assetName, int size, int usableSize) {
}
