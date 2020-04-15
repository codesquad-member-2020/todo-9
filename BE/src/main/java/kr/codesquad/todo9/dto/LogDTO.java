package kr.codesquad.todo9.dto;

import kr.codesquad.todo9.domain.Card;
import kr.codesquad.todo9.domain.Column;
import kr.codesquad.todo9.domain.User;

import java.time.LocalDateTime;

public class LogDTO {

    private Long id;
    private String action;
    private String type;
    private String beforeCardContents;
    private String afterCardContents;
    private Card beforeCard;
    private Card afterCard;
    private Column fromColumn;
    private Column toColumn;
    private LocalDateTime actionedAt;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeforeCardContents() {
        return beforeCardContents;
    }

    public void setBeforeCardContents(String beforeCardContents) {
        this.beforeCardContents = beforeCardContents;
    }

    public String getAfterCardContents() {
        return afterCardContents;
    }

    public void setAfterCardContents(String afterCardContents) {
        this.afterCardContents = afterCardContents;
    }

    public Card getBeforeCard() {
        return beforeCard;
    }

    public void setBeforeCard(Card beforeCard) {
        this.beforeCard = beforeCard;
    }

    public Card getAfterCard() {
        return afterCard;
    }

    public void setAfterCard(Card afterCard) {
        this.afterCard = afterCard;
    }

    public Column getFromColumn() {
        return fromColumn;
    }

    public void setFromColumn(Column fromColumn) {
        this.fromColumn = fromColumn;
    }

    public Column getToColumn() {
        return toColumn;
    }

    public void setToColumn(Column toColumn) {
        this.toColumn = toColumn;
    }

    public LocalDateTime getActionedAt() {
        return actionedAt;
    }

    public void setActionedAt(LocalDateTime actionedAt) {
        this.actionedAt = actionedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
