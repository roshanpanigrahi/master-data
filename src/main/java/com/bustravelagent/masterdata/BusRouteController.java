package com.bustravelagent.masterdata;

import com.bustravelagent.masterdata.model.BusRoute;
import com.bustravelagent.masterdata.repository.BusRouteRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/v1")
public class BusRouteController {

    private static final Logger log = LoggerFactory.getLogger(BusRouteController.class);

    BusRouteRepository busRouteRepository;

    public BusRouteController(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }

    @GetMapping("/get/busRoute")
    public ResponseEntity<BusRoute> getBusRoute(@RequestParam("source") String source,
                                              @RequestParam("destination") String destination)
    {
        log.info("Fetching BusRoute...");
        Optional<BusRoute> optionalBusRoute = Optional
                .ofNullable(busRouteRepository.getBusRouteBySourceAndDestination(source, destination));
        return getBusRouteResponseEntity(optionalBusRoute);

    }

    @NotNull
    private ResponseEntity<BusRoute> getBusRouteResponseEntity(Optional<BusRoute> optionalBusRoute) {
        return optionalBusRoute.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    BusRoute badBusRoute = new BusRoute();
                    badBusRoute.setBusNo(0);
                    badBusRoute.setSource("Not Found");
                    badBusRoute.setDestination("Not Found");
                    badBusRoute.setPrice(0);
                    return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(badBusRoute);
                });
    }

    @GetMapping("/get/bus/{busNo}")
    public ResponseEntity<BusRoute> getBusRouteByBusNo(@PathVariable Integer busNo) {

        Optional<BusRoute> optionalBusRoute = Optional
                .ofNullable(busRouteRepository.getBusRouteByBusNo(busNo));
        return getBusRouteResponseEntity(optionalBusRoute);
    }


    @PostMapping("/add/busRoute")
    public ResponseEntity<String> upsertBusRoute(
            @RequestBody BusRoute busRoute,
            HttpServletRequest request,
            HttpServletResponse httpResponse) {
        BusRoute busRouteDb = new BusRoute();
        AtomicReference<String> msg = new AtomicReference<>(null);
        Integer busNo = (Integer) busRoute.getBusNo();

        Optional<BusRoute> optionalBusRoute = Optional
                .ofNullable(busRouteRepository.getBusRouteByBusNo( busNo));

        optionalBusRoute.ifPresentOrElse(busRoute1 -> {
            busRouteRepository.updateBusRouteDetails(
                    busNo,
                    busRoute.getSource(),
                    busRoute.getDestination(),
                    (Integer) busRoute.getPrice()
            );
            msg.set("Bus Route updated successfully!");
        }, () -> {
            BusRoute addBusRoute = new BusRoute();
            addBusRoute.setBusNo((Integer) busRoute.getBusNo());
            addBusRoute.setSource(busRoute.getSource());
            addBusRoute.setDestination(busRoute.getDestination());
            addBusRoute.setPrice((Integer) busRoute.getPrice());
            busRouteRepository.save(addBusRoute);
            msg.set("Bus Route added successfully!");
        });

        return ResponseEntity.ok().body(msg.get().toString());

    }





}
