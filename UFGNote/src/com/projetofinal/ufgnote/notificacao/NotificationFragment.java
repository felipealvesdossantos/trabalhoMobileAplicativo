package com.projetofinal.ufgnote.notificacao;

import java.sql.SQLException;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.projetofinal.ufgnote.R;
import com.projetofinal.ufgnote.notificacao.SelectedNotificationActivity;
import com.projetofinal.ufgnote.notificacao.AdapterNotification;
import com.projetofinal.ufgnote.notificacao.Notification;
import com.projetofinal.ufgnote.persistencia.UsuarioDAO;
import com.projetofinal.ufgnote.notificacao.DatabaseHelper;

@SuppressLint("ValidFragment")
public class NotificationFragment extends ListFragment {
	private final String LOG_TAG = getClass().getSimpleName();

	private DatabaseHelper mHelper;
	private Integer mSenderId;
	private Context mContext;

	// View
	private ProgressBar mProgress;

	// Task
	private TaskLoadNotification taskLoadNotification;

	public NotificationFragment(Integer senderId) {
		this.mSenderId = senderId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_notification,
				container, false);

		initView(rootView);
		return rootView;
	}

	private void initView(View view) {
		mProgress = (ProgressBar) view.findViewById(R.id.progressBar);
		mProgress.setVisibility(View.VISIBLE);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mContext = view.getContext();

		initTask();
	}

	private void initTask() {
		
		if (taskLoadNotification == null) {
			taskLoadNotification = new TaskLoadNotification();
			taskLoadNotification.execute();
		} else {
			
			if (taskLoadNotification.getStatus() == AsyncTask.Status.FINISHED) {
				taskLoadNotification = new TaskLoadNotification();
				taskLoadNotification.execute();
			}
		}

	}

	private class TaskLoadNotification extends
			AsyncTask<Void, Void, List<Notification>> {

		@Override
		protected List<Notification> doInBackground(Void... arg0) {

			return loadNotificationList();
		}

		protected void onPostExecute(List<Notification> result) {
			if (!isCancelled()) {
				insertAdapter(result);
			}
			closeProgress();
		};
	};


	private List<Notification> loadNotificationList() {
		try {
			final List<Notification> notificationList = getDao().listFomSender(
					mSenderId.longValue());
			return notificationList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	private void insertAdapter(List<Notification> result) {
		if (result != null) {
			Log.d(LOG_TAG, "Notifica��es: " + result.size());
			final BaseAdapter adapter = new AdapterNotification(mContext,
					result);
			this.setListAdapter(adapter);
		}
	}

	private void closeProgress() {
		mProgress.setVisibility(View.GONE);
	}


	private NotificationDAO getDao() throws SQLException{
		if (mHelper == null) {
			mHelper = DatabaseHelper.getHelperInstance(mContext);
		}

		return mHelper.getNotificationDao();
	}

	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {
		final Intent intentSelectedNotification = new Intent(getActivity(),
				SelectedNotificationActivity.class);

		final Notification selectedNotification = (Notification) getListView()
				.getAdapter().getItem(position);

		Bundle extras = new Bundle();
		extras.putSerializable(
				SelectedNotificationActivity.PARAM_REQUEST_NOTIFICATION,
				selectedNotification);

		intentSelectedNotification.putExtras(extras);

		startActivity(intentSelectedNotification);
	}

	@Override
	public void onResume() {
		super.onResume();
		initTask();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// Helper
		if (mHelper != null) {
			mHelper.close();
			mHelper = null;
		}

		// Task
		if (taskLoadNotification != null) {
			// Background
			if (taskLoadNotification.getStatus() == AsyncTask.Status.RUNNING) {
				taskLoadNotification.cancel(true);
			}
		}
	}

}
