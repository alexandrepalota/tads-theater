package service;

import dao.DAO;
import exception.BusinessException;
import exception.NotFoundException;
import model.Room;

import javax.persistence.RollbackException;
import java.util.List;

public class RoomService {

    private DAO<Room> roomDAO;

    private static RoomService instance;

    private RoomService() {
        this.roomDAO = new DAO<>(Room.class);
    }

    public static RoomService getInstance() {
        if (instance == null) {
            instance = new RoomService();
        }
        return instance;
    }

    public void save(Room room) {
        if (room.getId() != null) {
            roomDAO.updateAtomic(room);
        } else {
            roomDAO.includeAtomic(room);
        }
    }

    public Room findById(Long id) {
        return roomDAO.findById(id);
    }

    public List<Room> findAll() {
        return roomDAO.findAll();
    }

    public void remove(Long id) {
        try {
            roomDAO.deleteAtomic(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (RollbackException e) {
            throw new BusinessException("Entidade possui vínculos");
        }
    }

}
