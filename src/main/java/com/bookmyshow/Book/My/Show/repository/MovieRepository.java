package com.bookmyshow.Book.My.Show.repository;

import com.bookmyshow.Book.My.Show.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
