package com.andistoev.optlockingservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;

@RequiredArgsConstructor
@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final EntityManager entityManager;
    private final EntityManagerFactory emf;

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void incrementAmount(String id, int amount) {
//        Item item = itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Item item = em.find(Item.class, id, LockModeType.PESSIMISTIC_WRITE);
//        Item item = entityManager.find(Item.class, id);
        log.info("Setting amount to {} + {} ", item.getAmount(), amount);
        item.setAmount(item.getAmount() + amount);
        em.flush();
        em.getTransaction().commit();
    }

}
