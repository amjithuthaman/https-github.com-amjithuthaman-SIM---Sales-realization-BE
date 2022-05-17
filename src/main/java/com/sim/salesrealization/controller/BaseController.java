package com.sim.salesrealization.controller;

import com.sim.salesrealization.model.DropDownEnum;
import com.sim.salesrealization.model.DropDownRequest;
import com.sim.salesrealization.model.SalesAggregatorResult;
import com.sim.salesrealization.service.SalesService;
import com.sim.salesrealization.topology.SalesTopology;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales/realization")
@Validated
public class BaseController {

    private final SalesService salesService;

    public BaseController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping(value = "/aggr/sales", produces = "application/json")
    public ResponseEntity<List<SalesAggregatorResult>> getResult() {
        var result = salesService.getResult();
        if(0 ==result.size()){
            var topology = SalesTopology.buildTopology();
           // var kafkaStreams = new KafkaStreams(topology, streamConfiguration);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(value= "/dropdown" ,produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> reportRecords(@RequestBody DropDownRequest reqObject) {
        Map<DropDownEnum, List<?>> response = new HashMap<>();
        try {
            response = salesService.getDropdown(reqObject);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
           // response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
