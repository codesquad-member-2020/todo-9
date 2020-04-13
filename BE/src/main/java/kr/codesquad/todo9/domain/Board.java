package kr.codesquad.todo9.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final Logger log = LoggerFactory.getLogger(Board.class);

    private @Id Long id;
    private String name;
    private List<Column> columns = new ArrayList<>();
    private List<Log> logs = new ArrayList<>();

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addCard(int boardKey, String contents, User user) {
        List<Card> cards = columns.get(boardKey).getCards();
        cards.add(createCard(contents, user));
    }

    public void addLog(String action, String type, User user, String contents, int boardKey) {
        logs.add(createLog(action, type, user, contents, boardKey));
    }

    public void addLog(String action, String type, User user, String contents, int boardKey, int columnKey) {
        logs.add(createLog(action, type, user, contents, boardKey, columnKey));
    }

    public void updateCard(int boardKey, int columnKey, String contents, User user) {
        Card card = this.columns.get(boardKey).getCards().get(columnKey);
        this.addLog("edit", "card", user, contents, boardKey, columnKey);
        card.setContents(contents);
        card.setUpdatedAt(LocalDateTime.now());
        card.setUpdatedUserId(user.getId());
    }

    public Log getLastLog() {
        return this.logs.get(this.logs.size() - 1);
    }

    private Log createLog(String action, String type, User user, String contents, int boardKey) {
        List<Card> cards = this.columns.get(boardKey).getCards();
        return createLog(action,
                type,
                null,
                contents,
                null,
                cards.get(cards.size() - 1).getId(),
                (long) boardKey,
                (long) boardKey,
                user);
    }

    private Log createLog(String action, String type, User user, String contents, int boardKey, int columnKey) {
        Card card = this.columns.get(boardKey).getCards().get(columnKey);
        return createLog(action,
                type,
                card.getContents(),
                contents,
                card.getId(),
                card.getId(),
                (long) boardKey,
                (long) boardKey,
                user);
    }

    private Log createLog(String action,
                          String type,
                          String beforeCardContents,
                          String afterCardContents,
                          Long beforeCardId,
                          Long afterCardId,
                          Long fromColumnId,
                          Long toColumnId,
                          User user) {
        return new Log(action,
                type,
                beforeCardContents,
                afterCardContents,
                beforeCardId,
                afterCardId,
                fromColumnId,
                toColumnId,
                LocalDateTime.now(),
                user.getId());
    }

    private Card createCard(String contents, User user) {
        Card card = new Card();
        card.setContents(contents);
        card.setCreatedUserId(user.getId());
        card.setUpdatedUserId(user.getId());
        log.debug("new Card: {}", card);

        return card;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Board{" + "id=" + id + ", name='" + name + '\'' + ", columns=" + columns + ", logs=" + logs + '}';
    }

}
