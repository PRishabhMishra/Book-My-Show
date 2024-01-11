package com.bookmyshow.Book.My.Show.service;

import com.bookmyshow.Book.My.Show.models.ApplicationUser;
import com.bookmyshow.Book.My.Show.models.Movie;
import com.bookmyshow.Book.My.Show.models.Ticket;
import com.bookmyshow.Book.My.Show.models.dto.request.MovieOwnerSignUpDTO;
import com.bookmyshow.Book.My.Show.repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    MovieRepository movieRepository;

    public ApplicationUser signUp(MovieOwnerSignUpDTO movieOwnerSignUpDTO){
        ApplicationUser movieOwners = new ApplicationUser();
        movieOwners.setName(movieOwnerSignUpDTO.getName());
        movieOwners.setEmail(movieOwnerSignUpDTO.getEmail());
        movieOwners.setPassword(movieOwnerSignUpDTO.getPassword());
        movieOwners.setPhoneNumber(movieOwnerSignUpDTO.getPhoneNumber());
        movieOwners.setAge(movieOwnerSignUpDTO.getCompanyAge());
        movieOwners.setType(movieOwnerSignUpDTO.getType().toString());
        List<Movie> movies = movieOwnerSignUpDTO.getMovies();
        applicationUserRepository.save(movieOwners);
        for(Movie movie: movies){
            movie.setOwner(movieOwners);
            movieRepository.save(movie);
        }
//        applicationUserRepository.save(movieOwners);
        return movieOwners;
    }

    public Movie getMovieBYId(UUID id){
        return movieRepository.findById(id).orElse(null);
    }

    public int getTotalTicketCount(Movie movie){
        return movie.getTickets().size();
    }

    public int getBoxOfficeCollection(Movie movie) {
        int totalIncome = 0;
        for (Ticket ticket : movie.getTickets()) {
            totalIncome += ticket.getShow().getTicketPrice();
        }
        return totalIncome;
    }
}
