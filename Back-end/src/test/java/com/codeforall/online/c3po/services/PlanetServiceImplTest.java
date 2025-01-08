package com.codeforall.online.c3po.services;

import com.codeforall.online.c3po.exceptions.PlanetNotFoundException;
import com.codeforall.online.c3po.exceptions.QuestionNotFoundException;
import com.codeforall.online.c3po.model.Planet;
import com.codeforall.online.c3po.model.Question;
import com.codeforall.online.c3po.persistence.dao.PlanetDao;
import com.codeforall.online.c3po.persistence.dao.QuestionDao;
import com.codeforall.online.c3po.persistence.managers.TransactionManager;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class PlanetServiceImplTest {
    // SUT
    private PlanetServiceImpl planetServiceImpl;

    // DOC
    private TransactionManager transactionManager;
    private PlanetDao planetDao;
    private QuestionDao questionDao;

    @Before
    public void setUp() {
        planetServiceImpl = spy(PlanetServiceImpl.class);

        transactionManager = mock(TransactionManager.class);
        planetDao = mock(PlanetDao.class);
        questionDao = mock(QuestionDao.class);

        this.planetServiceImpl.setTransactionManager(transactionManager);
        this.planetServiceImpl.setPlanetDao(planetDao);
        this.planetServiceImpl.setQuestionDao(questionDao);
    }

    public Planet setFakePlanet(Long id) {
        Planet fakePlanet = new Planet();

        fakePlanet.setId(id);
        fakePlanet.setName("Fake");

        return fakePlanet;
    }

    public Question setFakeQuestion(Long id) {
        Question fakeQuestion = new Question();

        fakeQuestion.setId(id);
        fakeQuestion.setDescription("One plus one equals?");
        fakeQuestion.setScore(1);

        return fakeQuestion;
    }

    public void assertPlanets(Planet fakePlanet, Planet actualPlanet) {
        assertEquals(fakePlanet.getId(), actualPlanet.getId());
        assertEquals(fakePlanet.getName(), actualPlanet.getName());
    }

    @Test
    @Parameters({"1"})
    public void testGetPlanet(Long planetId) throws PlanetNotFoundException {
        Planet fakePlanet = setFakePlanet(planetId);
        when(planetDao.findById(planetId)).thenReturn(fakePlanet);

        Planet actualPlanet = planetServiceImpl.getPlanetById(planetId);

        assertPlanets(fakePlanet, actualPlanet);
    }

    @Test
    @Parameters({"1"})
    public void throwPlanetNotFoundExceptionOnGetPlanetById(Long planetId) throws PlanetNotFoundException {
        when(planetDao.findById(planetId)).thenReturn(null);

        assertThrows(PlanetNotFoundException.class, () -> {
            planetServiceImpl.getPlanetById(planetId);
        });
    }

    @Test
    @Parameters({"1, Fake"})
    public void testGetPlanetByName(Long planetId, String planetName) throws PlanetNotFoundException {
        Planet fakePlanet = setFakePlanet(planetId);
        when(planetDao.findByName(planetName)).thenReturn(fakePlanet);

        Planet actualPlanet = planetServiceImpl.getPlanetByName(planetName);

        assertPlanets(fakePlanet, actualPlanet);
    }

    @Test
    @Parameters({"1, Fake"})
    public void throwPlanetNotFoundExceptionOnGetPlanetByName(Long id, String planetName) throws PlanetNotFoundException {
        when(planetDao.findByName(planetName)).thenReturn(null);

        assertThrows(PlanetNotFoundException.class, () -> {
            planetServiceImpl.getPlanetByName(planetName);
        });
    }

    @Test
    @Parameters({"1, 2"})
    public void testGetAllPlanets(Long planetId1, Long planetId2) throws PlanetNotFoundException {
        Planet fakePlanet1 = setFakePlanet(planetId1);
        Planet fakePlanet2 = setFakePlanet(planetId2);
        List<Planet> fakePlanets = Arrays.asList(fakePlanet1, fakePlanet2);

        when(planetDao.findAll()).thenReturn(fakePlanets);

        List<Planet> actualPlanets = planetServiceImpl.list();

        assertEquals(actualPlanets.get(0), fakePlanet1);
        assertEquals(actualPlanets.get(1), fakePlanet2);
        assertEquals(actualPlanets.size(), fakePlanets.size());
    }

    @Test
    @Parameters({"1"})
    public void testAddPlanet(Long planetId) {
        Planet fakePlanet = setFakePlanet(planetId);
        when(planetDao.saveOrUpdate(fakePlanet)).thenReturn(fakePlanet);

        Planet savedPlanet = planetServiceImpl.add(fakePlanet);

        assertPlanets(fakePlanet, savedPlanet);

        verify(transactionManager).beginWrite();
        verify(planetDao).saveOrUpdate(fakePlanet);
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    public void throwsIllegalArgumentExceptionOnAddPlanet() {
        assertThrows(IllegalArgumentException.class, () -> {
            planetServiceImpl.add(null);
        });

        verify(transactionManager, never()).beginWrite();
        verify(planetDao, never()).saveOrUpdate(any());
        verify(transactionManager, never()).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1"})
    public void testRemovePlanet(Long planetId) throws PlanetNotFoundException {
        Planet fakePlanet = setFakePlanet(planetId);
        when(planetDao.findById(planetId)).thenReturn(fakePlanet);

        planetServiceImpl.remove(planetId);

        verify(planetDao).findById(planetId);
        verify(transactionManager).beginWrite();
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    public void throwsPlanetNotFoundExceptionOnRemovePlanet() {
        assertThrows(PlanetNotFoundException.class, () -> {
            planetServiceImpl.remove(anyLong());
        });

        verify(planetDao).findById(anyLong());
        verify(transactionManager, never()).beginWrite();
        verify(transactionManager, never()).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, 1"})
    public void testAddQuestion(Long planetId, Long questionId) throws PlanetNotFoundException {
        Planet fakePlanet = setFakePlanet(planetId);
        Question fakeQuestion = setFakeQuestion(questionId);

        when(planetDao.findById(planetId)).thenReturn(fakePlanet);
        when(planetDao.saveOrUpdate(fakePlanet)).thenReturn(fakePlanet);
        when(questionDao.saveOrUpdate(any(Question.class))).thenReturn(fakeQuestion);

        Question savedQuestion = planetServiceImpl.addQuestion(planetId, fakeQuestion);

        assertEquals(savedQuestion, fakeQuestion);
        verify(transactionManager).beginWrite();
        verify(planetDao).saveOrUpdate(fakePlanet);
        verify(questionDao).saveOrUpdate(fakeQuestion);
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, 1"})
    public void throwsPlanetNotFoundExceptionOnAddQuestion(Long planetId, Long questionId) {
        when(planetDao.findById(planetId)).thenReturn(null);
        Question fakeQuestion = setFakeQuestion(questionId);

        assertThrows(PlanetNotFoundException.class, () -> {
            planetServiceImpl.addQuestion(planetId, fakeQuestion);
        });

        verify(planetDao).findById(planetId);
        verify(transactionManager, never()).beginWrite();
        verify(planetDao, never()).saveOrUpdate(any());
        verify(questionDao, never()).saveOrUpdate(any(Question.class));
        verify(transactionManager, never()).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, 1"})
    public void testRemoveQuestion(Long planetId, Long questionId) throws PlanetNotFoundException, QuestionNotFoundException {
        Planet fakePlanet = setFakePlanet(planetId);
        Question fakeQuestion = setFakeQuestion(questionId);

        Set<Question> planetFakeQuestions = new HashSet<>();
        planetFakeQuestions.add(fakeQuestion);
        fakePlanet.setQuestions(planetFakeQuestions);

        when(planetDao.findById(planetId)).thenReturn(fakePlanet);
        when(questionDao.findById(questionId)).thenReturn(fakeQuestion);

        planetServiceImpl.removeQuestion(planetId, questionId);

        verify(transactionManager).beginWrite();
        verify(planetDao).findById(planetId);
        verify(questionDao).findById(questionId);
        verify(planetDao).saveOrUpdate(fakePlanet);
        verify(questionDao).delete(questionId);
        verify(transactionManager).commit();
        verify(transactionManager, never()).rollBack();
        assertEquals(0, planetFakeQuestions.size());
    }

    @Test
    public void throwPlanetNotFoundExceptionOnRemoveQuestion() {
        assertThrows(PlanetNotFoundException.class, () -> {
            planetServiceImpl.removeQuestion(anyLong(), anyLong());
        });

        verify(planetDao).findById(anyLong());
        verify(questionDao, never()).findById(anyLong());
        verify(transactionManager, never()).beginWrite();
        verify(planetDao, never()).saveOrUpdate(any());
        verify(questionDao, never()).delete(any());
        verify(transactionManager, never()).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, 1"})
    public void throwQuestionNotFoundExceptionOnRemoveQuestion(Long planetId, Long questionId) {
        Planet fakePlanet = setFakePlanet(planetId);

        when(planetDao.findById(planetId)).thenReturn(fakePlanet);
        when(questionDao.findById(questionId)).thenReturn(null);

        assertThrows(QuestionNotFoundException.class, () -> {
            planetServiceImpl.removeQuestion(planetId, questionId);
        });

        verify(planetDao).findById(planetId);
        verify(questionDao).findById(questionId);
        verify(transactionManager, never()).beginWrite();
        verify(planetDao, never()).saveOrUpdate(any());
        verify(questionDao, never()).delete(any());
        verify(transactionManager, never()).commit();
        verify(transactionManager, never()).rollBack();
    }

    @Test
    @Parameters({"1, 1, 2"})
    public void shouldCorrectlyReturnASetOfQuestionIds(Long planetId, Long question1Id, Long question2Id) throws PlanetNotFoundException {
        Planet fakePlanet = setFakePlanet(planetId);
        Question fakeQuestion1 = setFakeQuestion(question1Id);
        Question fakeQuestion2 = setFakeQuestion(question2Id);

        Set<Question> planetFakeQuestions = new HashSet<>();
        planetFakeQuestions.add(fakeQuestion1);
        planetFakeQuestions.add(fakeQuestion2);

        Set<Long> planetFakeQuestionIds = planetFakeQuestions.stream()
                .map(Question::getId)
                .collect(Collectors.toSet());

        fakePlanet.setQuestions(planetFakeQuestions);

        when(planetDao.findById(planetId)).thenReturn(fakePlanet);

        Set<Long> actualQuestions = planetServiceImpl.getQuestionsIds(planetId);

        assertEquals(planetFakeQuestions.size(), actualQuestions.size());
        assertEquals(planetFakeQuestionIds.size(), actualQuestions.size());
        assertEquals(planetFakeQuestionIds.iterator().next(), actualQuestions.iterator().next());
    }

    @Test
    @Parameters({"1"})
    public void throwsPlanetNotFoundExceptionOnGetQuestionIds(Long planetId) {
        assertThrows(PlanetNotFoundException.class, () -> {
            planetServiceImpl.getQuestionsIds(planetId);
        });
    }

}
