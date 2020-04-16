package kr.codesquad.todo9.todo.domain.column;

import kr.codesquad.todo9.todo.domain.card.CardDTO;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ColumnDTO {

    private @Id Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime archivedAt;
    private Boolean isArchived;
    private Integer boardKey;
    private List<CardDTO> cards = new ArrayList<>();

    @org.springframework.data.relational.core.mapping.Column("CRT_USER_ID")
    private Long createdUserId;

    @org.springframework.data.relational.core.mapping.Column("UPD_USER_ID")
    private Long updatedUserId;

    public ColumnDTO(Column column) {
        this.id = column.getId();
        this.name = column.getName();
        this.createdAt = column.getCreatedAt();
        this.updatedAt = column.getUpdatedAt();
        this.archivedAt = column.getArchivedAt();
        this.isArchived = column.getArchived();
        this.boardKey = column.getBoardKey();
        this.createdUserId = column.getCreatedUserId();
        this.updatedUserId = column.getUpdatedUserId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getBoardKey() {
        return boardKey;
    }

    public void setBoardKey(Integer boardKey) {
        this.boardKey = boardKey;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
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
}
