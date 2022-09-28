package service;

import dao.DAO;
import exception.BusinessException;
import exception.NotFoundException;
import model.Movie;

import javax.persistence.RollbackException;
import java.util.List;

public class MovieService {

    private DAO<Movie> movieDAO;

    private static MovieService instance;

    private MovieService() {
        this.movieDAO = new DAO<>(Movie.class);
    }

    public static MovieService getInstance() {
        if (instance == null) {
            instance = new MovieService();
        }
        return instance;
    }

    public void save(Movie movie) {
        if (movie.getId() != null) {
            movieDAO.updateAtomic(movie);
        } else {
            movieDAO.includeAtomic(movie);
        }
    }

    public Movie findById(Long id) {
        return movieDAO.findById(id);
    }

    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    public void remove(Long id) {
        try {
            movieDAO.deleteAtomic(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (RollbackException e) {
            throw new BusinessException("Entidade possui vínculos");
        }
    }

}
