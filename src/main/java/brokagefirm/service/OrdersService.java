package brokagefirm.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brokagefirm.constants.CommonConstants;
import brokagefirm.constants.OrderStatuses;
import brokagefirm.dto.OrdersDto;
import brokagefirm.model.Asset;
import brokagefirm.model.Customer;
import brokagefirm.model.Orders;
import brokagefirm.repository.AssetRepository;
import brokagefirm.repository.CustomerRepository;
import brokagefirm.repository.OrderRepository;

@Service
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Orders createOrder(Long customerId, String assetName, String side, int size, BigDecimal price) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Asset asset = assetRepository.findByCustomerAndAssetName(customer, CommonConstants.TRY)
                .orElseThrow(() -> new IllegalArgumentException("TRY asset not found for customer"));

        if (side.equals("BUY")) {
            if (asset.getUsableSize() < size * price.intValue()) {
                throw new IllegalArgumentException("Insufficient TRY balance");
            }
            asset.setUsableSize(asset.getUsableSize() - size * price.intValue()); 
        } else if (side.equals("SELL")) {
            Asset sellAsset = assetRepository.findByCustomerAndAssetName(customer, assetName)
                    .orElseThrow(() -> new IllegalArgumentException("Asset not found for customer"));
            // check whether has asset
            if (sellAsset.getUsableSize() < size) {
                throw new IllegalArgumentException("Insufficient asset size");
            }
            sellAsset.setUsableSize(sellAsset.getUsableSize() - size);
        }

        // Emir oluştur ve kaydet
        Orders order = new Orders();
        order.setCustomer(customer);
        order.setAssetName(assetName);
        order.setOrderSide(side);
        order.setSize(size);
        order.setPrice(price);
        order.setStatus(OrderStatuses.PENDING);
        order.setCreateDate(LocalDateTime.now());

        assetRepository.save(asset); 
        orderRepository.save(order);
        return order;
    }

    public List<OrdersDto> listOrders(Long customerId, String startDate, String endDate) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return orderRepository.findByCustomerAndStatus(customer, "PENDING")
                .stream()
                .filter(order -> order.getCreateDate().isAfter(start) && order.getCreateDate().isBefore(end)).map(e -> e.toDto())
                .collect(Collectors.toList());
    }

    public void cancelOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!order.getStatus().equals("PENDING")) {
            throw new IllegalArgumentException("Only PENDING orders can be canceled");
        }

        //load assets
        if (order.getOrderSide().equals("BUY")) {
            Asset asset = assetRepository.findByCustomerAndAssetName(order.getCustomer(), CommonConstants.TRY)
                    .orElseThrow(() -> new IllegalArgumentException("TRY asset could not found"));
            asset.setUsableSize(asset.getUsableSize() + order.getSize() * order.getPrice().intValue());
            assetRepository.save(asset);
        } else if (order.getOrderSide().equals("SELL")) {
            Asset asset = assetRepository.findByCustomerAndAssetName(order.getCustomer(), order.getAssetName())
                    .orElseThrow(() -> new IllegalArgumentException("Asset could not found"));
            asset.setUsableSize(asset.getUsableSize() + order.getSize());
            assetRepository.save(asset);
        }

        order.setStatus(OrderStatuses.CANCELED);
        orderRepository.save(order);
    }
}