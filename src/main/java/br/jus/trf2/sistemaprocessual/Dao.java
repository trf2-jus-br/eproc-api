package br.jus.trf2.sistemaprocessual;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;

public class Dao implements Closeable {
	private EntityManager em;
	public static ThreadLocal<Dao> current = new ThreadLocal<>();

	public Dao() {
		this.em = PersistenceManager.INSTANCE.getEntityManager();
		current.set(this);
	}

	public Date obtemData() {
		return (Date) em.createNativeQuery("select sysdate() from dual").getSingleResult();
	}

	public void beginTransaction() {
		this.em.getTransaction().begin();
	}

	public void rollbackTransaction() {
		if (this.em.getTransaction().isActive())
			this.em.getTransaction().rollback();
	}

	public static void rollbackCurrentTransaction() {
		current.get().rollbackTransaction();
	}

	public <T> T find(Class<T> clazz, Long id) {
		return em.find(clazz, id);
	}

	public void persist(Object o) {
		if (!em.getTransaction().isActive())
			beginTransaction();
		this.em.persist(o);
		this.em.flush();
	}

	public void remove(Object o) {
		if (!em.getTransaction().isActive())
			beginTransaction();
		this.em.remove(o);
	}

	@Override
	public void close() throws IOException {
		if (em != null) {
			if (em.getTransaction().isActive()) {
				em.flush();
				em.getTransaction().commit();
			}
			em.close();
		}
	}

}
