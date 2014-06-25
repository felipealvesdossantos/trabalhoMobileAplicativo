package com.projetofinal.ufgnote.notificacao;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import com.projetofinal.ufgnote.R;
import com.projetofinal.ufgnote.notificacao.Preference;

public class SettingsNotificationsDialog extends DialogFragment {
	// View
	Switch switchReitoria;
	Switch switchProReitoria;
	Switch switchBiblioteca;
	Switch switchCoordenador;
	Switch switchDirecao;
	Switch switchGeral;

	Context mContext;

	String mUser;

	public static SettingsNotificationsDialog newInstance() return new SettingsNotificationsDialog();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(R.string.title_fragment_notifications_view);

		View view = inflater.inflate(R.layout.fragment_notifications_dialog,
				container, true);

		initView(view);

		return view;
	}

	private void initView(View view) {
		mContext = view.getContext();
		mUser = Preference.getString(mContext, Preference.USER_LOGGED);

		switchGeral = (Switch) view.findViewById(R.id.generalView);
		switchGeral.setOnCheckedChangeListener(checkedChangeListener);
		switchGeral.setTag(Preference.PUBLIC);
		switchGeral.setChecked(Preference.getBoolean(mContext,
				Preference.getUserPreference(mUser, Preference.PUBLIC)));

		switchReitoria = (Switch) view.findViewById(R.id.rectoryView);
		switchReitoria.setOnCheckedChangeListener(checkedChangeListener);
		switchReitoria.setTag(Preference.REITORIA);
		switchReitoria.setChecked(Preference.getBoolean(mContext,
				Preference.getUserPreference(mUser, Preference.REITORIA)));

		switchProReitoria = (Switch) view.findViewById(R.id.proRectoryView);
		switchProReitoria.setOnCheckedChangeListener(checkedChangeListener);
		switchProReitoria.setTag(Preference.PRO_REITORIAS);
		switchProReitoria.setChecked(Preference.getBoolean(mContext,
				Preference.getUserPreference(mUser, Preference.PRO_REITORIAS)));

		switchBiblioteca = (Switch) view.findViewById(R.id.libraryView);
		switchBiblioteca.setOnCheckedChangeListener(checkedChangeListener);
		switchBiblioteca.setTag(Preference.BIBLIOTECA);
		switchBiblioteca.setChecked(Preference.getBoolean(mContext,
				Preference.getUserPreference(mUser, Preference.BIBLIOTECA)));

		switchCoordenador = (Switch) view
				.findViewById(R.id.courseCoordinatorView);
		switchCoordenador.setOnCheckedChangeListener(checkedChangeListener);
		switchCoordenador.setTag(Preference.COORDENADOR_CURSO);
		switchCoordenador.setChecked(Preference.getBoolean(mContext, Preference
				.getUserPreference(mUser, Preference.COORDENADOR_CURSO)));

		switchDirecao = (Switch) view.findViewById(R.id.boardUnityView);
		switchDirecao.setOnCheckedChangeListener(checkedChangeListener);
		switchDirecao.setTag(Preference.DIRECAO_UNIDADE_CURSO);
		switchDirecao.setChecked(Preference.getBoolean(mContext, Preference
				.getUserPreference(mUser, Preference.DIRECAO_UNIDADE_CURSO)));
	}

	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			Object object = buttonView.getTag();
			if (object != null) {
				if (object instanceof String) {
					String key = (String) object;

					Preference
							.setBoolean(mContext,
									Preference.getUserPreference(mUser, key),
									isChecked);
				}
			}
		}
	};
}