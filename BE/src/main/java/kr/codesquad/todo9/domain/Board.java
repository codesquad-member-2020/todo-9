package kr.codesquad.todo9.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;

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

    public void addCard(int columnIndex, String contents, User user) {
        List<Card> cards = columns.get(columnIndex).getCards();
        cards.add(createCard(contents, user));
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
