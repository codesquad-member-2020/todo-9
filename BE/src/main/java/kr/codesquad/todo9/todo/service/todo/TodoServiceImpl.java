package kr.codesquad.todo9.todo.service.todo;

import kr.codesquad.todo9.common.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.todo.domain.board.Board;
import kr.codesquad.todo9.todo.domain.board.BoardDTO;
import kr.codesquad.todo9.todo.domain.board.BoardRepository;
import kr.codesquad.todo9.todo.domain.card.CardDTO;
import kr.codesquad.todo9.todo.domain.card.CardRepository;
import kr.codesquad.todo9.todo.domain.column.Column;
import kr.codesquad.todo9.todo.domain.column.ColumnDTO;
import kr.codesquad.todo9.todo.domain.log.LogDTO;
import kr.codesquad.todo9.todo.domain.log.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private static final Logger log = LoggerFactory.getLogger(TodoServiceImpl.class);

    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;
    private final LogRepository logRepository;

    public TodoServiceImpl(BoardRepository boardRepository, CardRepository cardRepository, LogRepository logRepository) {
        this.boardRepository = boardRepository;
        this.cardRepository = cardRepository;
        this.logRepository = logRepository;
    }

    @Override
    public List<Column> getColumnsOfBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard();
        log.debug("board: {}", board);
        return board.getColumns();
    }

    @Override
    public BoardDTO getSortedBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard();
        List<ColumnDTO> columns = new ArrayList<>();
        for (Column column : board.getColumns()) {
            ColumnDTO columnDTO = new ColumnDTO(column);
            List<CardDTO> cardList = new ArrayList<>();
            for (CardDTO cardDTO : cardRepository.getCardList(columnDTO.getId())) {
                if (!cardDTO.getArchived()) {
                    cardList.add(cardDTO);
                }
            }
            columnDTO.setCards(cardList);
            columns.add(columnDTO);
        }
        List<LogDTO> logs = logRepository.getLogDTOList(boardId);
        BoardDTO boardDTO = new BoardDTO(board, columns, logs);
        log.debug("boardDTO: {}", boardDTO);
        return boardDTO;
    }
}
