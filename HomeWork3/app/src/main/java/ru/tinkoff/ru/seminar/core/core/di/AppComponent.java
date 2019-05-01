package ru.tinkoff.ru.seminar.core.core.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.tinkoff.ru.seminar.core.core.di.module.AppModule;
import ru.tinkoff.ru.seminar.core.core.di.module.RetrofitModule;
import ru.tinkoff.ru.seminar.core.feature.presentation.MainViewModel;

@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class})
public interface AppComponent {
    void inject(MainViewModel viewModel);
}
