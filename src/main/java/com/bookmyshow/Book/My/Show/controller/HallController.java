package com.bookmyshow.Book.My.Show.controller;

import com.bookmyshow.Book.My.Show.exceptions.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.exceptions.UnAuthorized;
import com.bookmyshow.Book.My.Show.exceptions.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.models.ApplicationUser;
import com.bookmyshow.Book.My.Show.models.Show;
import com.bookmyshow.Book.My.Show.models.dto.request.AddScreenDTO;
import com.bookmyshow.Book.My.Show.models.dto.request.AddShowDTO;
import com.bookmyshow.Book.My.Show.models.dto.request.HallOwnerSignUpDTO;
import com.bookmyshow.Book.My.Show.models.dto.response.GeneralMessageDTO;
import com.bookmyshow.Book.My.Show.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hall")
public class HallController {
    @Autowired
    HallService hallService;
    @PostMapping("/signUp")
    public ResponseEntity signUp(HallOwnerSignUpDTO hallOwnerSignUpDTO){
        ApplicationUser user = hallService.signUp(hallOwnerSignUpDTO);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PostMapping("/addscreen")
    public ResponseEntity addScreen(@RequestBody AddScreenDTO addScreenDTO, @RequestParam String email){
        try{
            hallService.addScreens(addScreenDTO,email);
        }catch(UserDoesNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);//404
        }
        catch(UnAuthorized e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.UNAUTHORIZED);//401
        }
        catch(ResourceNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);//404
        }

        return new ResponseEntity(new GeneralMessageDTO("Screen added successfully"),HttpStatus.CREATED);//201
    }

    @PostMapping("/addshow")
    public ResponseEntity addShow(@RequestBody AddShowDTO addShowDTO,@RequestParam String email){
        try{
            Show show = hallService.createShows(addShowDTO,email);
            return new ResponseEntity(show,HttpStatus.CREATED);

        }catch(UserDoesNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);//404
        }
        catch(UnAuthorized e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.UNAUTHORIZED);//401
        }
        catch(ResourceNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()),HttpStatus.NOT_FOUND);//404
        }

    }
}