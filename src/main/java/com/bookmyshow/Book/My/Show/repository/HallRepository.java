package com.bookmyshow.Book.My.Show.repository;

import com.bookmyshow.Book.My.Show.models.Hall;
import com.bookmyshow.Book.My.Show.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface HallRepository extends JpaRepository<Hall, UUID> {

}
