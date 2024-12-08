package com.codeforall.online.c3po.persistence.dao.jpa;

import com.codeforall.online.c3po.model.Question;
import com.codeforall.online.c3po.persistence.dao.QuestionDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaQuestionGenericDao extends JpaGenericDao<Question> implements QuestionDao {
    /**
     * Initialize a new JPA DAO instance given a question model
     *
     */
    public JpaQuestionGenericDao() {
        super(Question.class);
    }
}
