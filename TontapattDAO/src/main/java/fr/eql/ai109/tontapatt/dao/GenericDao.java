package fr.eql.ai109.tontapatt.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.eql.ai109.tontapatt.idao.GenericIDao;

public abstract class GenericDao<T> implements GenericIDao<T> {

	@PersistenceContext(unitName = "Persistence")
	protected EntityManager entityManager;

	public T add(T t) {
		try {
			entityManager.persist(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public void delete(T t) {
		try {
			entityManager.remove(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public T update(T t) {
		try {
			entityManager.merge(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public T getById(int i) {
		T t = null;
		try {
			String className = ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0]
							.getTypeName();
			Class<?> clazz;
			clazz = Class.forName(className);
			t = (T) entityManager.find(clazz, i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		List<T> objects = null;
		try {
			String className = ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0]
							.getTypeName();
			Class<?> clazz;
			clazz = Class.forName(className);

			objects = (List<T>) entityManager.createQuery("FROM " + clazz.getName())
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}
}
