package com.example.jewellery.service;

import com.example.jewellery.entity.Item;
import com.example.jewellery.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.var;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoreService {

    private final EntityManagerFactory puneEmf;
    private final EntityManagerFactory mumbaiEmf;

    public StoreService(@Qualifier("puneEntityManagerFactory") EntityManagerFactory puneEmf,
                        @Qualifier("mumbaiEntityManagerFactory") EntityManagerFactory mumbaiEmf) {
        this.puneEmf = puneEmf;
        this.mumbaiEmf = mumbaiEmf;
    }

    private EntityManager getEm(String storeId) {
        if ("pune".equalsIgnoreCase(storeId)) return puneEmf.createEntityManager();
        if ("mumbai".equalsIgnoreCase(storeId)) return mumbaiEmf.createEntityManager();
        throw new IllegalArgumentException("Unknown storeId: " + storeId + " (use pune/mumbai)");
    }

    public Item createItem(String storeId, Item item) {
        EntityManager em = getEm(storeId);
        var tx = em.getTransaction();
        tx.begin();
        em.persist(item);
        tx.commit();
        em.close();
        return item;
    }

    public List<Item> listItems(String storeId) {
        EntityManager em = getEm(storeId);
        List<Item> list = em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
        em.close();
        return list;
    }

    public Sale recordSale(String storeId, Sale sale) {
        sale.setSoldAt(LocalDateTime.now());
        EntityManager em = getEm(storeId);
        var tx = em.getTransaction();
        tx.begin();
        em.persist(sale);
        tx.commit();
        em.close();
        return sale;
    }

    public List<Sale> listSales(String storeId) {
        EntityManager em = getEm(storeId);
        List<Sale> list = em.createQuery("SELECT s FROM Sale s", Sale.class).getResultList();
        em.close();
        return list;
    }

    public long totalItemsAcrossStores() {
        return listItems("pune").size() + listItems("mumbai").size();
    }
}
