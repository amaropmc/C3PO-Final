package com.codeforall.online.c3po.converters;

import com.codeforall.online.c3po.command.AnswerDto;
import com.codeforall.online.c3po.command.QuestionDto;
import com.codeforall.online.c3po.model.Question;
import com.codeforall.online.c3po.services.AnswerService;
import com.codeforall.online.c3po.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A concrete converter class that transforms an {@link Question} entity into an {@link QuestionDto}.
 */
@Component
public class QuestionToQuestionDto extends AbstractConverter<Question, QuestionDto> {

    private AnswerToAnswerDTO answerToAnswerDTO;

    /**
     * @see AbstractConverter#convert(List)
     */
    @Override
    public QuestionDto convert(Question question) {
        QuestionDto questionDto = new QuestionDto();

        List<AnswerDto> answers = question.getAnswers().stream()
                        .map(answerToAnswerDTO::convert)
                        .collect(Collectors.toList());

        questionDto.setId(question.getId());
        questionDto.setDescription(question.getDescription());
        questionDto.setScore(question.getScore());
        questionDto.setAnswers(answers);

        return questionDto;
    }

    /**
     * Set the answer to answer Data Transfer Objetct
     * @param answerToAnswerDTO the answer to answer DTO to set
     */
    @Autowired
    public void setAnswerToAnswerDTO(AnswerToAnswerDTO answerToAnswerDTO) {
        this.answerToAnswerDTO = answerToAnswerDTO;
    }
}
