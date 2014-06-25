package com.projetofinal.ufgnote.notificacao;


import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.projetofinal.ufgnote.R;
import com.projetofinal.ufgnote.notificacao.Notification;
import com.projetofinal.ufgnote.notificacao.Sender;
import com.projetofinal.ufgnote.notificacao.DatabaseHelper;

import com.j256.ormlite.stmt.UpdateBuilder;

public class SelectedNotificationActivity extends Activity {
	private final String LOG_TAG = getClass().getSimpleName();

	public static final String PARAM_REQUEST_NOTIFICATION = "request_notification";

	// Persistence
	private DatabaseHelper mHelper;

	// View
	TextView mDate;
	TextView mDescription;
	TextView mSender;
	
	// Object
	Notification mNotification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selected_notification);

		initView();

		mNotification = (Notification) getIntent().getExtras().getSerializable(
				PARAM_REQUEST_NOTIFICATION);

		if (mNotification != null) {
			mDate.setText(mNotification.getDate().toString());
			mDescription.setText(mNotification.getContent());
			mSender.setText(""
					+ Sender.getDefault(mNotification.getSender().intValue())
							.getName());

			// Atualizar a visualiza��o da notifica��o
			if (setNotificationAsViewed(true)) {
				Toast.makeText(this, "Notifica��o visualizada",
						Toast.LENGTH_SHORT).show();
				Log.i(LOG_TAG, "Notifica��o visualizada -> true");
			} else {
				Log.i(LOG_TAG,
						"Erro ao atualizar a visualiza��o da notifica��o");
			}

		} else {
			// Fechar tela
			finish();
		}

	}

	private void initView() {
		mDate = (TextView) findViewById(R.id.notificationDate);
		mDescription = (TextView) findViewById(R.id.notificationDescription);
		mSender = (TextView) findViewById(R.id.notificationSender);
	}

	/**
	 * Atualiza a notifica��o selecionada, atribuindo a data atual do sistema
	 * como sendo a data de visualiza��o mais recente desta notifica��o.
	 * 
	 * @return <b>True</b> se a atualiza��o foi realizada com sucesso, ou
	 *         <b>False</b> se nenhuma atualiza��o foi realizada.
	 * @throws SQLException
	 */
	private boolean setNotificationAsViewed(Boolean read) {

		// Notifica��o j� visualizada
		if (getSelectedNotification().isRead() == read) {
			return true;
		}

		// Alterar a visualiza��o da notifica��o
		// True, indicando que a mensagem j� foi viaulizada
		// False, mensagem n�o visualizada
		boolean successfullyUpdated = false;

		try {

			final UpdateBuilder<Notification, Long> updateBuilder = getHelper().getNotificationDao()
					.updateBuilder();

			// Atualiza a visualiza��o
			updateBuilder.updateColumnValue(Notification.COLUMN_READ, read);
			updateBuilder.where().idEq(getSelectedNotification().getId());

			successfullyUpdated = updateBuilder.update() == 1;

		} catch (SQLException e) {
			Log.i(LOG_TAG,
					"Erro ao atualizar a visualiza��o da notifica��o -> "
							+ read.toString());
		}

		if (successfullyUpdated) {
			getSelectedNotification().setRead(read);
			Log.i(LOG_TAG,
					"Visualiza��o da notifica��o atualizada com sucesso -> "
							+ read.toString());
		}

		return successfullyUpdated;
	}

	private DatabaseHelper getHelper() {
		if (mHelper == null) {
			mHelper = DatabaseHelper
					.getHelperInstance(SelectedNotificationActivity.this);
		}
		return mHelper;
	}

	private Notification getSelectedNotification() {
		return mNotification;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.selected_notification, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_mark:
			updateMenuRead(item);
			break;

		default:
			break;
		}
		return true;
	}

	private void updateMenuRead(MenuItem item) {

		if (getSelectedNotification().isRead()) {
			// Marcar notifica��o como n�o lida
			item.setTitle(R.string.action_mark_no_read);
			setNotificationAsViewed(false);
		} else {
			// Marcar notifica��o como lida
			item.setTitle(R.string.action_mark_read);
			setNotificationAsViewed(true);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null) {
			mHelper.close();
			mHelper = null;
		}
	}
}
