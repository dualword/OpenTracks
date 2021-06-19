package de.dennisguse.opentracks.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.AbstractActivity;
import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.adapters.SettingsCustomLayoutAdapter;
import de.dennisguse.opentracks.databinding.ActivitySettingsCustomLayoutBinding;
import de.dennisguse.opentracks.util.PreferencesUtils;

public class SettingsCustomLayoutActivity extends AbstractActivity {

    private ActivitySettingsCustomLayoutBinding viewBinding;
    private SettingsCustomLayoutAdapter settingsCustomLayoutAdapter;
    private SharedPreferences sharedPreferences;
    private List<String> orderItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferencesUtils.getSharedPreferences(this);

        settingsCustomLayoutAdapter = new SettingsCustomLayoutAdapter(this, PreferencesUtils.getCustomLayoutOrder(sharedPreferences, this));

        RecyclerView sensorsRecyclerView = viewBinding.recyclerView;
        sensorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorsRecyclerView.setAdapter(settingsCustomLayoutAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                orderItems = settingsCustomLayoutAdapter.swapData(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(sensorsRecyclerView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!orderItems.isEmpty()) {
            PreferencesUtils.setCustomLayoutOrder(sharedPreferences, this, orderItems);
            orderItems.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences = null;
    }

    @Override
    protected void setupActionBarBack(Toolbar toolbar) {
        super.setupActionBarBack(toolbar);
        toolbar.setTitle(R.string.menu_settings);
    }

    @Override
    protected View getRootView() {
        viewBinding = ActivitySettingsCustomLayoutBinding.inflate(getLayoutInflater());
        return viewBinding.getRoot();
    }
}
