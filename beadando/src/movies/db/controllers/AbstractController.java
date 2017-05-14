package movies.db.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import movies.db.DataSource;
import movies.db.entities.EntityWithId;

public abstract class AbstractController<E extends EntityWithId> implements EntityController<E> {

	private final String FULL_SELECT_SQL;
	private final String SELECT_COUNT_SQL;
	private final String SELECT_BY_ID_SQL;

	public AbstractController(String tableName) {
		FULL_SELECT_SQL = "SELECT * FROM " + tableName;
		SELECT_COUNT_SQL = "SELECT COUNT(*) AS CNT FROM " + tableName;
		SELECT_BY_ID_SQL = FULL_SELECT_SQL + " WHERE ID = ";
	}

	protected void doOnResultSet(String sql, int resultSetType, int resultSetConcurrency, RunnableOnResultSet todo)
			throws SQLException {
		try (Connection connection = DataSource.getInstance().getConnection();
				Statement statement = connection.createStatement(resultSetType, resultSetConcurrency);
				ResultSet rs = statement.executeQuery(sql);) {
			todo.run(rs);
		}
	}

	@Override
	public int getEntityCount() throws SQLException {
		final IntegerHolder count = new IntegerHolder();
		doOnResultSet(SELECT_COUNT_SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
			rs.next();
			count.setIntData(rs.getInt("CNT"));
		});
		return count.getIntData();
	}

	@Override
	public List<E> getEntities() throws SQLException {
		final List<E> entities = new ArrayList<>();
		doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
			while (rs.next()) {
				E entity = newEntity();
				entity.setId(rs.getLong("ID"));
				setEntityAttributes(entity, rs);
				entities.add(entity);
			}
		});
		return entities;
	}

	@Override
	public E getEntityById(long id) throws SQLException {
		final E entity = newEntity();
		doOnResultSet(SELECT_BY_ID_SQL + id, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
				(ResultSet rs) -> {
					rs.next();
					entity.setId(rs.getLong("ID"));
					setEntityAttributes(entity, rs);
				});
		return entity;
	}

	@Override
	public E getEntityByRowIndex(int rowIndex) throws SQLException {
		final E entity = newEntity();
		doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
				(ResultSet rs) -> {
					rs.absolute(rowIndex + 1);
					entity.setId(rs.getLong("ID"));
					setEntityAttributes(entity, rs);
				});
		return entity;
	}

	@Override
	public void addEntity(E entity) throws SQLException {
		doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE,
				(ResultSet rs) -> {
					rs.moveToInsertRow();
					rs.updateLong("ID", DataSource.getInstance().obtainNewId());
					getEntityAttributes(rs, entity);
					rs.insertRow();
				});
	}

	@Override
	public void deleteEntity(int index) throws SQLException {
		doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
			rs.absolute(index + 1);
			rs.deleteRow();
		});
	}

	@Override
	public void updateEntity(E entity, int index) throws SQLException {
		doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
			rs.absolute(index + 1);
			rs.updateLong("ID", entity.getId());
			getEntityAttributes(rs, entity);
			rs.updateRow();
		});
	}

	private class IntegerHolder {
		private int intData;

		public void setIntData(int intData) {
			this.intData = intData;
		}

		public int getIntData() {
			return intData;
		}

	}

	protected interface RunnableOnResultSet {
		public abstract void run(ResultSet rs) throws SQLException;
	}

	protected abstract E newEntity();

	protected abstract void setEntityAttributes(E entity, ResultSet resultSet) throws SQLException;

	protected abstract void getEntityAttributes(ResultSet resultSet, E entity) throws SQLException;

}
