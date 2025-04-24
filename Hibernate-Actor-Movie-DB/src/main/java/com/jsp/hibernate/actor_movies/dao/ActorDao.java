package com.jsp.hibernate.actor_movies.dao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import com.jsp.hibernate.actor_movies.entity.Actor;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Scanner;

public class ActorDao {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Actor");
    Scanner sc = new Scanner(System.in);

    public void addActor() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Actor actor = new Actor();
            System.out.println("Enter the Actor details ");
            sc.nextLine();

            System.out.print("Enter Actor Name: ");
            actor.setActorName(sc.nextLine());

            System.out.print("Enter Age: ");
            actor.setAge(sc.nextInt());
            sc.nextLine();

            System.out.print("Enter Industry: ");
            actor.setIndustries(sc.nextLine());

            System.out.print("Enter Nationality: ");
            actor.setNationality(sc.nextLine());

            em.persist(actor);
            et.commit();

            System.out.println("Data Inserted Successfully into Database");
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void findActorById() {
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter Actor ID: ");
        int id = sc.nextInt();
        Actor actor = em.find(Actor.class, id);
        if (actor != null) {
            System.out.println("Actor Found: " + actor.getActorName());
        } else {
            System.out.println("No Actor found with ID " + id);
        }
        em.close();
    }

    public void findActorByName() {
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter Actor Name: ");
        String name = sc.next();
        Query query = em.createQuery("FROM Actor a WHERE a.actorName = :name");
        query.setParameter("name", name);
        List<Actor> actors = query.getResultList();
        for (Actor actor : actors) {
            System.out.println(actor.getActorName() + " - " + actor.getIndustries());
        }
        em.close();
    }

    public void findActorsByIndustry() {
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter Industry: ");
        String industry = sc.next();
        Query query = em.createNativeQuery("SELECT * FROM Actor WHERE industries = ?", Actor.class);
        query.setParameter(1, industry);
        List<Actor> actors = query.getResultList();
        for (Actor actor : actors) {
            System.out.println(actor.getActorName() + " - " + actor.getNationality());
        }
        em.close();
    }

    public void findActorsBetweenAge() {
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter Min Age: ");
        int minAge = sc.nextInt();
        System.out.print("Enter Max Age: ");
        int maxAge = sc.nextInt();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Actor> cq = cb.createQuery(Actor.class);
        Root<Actor> root = cq.from(Actor.class);

        Predicate ageBetween = cb.between(root.get("age"), minAge, maxAge);
        cq.select(root).where(ageBetween);

        List<Actor> actors = em.createQuery(cq).getResultList();
        for (Actor actor : actors) {
            System.out.println(actor.getActorName() + " - Age: " + actor.getAge());
        }
        em.close();
    }

    public void findActorsWithAgeGreaterThan() {
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter Minimum Age: ");
        int age = sc.nextInt();

        Query query = em.createQuery("FROM Actor a WHERE a.age > :age");
        query.setParameter("age", age);

        List<Actor> actors = query.getResultList();
        for (Actor actor : actors) {
            System.out.println(actor.getActorName() + " - Age: " + actor.getAge());
        }
        em.close();
    }

    public void updateActorAgeById() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        System.out.print("Enter Actor ID: ");
        int id = sc.nextInt();
        System.out.print("Enter New Age: ");
        int newAge = sc.nextInt();

        try {
            et.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<Actor> update = cb.createCriteriaUpdate(Actor.class);
            Root<Actor> root = update.from(Actor.class);
            update.set("age", newAge).where(cb.equal(root.get("actorId"), id));
            em.createQuery(update).executeUpdate();
            et.commit();
            System.out.println("Age updated successfully.");
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void updateActorNationalityByIndustry() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        System.out.print("Enter Industry: ");
        String industry = sc.next();
        System.out.print("Enter New Nationality: ");
        String nationality = sc.next();

        try {
            et.begin();
            Query query = em.createQuery("UPDATE Actor a SET a.nationality = :nat WHERE a.industries = :ind");
            query.setParameter("nat", nationality);
            query.setParameter("ind", industry);
            int updated = query.executeUpdate();
            et.commit();
            System.out.println(updated + " actor(s) updated.");
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void deleteActorById() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        System.out.print("Enter Actor ID to delete: ");
        int id = sc.nextInt();

        try {
            et.begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaDelete<Actor> delete = cb.createCriteriaDelete(Actor.class);
            Root<Actor> root = delete.from(Actor.class);
            delete.where(cb.equal(root.get("actorId"), id));
            em.createQuery(delete).executeUpdate();
            et.commit();
            System.out.println("Actor deleted successfully.");
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void findActorsByMoviesTitle() {
        EntityManager em = emf.createEntityManager();
        System.out.print("Enter Movie Title: ");
        String title = sc.next();

        Query query = em.createNativeQuery(
                "SELECT a.* FROM actor a JOIN actor_movie am ON a.actorId = am.actor_actorId " +
                        "JOIN movie m ON m.movieId = am.movie_movieId WHERE m.title = ?", Actor.class);
        query.setParameter(1, title);

        List<Actor> actors = query.getResultList();
        for (Actor actor : actors) {
            System.out.println(actor.getActorName());
        }
        em.close();
    }
}
