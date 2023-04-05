package fr.polytech.controllers;

import fr.polytech.controllers.dto.StoreDTO;
import fr.polytech.entities.Store;
import fr.polytech.entities.item.Discount;
import fr.polytech.exceptions.BadCredentialsException;
import fr.polytech.exceptions.IllegalDateException;
import fr.polytech.exceptions.discount.DiscountNotFoundException;
import fr.polytech.exceptions.store.MissingInformationsException;
import fr.polytech.exceptions.store.StoreSiretAlreadyUsedException;
import fr.polytech.interfaces.catalog.StatsExplorer;
import fr.polytech.interfaces.store.StoreRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = PartnerStoreController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class PartnerStoreController {
    public static final String BASE_URI = "/stores";

    StoreRegistration storeRegistration;
    StatsExplorer statsExplorer;

    @Autowired
    public PartnerStoreController(StoreRegistration storeRegistration) {
        this.storeRegistration=storeRegistration;
    }

    @PostMapping(path = "/registration", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<StoreDTO> register(@RequestBody @Valid StoreDTO storeDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertStoreToDto(storeRegistration.registerNewStore(storeDTO.getName(), storeDTO.getSiret(), storeDTO.getPassword())));
        } catch (BadCredentialsException | MissingInformationsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (StoreSiretAlreadyUsedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/statistics", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getTotalCostFromBeginning() {
        return ResponseEntity.ok().body(statsExplorer.getOperationCost());
    }

    @PostMapping(path = "/statistics", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getTotalCostFromDate(@RequestBody Date date) throws IllegalDateException {
        return ResponseEntity.ok().body(statsExplorer.getOperationCost(date));
    }

    @GetMapping(path = "/statistics", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getTotalPointsUsedFromBeginning() {
        return ResponseEntity.ok().body(statsExplorer.getTotalPointUsed());
    }

    @PostMapping(path = "/statistics", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getTotalPointUsedFromDate(@RequestBody Date date) throws IllegalDateException {
        return ResponseEntity.ok().body(statsExplorer.getTotalPointUsed(date));
    }

    private StoreDTO convertStoreToDto(Store store) {
        return new StoreDTO(store.getId(), store.getSiret(), store.getName(), store.getPassword());
    }

}
