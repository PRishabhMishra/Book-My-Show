package com.bookmyshow.Book.My.Show.service;

import com.bookmyshow.Book.My.Show.exceptions.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.exceptions.UnAuthorized;
import com.bookmyshow.Book.My.Show.exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.models.*;
import com.bookmyshow.Book.My.Show.repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    ShowService showService;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    HallService hallService;

    @Autowired
    MailService mailService;

    @Autowired
     BarCodeGenerationService barCodeGenerationService;

    public Ticket buyTicket(String email, UUID showId){
        //.get user by emailId
        ApplicationUser user = applicationUserRepository.findByEmail(email);
        //if user is null
        if(user==null){
            throw new UserDoesNotExistException(String.format("User with emailId %s does not exist in system ",email));
        }
        //check user has required access or not
        if(!user.getType().equals("RegularUser")){
            throw new ResourceNotExistException(String.format("User with email %s does not have required access",email));
        }
        //validate show with showId
        Show show = showService.getShowByShowId(showId);
        if(show == null){
            throw new ResourceNotExistException(String.format("show with id %s does not exist in our system",showId));
        }

        //we have to decrease Ticket count for this particular showId as we are buying one ticket.
        showService.updateAvailableTicketCount(show);

        Ticket ticket = new Ticket();
        ticket.setHall(show.getHall());
        ticket.setMovie(show.getMovie());
        ticket.setShow(show);
        ticket.setUser(user);
        ticketRepository.save(ticket);

        Movie movie =movieService.getMovieBYId(show.getMovie().getId());
        Hall hall = hallService.getHallBYId(show.getHall().getId());

        //send ticket details to user
        String userMessage = String.format("Hey %s ,\n" +
                "Congratulations your ticket got booked on our application .Below are your ticket details:\n+" +
                "1.Movie Name -%s\n"+
                "2.Hall Name - %s\n"+
                "3.Hall Address - %s\n"+
                "4.Date and timing - %s\n"+
                "5.Ticket Price - %d\n"+
                "\n Hope you will enjoy your show ,All the Best\n"+
                "Accio Booking Application",user.getName(),movie.getName(),hall.getName(),hall.getAddress(),show.getStartTime().toString(),show.getTicketPrice());
        String userSub = String.format("Congratulations!! %s your ticket got generated",user.getName());
        try{
            barCodeGenerationService.generateQR(userMessage);

        }catch(Exception e){
            e.printStackTrace();

        }
        mailService.generateMail(user.getEmail(),userSub,userMessage,"./src/main/resources/static/QRCode.png");

        int totalTickets = movieService.getTotalTicketCount(movie);
        int totalIncome = movieService.getBoxOfficeCollection(movie);

        String movieMessage = String.format("Hi %s\n" +
                "Congratulations!! your ticket got sold\n"+
                "TotalTicketSold :%d"+
                "TotalIncome : %d",movie.getOwner().getEmail(),totalTickets,totalIncome);
        String movieSubject = String.format("Congratulations!! %s One more Ticket sold",movie.getOwner().getName());
        mailService.generateMail(movie.getOwner().getEmail(),movieSubject,movieMessage                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   );
        return ticket;

    }
}
