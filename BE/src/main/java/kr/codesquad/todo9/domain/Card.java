package kr.codesquad.todo9.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Card {
    private Long id;
    private String contents;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime archivedAt;
    private Boolean isArchived;
    private Long beforeCardId;
    private Long afterCardId;
    private Long createdUserId;
    private Long updatedUserId;

    public Card(long id, String contents, Long userId) {
        this.id = id;
        this.contents = contents;
        this.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.isArchived = false;
        this.createdUserId = userId;
        this.updatedUserId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBeforeCardId(Long beforeCardId) {
        this.beforeCardId = beforeCardId;
    }

    public void setAfterCardId(Long afterCardId) {
        this.afterCardId = afterCardId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public void setUpdatedUserId(Long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
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

    public Long getBeforeCardId() {
        return beforeCardId;
    }

    public Long getAfterCardId() {
        return afterCardId;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public Long getUpdatedUserId() {
        return updatedUserId;
    }
}
