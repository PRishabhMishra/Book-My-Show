package com.bookmyshow.Book.My.Show.service;

import com.bookmyshow.Book.My.Show.models.Movie;
import com.bookmyshow.Book.My.Show.models.Show;
import com.bookmyshow.Book.My.Show.models.Ticket;
import com.bookmyshow.Book.My.Show.repository.HallRepository;
import com.bookmyshow.Book.My.Show.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShowService {
    @Autowired
    ShowRepository showRepository;


    public void createShow(Show show){
        showRepository.save(show);
    }

    public List<Show>getShowsByMovieId(UUID movieId){
        return showRepository.getShowByMovieID(movieId);
    }

    public List<Show>getShowsByHallId(UUID hallId){
        return showRepository.getShowByHallId(hallId);
    }

    public List<Show>getShowsByHallIdAndMovieId(UUID hallId,UUID movieId){
        return showRepository.getShowsByHallIdAndMovieId(hallId,movieId);

    }

    public Show getShowByShowId(UUID showId){
        return showRepository.findById(showId).orElse(null);
    }

    /* decrease Available Ticket Count for a particular show

     */
    public void updateAvailableTicketCount(Show show){
         int updatedAvailableTicket = show.getAvailableTickets()-1;
         UUID showID = show.getId();
         showRepository.updateAvailableTicketCount(showID,updatedAvailableTicket);
    }

    }

