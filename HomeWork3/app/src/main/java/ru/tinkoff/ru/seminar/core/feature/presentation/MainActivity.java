package ru.tinkoff.ru.seminar.core.feature.presentation;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import ru.tinkoff.ru.seminar.R;
import ru.tinkoff.ru.seminar.core.feature.domain.model.Weather;
import ru.tinkoff.ru.seminar.core.feature.domain.model.WeatherList;


public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button performBtn;
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView resultTextView;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        init();
        showProgress(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(
                broadcastReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void init() {
        initSpinner();
        imageView = findViewById(R.id.imageView);
        performBtn = findViewById(R.id.performBtn);
        progressBar = findViewById(R.id.progressBar);
        resultTextView = findViewById(R.id.resultTextView);
        performBtn.setOnClickListener(v -> {
            performRequest(spinner.getSelectedItem().toString());
            setEnablePerformButton(false);
            showProgress(true);
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                setEnablePerformButton(true);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud_black_24dp));

            } else {
                setEnablePerformButton(false);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_signal_cellular_off_black_24dp));
                Snackbar.make((View) imageView.getParent(), getResources().getString(R.string.connect_error), Snackbar.LENGTH_LONG).show();
            }

        }
    };

    private void setEnablePerformButton(boolean enable) {
        performBtn.setEnabled(enable);
    }

    @SuppressLint("DefaultLocale")
    private void printResult(@NonNull Weather weather, @NonNull List<Weather> forecast) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format(
                        "Current Weather:\n\nDesc: %s \nDate: %s\nTemp: %.1f C\nSpeed wind: %.1f m/s\n\n",
                        weather.description,
                        viewModel.unixTimeToDate(weather.time),
                        viewModel.KelvinToCelsius(weather.temp),
                        weather.speedWind
                )
        );
        stringBuilder.append("Forecast:\n");
        for (Weather forecastWeather : forecast.subList(0, 5)) {
            stringBuilder.append("\n");
            stringBuilder.append(String.format(
                    "Desc: %s \nDate: %s\nTemp: %.1f C\nSpeed wind: %.1f m/s\n",
                    forecastWeather.description,
                    viewModel.unixTimeToDate(forecastWeather.time),
                    viewModel.KelvinToCelsius(forecastWeather.temp),
                    forecastWeather.speedWind
            ));
        }
        resultTextView.setText(stringBuilder.toString());

    }

    private void showProgress(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void showError(@NonNull String error) {
        Snackbar.make((View) imageView.getParent(), error, Snackbar.LENGTH_LONG).show();

    }

    private void performRequest(@NonNull String city) {
        viewModel.getWeather(city, single -> single.subscribe(
                pair -> {
                    printResult(((Weather) pair.first), ((WeatherList) pair.second).weatherList);
                    showProgress(false);
                    setEnablePerformButton(true);
                },
                error -> {
                    showError(error.getMessage());
                    showProgress(false);
                    setEnablePerformButton(true);
                }
        ));
    }

    void initSpinner() {
        spinner = findViewById(R.id.spinner);
        SpinnerAdapter adapter = new ArrayAdapter<>(getBaseContext(), R.layout.spinner_item, getResources().getStringArray(R.array.city_array));
        ((ArrayAdapter) adapter).setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
    }

}

