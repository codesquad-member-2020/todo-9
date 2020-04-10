package kr.codesquad.todo9.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

public class Card {

    @Id
    private Long id;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime archivedAt;
    private Integer cardOrder;
    private Boolean isArchived;
    private Long columnKey;

    @Column("CRT_USER_ID")
    private Long createdUserId;

    @Column("UPD_USER_ID")
    private Long updatedUserId;

    public Card() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isArchived = false;
    }

    public Card(Long id, String contents, User user) {
        this.id = id;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isArchived = false;
        this.createdUserId = user.getId();
        this.updatedUserId = user.getId();
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public Integer getCardOrder() {
        return cardOrder;
    }

    public void setCardOrder(Integer cardOrder) {
        this.cardOrder = cardOrder;
    }

    public Long getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(Long columnKey) {
        this.columnKey = columnKey;
    }

    @Override
    public String toString() {
        return "Card{" + "id=" + id + ", contents='" + contents + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", archivedAt=" + archivedAt + ", cardOrder=" + cardOrder + ", isArchived=" + isArchived + ", columnKey=" + columnKey + ", createdUserId=" + createdUserId + ", updatedUserId=" + updatedUserId + '}';
    }
}
