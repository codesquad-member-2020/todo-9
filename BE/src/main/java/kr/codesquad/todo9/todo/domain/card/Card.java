package kr.codesquad.todo9.todo.domain.card;

import kr.codesquad.todo9.todo.domain.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.Objects;

public class Card implements Comparable<Card> {

    private @Id Long id;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime archivedAt;
    private Integer columnKey;
    private Boolean isArchived;
    private Long column;

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

    public Integer getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(Integer columnKey) {
        this.columnKey = columnKey;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public Long getColumn() {
        return column;
    }

    public void setColumn(Long column) {
        this.column = column;
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
        return "Card{" + "id=" + id + ", contents='" + contents + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", archivedAt=" + archivedAt + ", columnKey=" + columnKey + ", isArchived=" + isArchived + ", columnId=" + column + ", createdUserId=" + createdUserId + ", updatedUserId=" + updatedUserId + '}';
    }

    @Override
    public int compareTo(Card o) {
        if (o == null || o.columnKey == null) {
            return 0;
        }
        return o.columnKey - this.columnKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Card)) { return false; }
        Card card = (Card) o;
        return getCreatedAt().equals(card.getCreatedAt()) && getUpdatedAt().equals(card.getUpdatedAt()) && Objects.equals(
                getArchivedAt(),
                card.getArchivedAt()) && isArchived.equals(card.isArchived) && getCreatedUserId().equals(card.getCreatedUserId()) && getUpdatedUserId()
                .equals(card.getUpdatedUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreatedAt(),
                getUpdatedAt(),
                getArchivedAt(),
                isArchived,
                getCreatedUserId(),
                getUpdatedUserId());
    }
}
