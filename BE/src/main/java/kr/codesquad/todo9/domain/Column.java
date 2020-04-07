package kr.codesquad.todo9.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class Column {
    private Long id;
    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime archivedAt;
    private Boolean isArchived;
    private Integer order;
    private List<Card> cards;

    public Column(long id, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.isArchived = false;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ZonedDateTime getArchivedAt() {
        return archivedAt;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public Integer getOrder() {
        return order;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
