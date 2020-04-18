package kr.codesquad.todo9.todo.service.card;

import kr.codesquad.todo9.common.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.todo.domain.board.Board;
import kr.codesquad.todo9.todo.domain.board.BoardRepository;
import kr.codesquad.todo9.todo.domain.card.Card;
import kr.codesquad.todo9.todo.domain.log.LogDTO;
import kr.codesquad.todo9.todo.domain.log.LogRepository;
import kr.codesquad.todo9.todo.domain.user.User;
import kr.codesquad.todo9.todo.requestobject.ContentsObject;
import kr.codesquad.todo9.todo.requestobject.MoveCardObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    private final BoardRepository boardRepository;
    private final LogRepository logRepository;

    public CardServiceImpl(BoardRepository boardRepository, LogRepository logRepository) {
        this.boardRepository = boardRepository;
        this.logRepository = logRepository;
    }

    @Override
    public List<Card> getCardList(Long boardId, int boardKey) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard();
        log.debug("board: {}", board);
        return board.getColumns().get(boardKey).getCards();
    }

    @Override
    @Transactional
    public LogDTO addCard(Long boardId, int boardKey, User user, ContentsObject contentsObject) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        String contents = contentsObject.getContents();

        board.addCard(boardKey, contents, user);
        boardRepository.save(board);
        log.debug("save after board: {}", board);

        board.addLog("create", "card", user, contents, boardKey);
        board = boardRepository.save(board);
        log.debug("save log after board: {}", board);
        return logRepository.getLogDTO(board.getId(), board.getLastLog().getId());
    }

    @Override
    public LogDTO editCard(Long boardId, int boardKey, int columnKey, User user, ContentsObject contentsObject) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        board.updateCard(boardKey, columnKey, contentsObject.getContents(), user);
        board = boardRepository.save(board);
        log.debug("save after board: {}", board);
        return logRepository.getLogDTO(board.getId(), board.getLastLog().getId());
    }

    @Override
    public LogDTO deleteCard(Long boardId, int boardKey, int columnKey, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        board.deleteCard(boardKey, columnKey, user);
        board = boardRepository.save(board);
        log.debug("save after board: {}", board);
        return logRepository.getLogDTO(board.getId(), board.getLastLog().getId());
    }

    @Override
    public LogDTO moveCard(Long boardId, int boardKey, int columnKey, User user, MoveCardObject moveCardObject) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        board.moveCard(boardKey, columnKey, user, moveCardObject);
        board = boardRepository.save(board);
        log.debug("save after board: {}", board);
        return logRepository.getLogDTO(board.getId(), board.getLastLog().getId());
    }
}
