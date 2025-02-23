package brokagefirm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import brokagefirm.model.Asset;
import brokagefirm.model.Customer;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByCustomerAndAssetName(Customer customer, String assetName);

    List<Asset> findByCustomer(Customer customer);
}
