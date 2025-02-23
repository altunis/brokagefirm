package brokagefirm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import brokagefirm.dto.AssetDto;
import brokagefirm.model.Asset;
import brokagefirm.service.AssetService;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PreAuthorize("@authService.userHasAuthorization(#customerId)")
    @PostMapping("/deposit")
    public ResponseEntity<Asset> deposit(@RequestParam Long customerId, @RequestParam Integer amount) {
        Asset asset = assetService.depositMoney(customerId, amount);
        return ResponseEntity.ok(asset);
    }

    @PreAuthorize("@authService.userHasAuthorization(#customerId)")
    @PostMapping("/withdraw")
    public ResponseEntity<Asset> withdraw(@RequestParam Long customerId, @RequestParam Integer amount) {
        Asset asset = assetService.withdrawMoney(customerId, amount);
        return ResponseEntity.ok(asset);
    }

    @PreAuthorize("@authService.userHasAuthorization(#customerId)")
    @GetMapping("/list")
    public ResponseEntity<List<AssetDto>> listAssets(@RequestParam Long customerId) {
        List<AssetDto> assets = assetService.listAssets(customerId);
        return ResponseEntity.ok(assets);
    }
}