package com.projetofinal.ufgnote.notificacao;

import com.projetofinal.ufgnote.notificacao.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preference {

	public static final String COORDENADOR_CURSO = "COORDENADOR";
	public static final String DIRECAO_UNIDADE_CURSO = "DIRECAO_UNIDADE";
	public static final String BIBLIOTECA = "BIBLIOTECA";
	public static final String PRO_REITORIAS = "PRO_REITORIAS";
	public static final String REITORIA = "REITORIA";
	public static final String DOCENTE_DISCIPLINA = "DOCENTE_DISCIPLINA";
	public static final String PUBLIC = "PUBLIC";

	/**
	 * Salvar qual o �ltimo {@link User} que est� logado no sistema
	 * 
	 */
	public static final String USER_LOGGED = "user_logged";
	/**
	 * Status se o {@link User} acessou como visitante ou realizando o login
	 * <p>
	 * <b>true</b> acesso realizado com login</br>
	 * </p>
	 * <p>
	 * <b>false</b> acesso como visitante
	 * </p>
	 */
	public static final String LOGGED = "logged";

	public static final boolean DEFAULT_BOOLEAN = false;
	public static final String DEFAULT_STRING = "";

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setBoolean(final Context context, final String key,
			final boolean value) {
		final SharedPreferences preferences = getPreferences(context);
		final SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
		Log.d("Preference SET", key + " = " + value);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(final Context context, final String key) {
		final SharedPreferences preferences = getPreferences(context);
		final boolean value = preferences.getBoolean(key, DEFAULT_BOOLEAN);
		Log.d("Preference GET ", key + " = " + value);
		return value;
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setString(final Context context, final String key,
			final String value) {
		final SharedPreferences preferences = getPreferences(context);
		final SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
		Log.d("Preference SET ", key + " = " + value);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(final Context context, final String key) {
		final SharedPreferences preferences = getPreferences(context);
		final String value = preferences.getString(key, DEFAULT_STRING);
		Log.d("Preference GET ", key + " = " + value);
		return value;
	}

	/**
	 * Chave de preferencia do usu�rio
	 * 
	 * @param user
	 * @param key
	 * @return
	 */
	public static String getUserPreference(final String user, final String key) {
		return user.concat(key);
	}

	/**
	 * 
	 * @return Preferencias da aplica��o {@code SharedPreferences}.
	 */
	public static SharedPreferences getPreferences(final Context context) {
		return context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE);
	}
}
