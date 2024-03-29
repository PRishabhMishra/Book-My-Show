package com.bookmyshow.Book.My.Show.controller;

import com.bookmyshow.Book.My.Show.exceptions.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.exceptions.UnAuthorized;
import com.bookmyshow.Book.My.Show.exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.models.Ticket;
import com.bookmyshow.Book.My.Show.models.dto.response.GeneralMessageDTO;
import com.bookmyshow.Book.My.Show.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping("/buyTicket")
    public ResponseEntity buyTicket(@RequestParam String email, @RequestParam UUID showID){
        try{
            Ticket ticket = ticketService.buyTicket(email,showID);
            return new ResponseEntity(ticket, HttpStatus.CREATED);

        }catch(UnAuthorized e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.UNAUTHORIZED);//401
        }
        catch(ResourceNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);//404
        }
        catch(UserDoesNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);//404
        }

    }
}
