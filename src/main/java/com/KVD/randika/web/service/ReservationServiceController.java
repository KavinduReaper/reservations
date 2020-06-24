package com.KVD.randika.web.service;

import com.KVD.randika.business.domain.RoomReservation;
import com.KVD.randika.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ReservationServiceController {

    @Autowired
    private ReservationService reservationService;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");

    @RequestMapping(method = RequestMethod.GET, value = "/reservations/{date}")
    public List<RoomReservation> getAllReservationsForDate(@PathVariable(value = "date")String dateString){
        return this.reservationService.getRoomReservationForDate(dateString);
    }
}
