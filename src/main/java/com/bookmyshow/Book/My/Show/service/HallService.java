package com.bookmyshow.Book.My.Show.service;

import com.bookmyshow.Book.My.Show.exceptions.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.exceptions.UnAuthorized;
import com.bookmyshow.Book.My.Show.exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.models.*;
import com.bookmyshow.Book.My.Show.models.dto.request.AddScreenDTO;
import com.bookmyshow.Book.My.Show.models.dto.request.AddShowDTO;
import com.bookmyshow.Book.My.Show.models.dto.request.HallOwnerSignUpDTO;
import com.bookmyshow.Book.My.Show.repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HallService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    RegularUserService regularUserService;

    @Autowired
    ScreenService screenService;

    @Autowired
    MovieService movieService;

    @Autowired
    ShowService showService;



    public ApplicationUser signUp(HallOwnerSignUpDTO hallOwnerSignUpDTO){
        ApplicationUser hallOwner = new ApplicationUser();
        hallOwner.setName(hallOwnerSignUpDTO.getName());
        hallOwner.setEmail(hallOwnerSignUpDTO.getEmail());
        hallOwner.setPassword(hallOwnerSignUpDTO.getPassword());
        hallOwner.setPhoneNumber(hallOwnerSignUpDTO.getPhoneNumber());
        hallOwner.setAge(hallOwnerSignUpDTO.getCompanyAge());
        hallOwner.setType(hallOwnerSignUpDTO.getType().toString());
        List<Hall>halls = hallOwnerSignUpDTO.getHalls();
        applicationUserRepository.save(hallOwner);
        for(Hall hall : halls){
            hallRepository.save(hall);
        }
//        applicationUserRepository.save(hallOwner);
        return hallOwner;
    }

    public Hall getHallBYId(UUID id){
        return hallRepository.findById(id).orElse(null);
    }

    public void addScreens(AddScreenDTO addScreenDTO,String email){
        List<Screen>screens = addScreenDTO.getScreens();
        UUID hallId = addScreenDTO.getHallId();

        ApplicationUser user = regularUserService.getUserBYEmail(email);
        if(user == null){
            throw new UserDoesNotExistException("User with this email does not exist");

        }
        if(!user.getType().equals("HallOwner")){
            throw new UnAuthorized("User does not have access to perform this access");
        }

        Hall hall = getHallBYId(hallId);
        if(hall == null){
            throw new ResourceNotExistException(String.format("Hall id %s does not exist in system",hallId.toString()));
        }

        if(hall.getOwner().getId() != user.getId()){
            throw new UnAuthorized("User does not own this halid");
        }

        for(Screen screen : screens){
            screen.setHall(hall);
            screenService.registerScreen(screen);
        }
    }

    public Show createShows(AddShowDTO addShowDTO,String email){
        //validate email exist in application user table or not
        ApplicationUser user = applicationUserRepository.findByEmail(email);
        if(user==null){
            throw new UserDoesNotExistException(String.format("User with email id %s does not exist in system" ,email));
        }
        if(!user.getType().equals("HallOwner")){
            throw new UnAuthorized(String.format("User with emailId %s does not have required permission to perform this action" ,email));
        }
        UUID hallId = addShowDTO.getHallId();
        //validate this particular hallId is existing in our system or not
        //if not throw ResourceNotFoundException because hall is a resource here and it is not existing

        Hall hall = getHallBYId(hallId);
        if(hall == null){
            throw new ResourceNotExistException(String.format("Hall with id %s does not exist in System",hallId.toString()));
        }
        //validate user owns this hall or not
        //if user does not own this hall user is unAuthorized to create shows

        if(hall.getId()!=user.getId()){
            throw new UnAuthorized(String.format("User with emailId %s does not own hall with HallId %s",email,hallId.toString()));

        }

        //validate movieId which we got from AddShowDTo is existing in our system
        //if not then we need to throw ResourceNOtFound Exception because movie is a resource in our Application

        UUID movieId = addShowDTO.getMovieId();
        Movie movie = movieService.getMovieBYId(movieId);
        if(movie == null){
            throw new ResourceNotExistException(String.format("Movie with movieID %s does not exist in system",movieId));
        }

        //All the validation passed
        //Get Screens that are not occupied
        List<Screen>screens = new ArrayList<>();
        for(Screen screen:hall.getScreens()){
            if(screen.isStatus() == false){
                screens.add(screen);
            }

        }
        //if after the for loop list screens is having size as zero then we should throw one exception
        //Resource not found Exception
        if(screens.size()==0){
            throw new ResourceNotExistException(String.format("Hall with hallId %s does not have any occupied screen",hallId.toString()));

        }
        Screen screen = screens.get(0);
        Show show = new Show();
        show.setHall(hall);
        show.setMovie(movie);
        show.setAvailableTickets(screen.getScreenCapacity());
        show.setTicketPrice(addShowDTO.getTicketPrice());
        show.setScreen(screen);


        Date startDateTime = new Date();
        startDateTime.setHours(addShowDTO.getHours());
        startDateTime.setMinutes(addShowDTO.getMinutes());
        //
        Date endDateTime = new Date();
        int hour =  (int)(addShowDTO.getHours() + movie.getDuration())%24;
        endDateTime.setHours(hour);
        endDateTime.setMinutes(addShowDTO.getMinutes());
        show.setStartTime(startDateTime);
        show.setEndTime(endDateTime);

        //mark status of screen as true .Such that no other show can book that
        screenService.bookScreen(screen.getId());
        showService.createShow(show);
        return show;
        }

    }

