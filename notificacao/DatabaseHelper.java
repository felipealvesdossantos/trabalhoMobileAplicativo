package com.projetofinal.ufgnote.notificacao;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.projetofinal.ufgnote.R;
import com.projetofinal.ufgnote.notificacao.Notification;
import com.projetofinal.ufgnote.notificacao.User;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Classe de respons�vel por gerenciar o esquema do banco de dados e conter os
 * objetos de acesso a dados de todas classes pojo.
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private final String LOG_TAG = getClass().getSimpleName();

	private UserDAO userDao = null;
	private NotificationDAO notificationDao = null;

	private static final AtomicInteger usageCounter = new AtomicInteger(0);
	private static DatabaseHelper helperInstance = null;

	private DatabaseHelper(final Context context) {
		super(context, context.getString(R.string.database_name), null, Integer
				.valueOf(context.getString(R.string.database_version)),
				R.raw.ormlite_config);
	}

	/**
	 * Retorna o helper ({@link #helperInstance}) que � uma inst�ncia do objeto
	 * que gerencia as conex�es com o banco de dados e os objetos de acesso a
	 * dados (DAO). Ser� criada uma inst�ncia do helper e ser� cacheado para
	 * futuras chamadas a este m�todo. <br>
	 * <b>A cada chamada a este m�todo dever� haver 1 e somente uma chamada ao
	 * m�todo {@link #close()}.</b>
	 * 
	 * @param context
	 *            contexto da aplica��o.
	 * @return o helper.
	 */
	public static synchronized DatabaseHelper getHelperInstance(
			final Context context) {
		if (helperInstance == null) {
			helperInstance = new DatabaseHelper(context);
		}
		usageCounter.incrementAndGet();
		Log.d("DatabaseHelper", String.format(
				"Uso de conex�es com banco de dados: %d", usageCounter.get()));
		return helperInstance;
	}

	/**
	 * Este m�todo � chamado quando o banco de dados � criado pela primeira vez.
	 */
	@Override
	public void onCreate(final SQLiteDatabase database,
			final ConnectionSource connectionSource) {
		Log.d(LOG_TAG, "Chamada do m�todo onCreate em execu��o...");
		try {
			int schemaCreatedCount = 0;
			schemaCreatedCount += TableUtils.createTable(connectionSource,
					User.class);
			schemaCreatedCount += TableUtils.createTable(connectionSource,
					Notification.class);
			// schemaCreatedCount += TableUtils.createTable(connectionSource,
			// Class.class);
			Log.d(LOG_TAG, String.format(
					"%d script(s) executado(s) com sucesso!",
					schemaCreatedCount));
			insertStaticDataIntoDatabase(database);
		} catch (final SQLException e) {
			Log.e(LOG_TAG, "Erro ao tentar criar esquema do banco de dados.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Este m�todo � chamado quando a aplica��o � atualizada e houver uma vers�o
	 * superior do banco de dados (Refere-se �:
	 * {@link br.ufg.es.snef.R.string#database_version}).<br>
	 * <br>
	 * Assim para que a aplica��o se ajuste �s modifica��es feita no banco de
	 * dados, as tabelas s�o recriadas (
	 * {@link TableUtils#createTable(ConnectionSource, Class)}, depois
	 * {@link TableUtils#dropTable(ConnectionSource, Class, boolean)}).
	 */
	@Override
	public void onUpgrade(final SQLiteDatabase database,
			final ConnectionSource connectionSource, final int oldVersion,
			final int newVersion) {
		Log.d(LOG_TAG, "Chamada do m�todo onUpgrade em execu��o...");
		try {
			int schemaDeletedCount = 0;
			schemaDeletedCount += TableUtils.dropTable(connectionSource,
					User.class, true);
			schemaDeletedCount += TableUtils.dropTable(connectionSource,
					Notification.class, true);
			Log.d(LOG_TAG, String.format(
					"%d script(s) executado(s) com sucesso!",
					schemaDeletedCount));
			super.onCreate(database);
		} catch (final SQLException e) {
			Log.e(LOG_TAG,
					String.format(
							"Erro ao tentar atualizar esquema do banco de dados da vers�o %d para a vers�o %d.",
							oldVersion, newVersion), e);
			throw new RuntimeException(e);
		}
	}

	private DatabaseHelper getHelper() {
		return helperInstance;
	}

	/**
	 * Retorna o objeto de acesso a dados para a classe {@link User}. Ser�
	 * criada uma inst�ncia do objeto e ser� cacheado para futuras chamadas a
	 * este m�todo.
	 * 
	 * @return o DAO da classe {@link User}.
	 * @throws SQLException
	 */
	public UserDAO getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = new UserDAO(getHelper().getConnectionSource());
		}
		return userDao;
	}

	/**
	 * Retorna o objeto de acesso a dados para a classe {@link Notification}.
	 * Ser� criada uma inst�ncia do objeto e ser� cacheado para futuras chamadas
	 * a este m�todo.
	 * 
	 * @return o DAO da classe {@link Notification}.
	 * @throws SQLException
	 */
	public NotificationDAO getNotificationDao() throws SQLException {
		if (notificationDao == null) {
			notificationDao = new NotificationDAO(getHelper()
					.getConnectionSource());
		}
		return notificationDao;
	}

	/**
	 * Fecha a conex�o com banco de dados e libera as inst�ncias alocadas para
	 * os objetos de acesso a dados (DAO). Para cada chamada ao m�todo est�tico
	 * ({@link #getHelperInstance(Context)}, dever� haver 1 e somente 1 chamada
	 * para este m�todo.<br>
	 * Por exemplo: Se houverem 3 chamadas ao m�todo est�tico
	 * {@link #getHelperInstance(Context)} ent�o somente na terceira chamada a
	 * este m�todo, o helper ({@link #helperInstance}) e suas conex�es ser�o
	 * liberadas e fechadas.
	 */
	@Override
	public void close() {
		Log.d(LOG_TAG, String.format("Uso de conex�es com banco de dados: %d",
				usageCounter.get()));
		if (usageCounter.decrementAndGet() == 0) {
			Log.d("DatabaseHelper", "Fechando conex�o com banco de dados.");
			super.close();
			userDao = null;
			notificationDao = null;
			helperInstance = null;
		}
	}

	private synchronized void insertStaticDataIntoDatabase(
			final SQLiteDatabase database) throws SQLException {
		if (database == null) {
			throw new IllegalArgumentException("");
		}

		if (database.isOpen()) {
			if (getUserDao().isTableExists()) {

			}
		}
	}

}
