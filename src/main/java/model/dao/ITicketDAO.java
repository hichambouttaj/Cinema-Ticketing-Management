package model.dao;

import model.entity.Ticket;

import java.util.List;

public interface ITicketDAO extends IDAO<Ticket> {
    List<Integer> getAll(int showId);
    int count(int showId);
}
