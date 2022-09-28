import exception.BusinessException;
import exception.NotFoundException;
import model.Movie;
import model.Room;
import model.Session;
import model.Ticket;
import service.MovieService;
import service.RoomService;
import service.SessionService;
import service.TicketService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        RoomService roomService = RoomService.getInstance();

        // ************* CRUD de salas *****************************
        System.out.println("********** CRUD DE SALAS **************");
        // inserindo algumas salas
        Room room1 = new Room("Sala 1", 8);
        Room room2 = new Room("Sala 2", 10);
        Room room3 = new Room("Sala 3", 7);
        roomService.save(room1);
        roomService.save(room2);
        roomService.save(room3);

        // Listando as salas cadastradas
        System.out.println("LISTANDO TODAS AS SALAS");
        roomService.findAll().forEach(System.out::println);

        // Buscando sala por ID
        System.out.println("\nBUSCANDO SALA POR ID EXISTENTE");
        System.out.println(roomService.findById(1L));

        // Buscando sala por ID
        System.out.println("\nBUSCANDO SALA POR ID INEXISTENTE");
        try {
            roomService.findById(1111L);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Atualizando sala
        room1.setName("Sala 1 Editada");
        room1.setCapacity(10);
        roomService.save(room1);

        // Mostrando sala atualizada
        System.out.println("\nMOSTRANDO SALA EDITADA");
        System.out.println(roomService.findById(1L));

        // Excluindo Sala sem dependências
        roomService.remove(3L);

        // Listando salas após exclusão da sala com ID=3
        System.out.println("\nLISTANDO TODAS AS SALAS APÓS EXCLUSÃO DA ID=3");
        roomService.findAll().forEach(System.out::println);

        // Tentando excluir uma sala que já foi excluída
        System.out.println("\nEXLUINDO SALA INEXISTENTE");
        try {
            roomService.remove(3L);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        // ********************* FILMES ************************
        System.out.println("\n\n********** CRUD DE FILMES **************");

        MovieService movieService = MovieService.getInstance();
        // inserindo novos filmes
        Movie movie1 = new Movie("Pulp Fiction", "Ação", "bla bla bla, bla bla bla, bla");
        Movie movie2 = new Movie("Bastardos Inglórios", "Ação", "bla bla bla, bla bla bla, bla");
        Movie movie3 = new Movie("Kill Bill", "Ação", "bla bla bla, bla bla bla, bla");
        movieService.save(movie1);
        movieService.save(movie2);
        movieService.save(movie3);

        // Listando os filmes cadastradas
        System.out.println("LISTANDO TODAS OS FILMES");
        movieService.findAll().forEach(System.out::println);

        // Buscando filme por ID
        System.out.println("\nBUSCANDO FILME POR ID EXISTENTE");
        System.out.println(movieService.findById(1L));

        // Buscando filme por ID
        System.out.println("\nBUSCANDO FILME POR ID INEXISTENTE");
        try {
            movieService.findById(1111L);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Atualizando filme
        movie3.setTitle("Kill Bill - part II");
        movie3.setGenre("Mais ação");
        movie3.setSynopsis("Mais bla bla bla, bla bla bla, bla");
        movieService.save(movie3);

        // Mostrando filme atualizado
        System.out.println("\nMOSTRANDO FILME EDITADO");
        System.out.println(movieService.findById(3L));

        // Excluindo Filme sem dependências
        movieService.remove(3L);

        // Listando filmes após exclusão do filme com ID=3
        System.out.println("\nLISTANDO TODOS OS FILMES APÓS EXCLUSÃO DO ID=3");
        movieService.findAll().forEach(System.out::println);

        // Tentando excluir um filme que já foi excluído
        System.out.println("\nEXLUINDO FILME INEXISTENTE");
        try {
            movieService.remove(3L);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        // ********************* SESSOES ************************
        System.out.println("\n\n********** CRUD DE SESSOES **************");

        SessionService sessionService = SessionService.getInstance();
        // inserindo novas sessões
        // Mesmo filme, mesma sala, dataHora diferente
        Session session1 = new Session(LocalDateTime.of(2023, Month.SEPTEMBER, 20, 18, 30), movie1, room1);
        Session session2 = new Session(LocalDateTime.of(2023, Month.SEPTEMBER, 21, 19, 15), movie1, room1);
        // Mesmo filme, mesma dataHora, salas diferentes
        Session session3 = new Session(LocalDateTime.of(2023, Month.SEPTEMBER, 21, 19, 15), movie1, room2);
        // Filme, sala e dataHora diferente
        Session session4 = new Session(LocalDateTime.of(2023, Month.SEPTEMBER, 22, 19, 15), movie2, room2);
        sessionService.save(session1);
        sessionService.save(session2);
        sessionService.save(session3);
        sessionService.save(session4);

        // Listando as sessões cadastradas
        System.out.println("LISTANDO TODAS AS SESSÕES");
        sessionService.findAll().forEach(System.out::println);

        // Buscando sessão por ID
        System.out.println("\nBUSCANDO SESSÃO POR ID EXISTENTE");
        System.out.println(sessionService.findById(1L));

        // Buscando sessão por ID
        System.out.println("\nBUSCANDO SESSÃO POR ID INEXISTENTE");
        try {
            sessionService.findById(1111L);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Atualizando sessão
        session1.setMovie(movie1);
        session1.setDateTime(LocalDateTime.of(2033, Month.JANUARY, 30, 18, 0));
        sessionService.save(session1);

        // Mostrando sessão atualizada
        System.out.println("\nMOSTRANDO SESSÃO EDITADA");
        System.out.println(sessionService.findById(1L));

        // Excluindo Sessão sem dependências
        sessionService.remove(4L);

        // Listando sessões após exclusão da sessão com ID=3
        System.out.println("\nLISTANDO TODAS AS SESSÕES APÓS EXCLUSÃO DO ID=4");
        sessionService.findAll().forEach(System.out::println);

        // Tentando excluir uma sessão que já foi excluída
        System.out.println("\nEXLUINDO SESSÃO INEXISTENTE");
        try {
            sessionService.remove(4L);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Tentando inserir sessão com data inválida
        System.out.println("\nINSERINDO SESSÃO COM DATA NO PASSADO");
        Session session5 = new Session(LocalDateTime.of(2020, Month.SEPTEMBER, 20, 18, 30), movie1, room1);
        try {
            sessionService.save(session5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Listando sessões novamente
        System.out.println("\nLISTANDO TODAS AS SESSÕES PARA GARANTIR QUE NÃO INSERIU COM DATA INVÁLIDA");
        sessionService.findAll().forEach(System.out::println);

        // *************** INGRESSOS ***********************
        System.out.println("\n\n************* INGRESSOS ********************");
        // Inserindo ingressos
        TicketService ticketService = TicketService.getInstance();
        Ticket ticket1 = new Ticket(session1, 1, "John");
        Ticket ticket2 = new Ticket(session1, 2, "Paul");
        Ticket ticket3 = new Ticket(session1, 3, "George");
        ticketService.save(ticket1);
        ticketService.save(ticket2);
        ticketService.save(ticket3);

        // Listando ingressos
        System.out.println("\nLISTA DE INGRESSOS CADASTRADOS");
        ticketService.findAll().forEach(System.out::println);

        // Buscando ingresso por id
        System.out.println("\nBUSCANDO INGRESSO POR ID");
        System.out.println(ticketService.findById(1L));

        //ATUALIZANDO INGRESSO
        System.out.println("\nATUALIZANDO INGRESSO");
        ticket2.setSeat(4);
        ticket2.setCustomerName("Ringo");
        ticketService.save(ticket2);

        // EXIBINDO INGRESSO ATUALIZADO
        System.out.println("\nEXIBINDO INGRESSO ATUALIZADO");
        System.out.println(ticketService.findById(2L));

        // REMOVENDO UM INGRESSO
        System.out.println("\nREMOVENDO INGRESSO ID=3");
        ticketService.remove(2L);

        // LISTANDO INGRESSOS APÓS REMOÇÃO
        System.out.println("\nLISTANDO INGRESSOS APÓS REMOÇÃO");
        ticketService.findAll().forEach(System.out::println);

        // Tentando inserir um ingresso para um lugar já ocupado em uma sessão
        System.out.println("\nTENTANDO INSERIR UM INGRESSO PARA UM LUGAR JÁ COMPRADO EM UMA SESSÃO");
        try {
            Ticket ticket = new Ticket(session1, 1, "Yoko");
            ticketService.save(ticket);
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
        }

        // Ocupando todos os lugares
        for (int i = 1; i <= session1.getRoom().getCapacity(); i++) {
            if (i != 1 && i != 3) {
                ticketService.save(new Ticket(session1, i, "NONONONO"));
            }
        }

        // Tentando vender ingresso para sessão lotada
        System.out.println("\nTENTANDO COMPRAR INGRESSO PARA SESSÃO LOTADA");
        try {
            ticketService.save(new Ticket(session1, 1, "Late customer"));
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
        }

        // Mostrando lugares ocupados na sessão
        System.out.println("\nMOSTRANDO LUGARES OCUPADOS EM UMA SESSÃO (ID=1)");
        var sessao = sessionService.findById(1L);
        sessao.getTickets().forEach(t -> {
            System.out.print(" [" + t.getSeat() + "] ");
        });
        System.out.println();

        // Mostrando lugares disponíveis disponíveis na sessão
        System.out.println("\nMOSTRANDO LUGARES DISPONÍVEIS NA SESSÃO (ID=2)");
        var t1 = new Ticket(session2, 2, "Maria");
        var t2 = new Ticket(session2, 4, "João");
        ticketService.save(t1);
        ticketService.save(t2);
        sessao = sessionService.findById(2L);
        List<Integer> ocupados = sessao.getTickets().stream().map(x -> x.getSeat()).collect(Collectors.toList());
        for (int i = 0; i < sessao.getRoom().getCapacity(); i++) {
            if (!ocupados.contains(Integer.valueOf(i + 1))) {
                System.out.print(" [" + (i + 1) + "] ");
            }
        }
    }
}
