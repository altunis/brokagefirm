package brokagefirm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import brokagefirm.model.Asset;
import brokagefirm.model.Customer;
import brokagefirm.repository.CustomerRepository;
import brokagefirm.service.AssetService;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BrokageFirmMain.class)
public class Tests {

    @Autowired
    private AssetService assetService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testDepositMoney() {
        Customer customer = new Customer();
        customer.setName("Customer test");
        customerRepository.save(customer);
        Asset asset = assetService.depositMoney(customer.getId(), 3000);
        assertNotNull(asset);
        assertEquals("TRY", asset.getAssetName());
        assertEquals(3000, asset.getSize());
    }

    @Test
    public void testWithdrawMoney() {
        Customer customer = new Customer();
        customer.setName("Customer Test");
        customerRepository.save(customer);

        Asset asset = assetService.depositMoney(customer.getId(), 5000);
        asset = assetService.withdrawMoney(customer.getId(), 2500);

        assertEquals(2500, asset.getUsableSize());
    }
}