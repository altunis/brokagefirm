package brokagefirm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brokagefirm.constants.CommonConstants;
import brokagefirm.dto.AssetDto;
import brokagefirm.model.Asset;
import brokagefirm.model.Customer;
import brokagefirm.repository.AssetRepository;
import brokagefirm.repository.CustomerRepository;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Asset depositMoney(Long customerId, Integer amount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Asset asset = assetRepository.findByCustomerAndAssetName(customer, CommonConstants.TRY)
                .orElse(new Asset());

        if (asset.getId() == null) {
            asset.setCustomer(customer);
            asset.setAssetName(CommonConstants.TRY);
            asset.setSize(amount);
            asset.setUsableSize(amount);
        } else {
            asset.setSize(asset.getSize() + amount);
            asset.setUsableSize(asset.getUsableSize() + amount);
        }

        return assetRepository.save(asset);
    }

    public Asset withdrawMoney(Long customerId, Integer amount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Asset asset = assetRepository.findByCustomerAndAssetName(customer, CommonConstants.TRY)
                .orElseThrow(() -> new IllegalArgumentException("TRY asset not found"));

        if (asset.getUsableSize() < amount) {
            throw new IllegalArgumentException("TRY asset is out of balance");
        }

        asset.setUsableSize(asset.getUsableSize() - amount);

        return assetRepository.save(asset);
    }

    public List<AssetDto> listAssets(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return (List<AssetDto>) assetRepository.findByCustomer(customer).stream().map(e -> e.toDto());
    }
    
}
