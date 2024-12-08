package com.codeforall.online.c3po.persistence.dao.jpa;

import com.codeforall.online.c3po.model.Answer;
import com.codeforall.online.c3po.persistence.dao.AnswerDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAnswerGenericDao extends JpaGenericDao<Answer> implements AnswerDao {

    /**
     * initializes a new JPA DAO instance given an Answer model
     */
    public JpaAnswerGenericDao() {
        super(Answer.class);
    }
}
