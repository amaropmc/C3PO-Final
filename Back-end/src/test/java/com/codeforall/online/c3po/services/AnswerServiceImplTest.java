package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.AnswerNotFoundException;
import com.codeforall.online.c3po.model.Answer;
import com.codeforall.online.c3po.persistence.dao.AnswerDao;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class AnswerServiceImplTest {
    //SUT
    private AnswerServiceImpl answerService;

    //DOC
    private AnswerDao answerDao;

    @Before
    public void setUp() {
        answerService = spy(AnswerServiceImpl.class);

        answerDao = mock(AnswerDao.class);

        answerService.setAnswerDao(answerDao);
    }

    private Answer setFakeAnswer(Long answerId) {
        Answer fakeAnswer = new Answer();

        fakeAnswer.setId(answerId);
        fakeAnswer.setDescription("This is an answer.");
        fakeAnswer.setCorrect(true);

        return fakeAnswer;
    }

    private void assertAnswers(Answer expectedAnswer, Answer actualAnswer) {
        assertEquals(expectedAnswer.getId(), actualAnswer.getId());
        assertEquals(expectedAnswer.getDescription(), actualAnswer.getDescription());
        assertEquals(expectedAnswer.isCorrect(), actualAnswer.isCorrect());
    }

    @Test
    @Parameters({"1"})
    public void testGetAnswerById(Long answerId) throws AnswerNotFoundException {
        Answer fakeAnswer = setFakeAnswer(answerId);
        when(answerDao.findById(answerId)).thenReturn(fakeAnswer);

        Answer resultAnswer = answerService.getAnswerById(answerId);

        assertAnswers(fakeAnswer, resultAnswer);
    }
}
