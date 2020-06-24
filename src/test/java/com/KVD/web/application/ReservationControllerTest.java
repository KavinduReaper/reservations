package com.KVD.web.application;

import com.KVD.randika.business.domain.RoomReservation;
import com.KVD.randika.business.service.ReservationService;
import com.KVD.randika.web.application.ReservationController;
import jdk.net.SocketFlow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;
    @Autowired
    private MockMvc mockMvc;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");

    @Test
    public void getReservations() throws Exception{
        String dateString = "2017-10-20";
        Date date = DATE_FORMAT.parse(dateString);

        List<RoomReservation> mockReservationList = new ArrayList<>();
        RoomReservation mockRoomReservation = new RoomReservation();
        mockRoomReservation.setLastName("Test");
        mockRoomReservation.setFirstName("Junit");
        mockRoomReservation.setDate(date);
        mockRoomReservation.setGuestId(1);
        mockRoomReservation.setRoomNumber("j1");
        mockRoomReservation.setRoomId(100);
        mockRoomReservation.setRoomName("Junit Room");
        mockReservationList.add(mockRoomReservation);

        given(reservationService.getRoomReservationForDate(dateString)).willReturn(mockReservationList);
        this.mockMvc.perform(get("/reservations?date=2017-10-20")).andExpect(status().isOk()).andExpect(content().string(containsString("Test, Junit")));
    }
}
