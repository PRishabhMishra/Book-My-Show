package com.bookmyshow.Book.My.Show.controller;

import com.bookmyshow.Book.My.Show.models.Show;
import com.bookmyshow.Book.My.Show.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    ShowService showService;

    @GetMapping("/search")
    public ResponseEntity searchShowByMovieId(@RequestParam(required = false) UUID movieId, @RequestParam(required = false) UUID hallId) {
        if (movieId != null && hallId != null) {
            //search for both movieId and hallId
            List<Show>shows = showService.getShowsByHallIdAndMovieId(hallId,movieId);
            return new ResponseEntity(shows,HttpStatus.OK);

        } else if (movieId == null && hallId != null) {
            //search for only hallId
            List<Show>shows = showService.getShowsByHallId(hallId);
            return new ResponseEntity(shows,HttpStatus.OK);

        } else if (movieId != null && hallId == null) {
            //search for only movieId
            List<Show>shows = showService.getShowsByMovieId(movieId);
            return new ResponseEntity(shows,HttpStatus.OK);

        }else{
            return new ResponseEntity("Please pass atleast one param",HttpStatus.OK);
        }

    }
}