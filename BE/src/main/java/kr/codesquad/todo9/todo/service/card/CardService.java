package kr.codesquad.todo9.todo.service.card;

import kr.codesquad.todo9.todo.domain.card.Card;
import kr.codesquad.todo9.todo.domain.log.LogDTO;
import kr.codesquad.todo9.todo.domain.user.User;
import kr.codesquad.todo9.todo.requestobject.ContentsObject;
import kr.codesquad.todo9.todo.requestobject.MoveCardObject;

import java.util.List;

public interface CardService {

    List<Card> getCardList(Long boardId, int boardKey);

    LogDTO addCard(Long boardId, int boardKey, User user, ContentsObject contentsObject);

    LogDTO editCard(Long boardId, int boardKey, int columnKey, User user, ContentsObject contentsObject);

    LogDTO deleteCard(Long boardId, int boardKey, int columnKey, User user);

    LogDTO moveCard(Long boardId, int boardKey, int columnKey, User user, MoveCardObject moveCardObject);
}
