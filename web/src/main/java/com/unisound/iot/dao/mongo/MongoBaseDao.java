package com.unisound.iot.dao.mongo;

import com.mongodb.WriteResult;
import com.unisound.iot.common.util.MongoDBReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @title: 基础Dao
 * @author: lizehao
 * @date: 2016年6月18日22:32:43
 */
@Repository
public class MongoBaseDao<T> {

	@Autowired
	private MongoTemplate mongoTemplate;

	public final MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	/**
	 * 保存一个对象
	 *
	 * @param entity
	 */
	public void insert(T entity) {
		mongoTemplate.insert(entity);
	}

	/**
	 * save和insert区别 save:有则改之,无则插入
	 *
	 * @param entity
	 */
	public void save(T entity) {
		mongoTemplate.save(entity);
	}

	public void save(List<T> list) {
		mongoTemplate.insert(list, this.getEntityClass());
	}

	public T findById(String id) {
		Class< T > object = this.getEntityClass();
		return mongoTemplate.findById(id, object );
	}

	/**
	 *
	 * 排序:Query query = new Query(); query.with(new Sort(new
	 * Order(Direction.DESC,"age")));
	 *
	 * @param query
	 * @return
	 */
	public List<T> find(Query query) {
		Class object = this.getEntityClass();
		return mongoTemplate.find(query, this.getEntityClass());
	}

	public List<T> findAll() {
		return mongoTemplate.findAll(this.getEntityClass());
	}

	/**
	 * 根据Id删除
	 *
	 * @param id
	 */
	public void deleteById(String id) {
		Criteria criteria = Criteria.where("_id").in(id);
		if (null != criteria) {
			Query query = new Query(criteria);
			if (this.find(query) != null) {
				this.delete(query);
			}
		}
	}

	public WriteResult delete(Query query) {
		mongoTemplate.remove(query, this.getEntityClass());
		return null;
	}

	public long count(Query query) {
		return mongoTemplate.count(query, this.getEntityClass());
	}

	/**
	 * 一次最多只更新一个文档，也就是条件query条件，且执行sort后的第一个文档。它的功能强大之处在于可以保证操作的原子性。
	 *
	 * @param query
	 *            查询条件
	 * @param update
	 *            更新值
	 * @param returnNew
	 *            返回文档参数, true:返回更新后的文档, false:返回更新前的文档（默认值）
	 * @param upsert
	 *            更新操作, true:有则更新无则插入参数, false:只更新
	 */
	public T findAndModify(Query query, Update update, boolean returnNew, boolean upsert) {
		return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(returnNew)
				.upsert(upsert), this.getEntityClass());
	}



	/**
	 * 获取需要操作的实体类class
	 *
	 * @return
	 */
	private Class<T> getEntityClass() {
		return MongoDBReflectionUtils.getSuperClassGenricType(getClass());
	}





	private String determineCollectionName(Class<?> entityClass) {
		if (entityClass == null) {
			throw new InvalidDataAccessApiUsageException(
					"No class parameter provided, entity collection can't be determined!");
		}
		String collName = entityClass.getSimpleName();
		if (entityClass.isAnnotationPresent(Document.class)) {
			Document document = entityClass.getAnnotation(Document.class);
			collName = document.collection();
		} else {
			collName = collName.replaceFirst(collName.substring(0, 1), collName.substring(0, 1).toLowerCase());
		}
		return collName;
	}
}
