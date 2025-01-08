package com.codeforall.online.c3po.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class QuestionTest {
    //SUT
    private Question question;

    @Before
    public void setUp() {
        question = new Question();
    }

    @Test
    @Parameters({"0"})
    public void getInitialAnswersState(int expectedCollectionSize) {
        assertEquals(expectedCollectionSize, question.getAnswers().size());
    }

    @Test
    @Parameters({"1, 0"})
    public void shouldCorrectlyAddAnswerToCollection(int expectedCollectionSize, int index) {
        //Setup
        Answer answer = mock(Answer.class);

        //Exercise
        question.addAnswer(answer);

        //Verify
        assertEquals(expectedCollectionSize, question.getAnswers().size());
        assertEquals(answer, question.getAnswers().stream().toList().get(index));
    }

    @Test
    @Parameters({"0"})
    public void shouldCorrectlyRemoveAnswerFromCollection(int expectedCollectionSize) {
        //Setup
        Answer answer = mock(Answer.class);
        question.addAnswer(answer);

        //Exercise
        question.removeAnswer(answer);

        //Verify
        assertEquals(expectedCollectionSize, question.getAnswers().size());
    }


}
