package com.codeforall.online.c3po.converters;

import com.codeforall.online.c3po.command.AnswerDto;
import com.codeforall.online.c3po.model.Answer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A concrete converter class that transforms an {@link Answer} entity into an {@link AnswerDto}.
 */
@Component
public class AnswerToAnswerDTO extends AbstractConverter<Answer, AnswerDto> {

    /**
     * @see AbstractConverter#convert(List)
     */
    @Override
    public AnswerDto convert(Answer answer) {
        AnswerDto answerDto = new AnswerDto();

        answerDto.setId(answer.getId());
        answerDto.setDescription(answer.getDescription());
        answerDto.setCorrect(answer.isCorrect());

        return answerDto;
    }
}
