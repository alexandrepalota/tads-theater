package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"datetime", "room_id"}
))
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "timestamp check (datetime > now())")
    private LocalDateTime dateTime;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Room room;
    @OneToMany(mappedBy = "session")
    private List<Ticket> tickets = new ArrayList<>();

    public Session() {
    }

    public Session(LocalDateTime dateTime, Movie movie, Room room) {
        this.dateTime = dateTime;
        this.movie = movie;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", movie=" + movie.getTitle() +
                ", room=" + room.getName() +
                '}';
    }
}
