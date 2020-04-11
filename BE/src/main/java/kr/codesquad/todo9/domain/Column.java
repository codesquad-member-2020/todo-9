package kr.codesquad.todo9.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Column {

    private static final Logger log = LoggerFactory.getLogger(Column.class);

    @Id
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime archivedAt;
    private Boolean isArchived;
    private Integer columnOrder;

    private List<Card> cards = new ArrayList<>();

    @org.springframework.data.relational.core.mapping.Column("CRT_USER_ID")
    private Long createdUserId;

    @org.springframework.data.relational.core.mapping.Column("UPD_USER_ID")
    private Long updatedUserId;

    public Column(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addCard(String contents, User user) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(LocalDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public Integer getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(Integer columnOrder) {
        this.columnOrder = columnOrder;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    @Override
    public String toString() {
        return "Column{" + "id=" + id + ", name='" + name + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", archivedAt=" + archivedAt + ", isArchived=" + isArchived + ", columnOrder=" + columnOrder + ", cards=" + cards + ", createdUserId=" + createdUserId + ", updatedUserId=" + updatedUserId + '}';
    }
}
