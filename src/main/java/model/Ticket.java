package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"session_id", "seat"}
))
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Session session;

    @Column(columnDefinition = "integer check(seat > 0)")
    private Integer seat;

    @Column(nullable = false, length = 100)
    private String customerName;

    public Ticket() {
    }

    public Ticket(Session session, Integer seat, String customerName) {
        this.session = session;
        this.seat = seat;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", session=" + session +
                ", seat=" + seat +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
