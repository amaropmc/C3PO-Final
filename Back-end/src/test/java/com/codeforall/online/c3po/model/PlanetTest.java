package com.codeforall.online.c3po.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class PlanetTest {
    //SUT
    private Planet planet;

    @Before
    public void setUp() {
        planet = new Planet();
    }

    @Test
    public void getInitialQuestionsState() {
        assertEquals(0, planet.getQuestions().size());
    }

    @Test
    @Parameters({"1, 0"})
    public void shouldCorrectlyAddAQuestion(int expectedCollectionSize, int index) {
        //Setup
        Question question = mock(Question.class);

        //Exercise
        planet.addQuestion(question);

        //Verify
        assertEquals(expectedCollectionSize, planet.getQuestions().size());
        assertEquals(question, planet.getQuestions().stream().toList().get(index));
    }

    @Test
    @Parameters({"0"})
    public void shouldCorrectlyRemoveAQuestion(int expectedCollectionSize) {
        //Setup
        Question question = mock(Question.class);
        planet.addQuestion(question);

        //Exercise
        planet.removeQuestion(question);

        //Verify
        assertEquals(expectedCollectionSize, planet.getQuestions().size());
    }
}
