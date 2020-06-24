package com.KVD.randika.business.service;

import com.KVD.randika.business.domain.RoomReservation;
import com.KVD.randika.data.entity.Guest;
import com.KVD.randika.data.entity.Reservation;
import com.KVD.randika.data.entity.Room;
import com.KVD.randika.data.repository.GuestRepository;
import com.KVD.randika.data.repository.ReservationRepository;
import com.KVD.randika.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationForDate(String dateString){
        Date date = this.createDateFromDateString(dateString);
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
        if (null != reservations){
            reservations.forEach(reservation -> {
                Optional<Guest> guestResponse = this.guestRepository.findById(reservation.getGuestId());
                if (guestResponse.isPresent()){
                    Guest guest = guestResponse.get();
                    RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
                    roomReservation.setDate(date);
                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                    roomReservation.setGuestId(guest.getId());
                }
            });
        }
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long roomId:roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(roomId));
        }
        return roomReservations;
    }
    private Date createDateFromDateString(String dateString){
        Date date = null;
        if (null != dateString){
            try {
                date = DATE_FORMAT.parse(dateString);
            }
            catch (ParseException pe){
                date = new Date();
            }
        }
        else date = new Date();
        return date;
    }
}
