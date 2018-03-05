package com.xrom.ssh.repository.impl;

import com.xrom.ssh.entity.Card;
import com.xrom.ssh.repository.CardRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardRepositoryImpl  extends BaseRepositoryImpl implements CardRepository {

    @Override
    public Card load(Long id) {
        return (Card) getCurrentSession().load(Card.class, id);
    }

    @Override
    public Card get(Long id) {
        return (Card) getCurrentSession().get(Card.class, id);
    }

    @Override
    public List<Card> findAll() {
        return null;
    }

    @Override
    public void persist(Card entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public Long save(Card entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Card entity) {

    }

    @Override
    public void delete(Long id) {
        Card card = load(id);
        getCurrentSession().delete(card);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void update(Card entity) {
        getCurrentSession().update(entity);
    }
}
