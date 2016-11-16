package ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.snipsnap.slyce_messaging_example.R;


public class PrefsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

    }

    //选中的设置
    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

}
