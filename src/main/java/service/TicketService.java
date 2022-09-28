package service;

import dao.DAO;
import exception.BusinessException;
import model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public class TicketService {

    private DAO<Ticket> ticketDAO;

    private static TicketService instance;

    private TicketService() {
        this.ticketDAO = new DAO<>(Ticket.class);
    }

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public void save(Ticket ticket) {
        if (ticket.getSession().getDateTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Session already finish");
        }
        var existingTicket = ticketDAO.searchOne("getTicketsBySessionAndSeat", "sessionId",
                ticket.getSession().getId(), "seat", ticket.getSeat());
        var soldTickets = ticketDAO.searchMany("getTicketsBySession", "sessionId",
                ticket.getSession().getId());
        if (soldTickets.size() >= ticket.getSession().getRoom().getCapacity()) {
            throw new BusinessException("No seats available for this session");
        }
        if (existingTicket != null) {
            throw new BusinessException("Ticket already bought");
        }
        if (ticket.getId() != null) {
            ticketDAO.updateAtomic(ticket);
        } else {
            ticketDAO.includeAtomic(ticket);
        }
    }

    public Ticket findById(Long id) {
        return ticketDAO.findById(id);
    }

    public List<Ticket> findAll() {
        return ticketDAO.findAll();
    }

    public void remove(Long id) {
        ticketDAO.deleteAtomic(id);
    }

}
