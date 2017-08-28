package com.example.olden.cryptoexchange.presentation.currencies_list.presenter;


import android.util.Log;

import com.example.olden.cryptoexchange.business.currencies_list.ICurrenciesInteractor;
import com.example.olden.cryptoexchange.presentation.currencies_list.view.ICurrenciesView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CurrenciesPresenter implements ICurrenciesPresenter {

    private static final String TAG = "CurrenciesPresenter";

    private ICurrenciesView iCurrenciesView;

    private ICurrenciesInteractor iCurrenciesInteractor;
    private CurrenciesPresenterCache currenciesPresenterCache;

    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    public CurrenciesPresenter(ICurrenciesInteractor iCurrenciesInteractor,
                               CurrenciesPresenterCache currenciesPresenterCache) {
        this.iCurrenciesInteractor = iCurrenciesInteractor;
        this.currenciesPresenterCache = currenciesPresenterCache;
    }

    @Override
    public void bindView(ICurrenciesView view) {
        this.iCurrenciesView = view;
    }

    @Override
    public void unBindView() {
        compositeSubscription.clear();
        this.iCurrenciesView = null;
    }

    @Override
    public void fillAutoCompleteList() {
        if (currenciesPresenterCache.isCurrenciesListCached()) {
            setCurrenciesListToView(currenciesPresenterCache.getCurrenciesList());
        } else {
            loadCurrenciesListFromData();
        }
    }

    private void loadCurrenciesListFromData() {
        //Todo show loading
        Disposable disposable = iCurrenciesInteractor.getCurrencyNamesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessLoadCurrenciesList,
                        this::handleErrorLoadCurrenciesList);
        compositeSubscription.add(disposable);
    }


    private void handleSuccessLoadCurrenciesList(List<String> strings) {

        currenciesPresenterCache.setCurrenciesList(strings);
        setCurrenciesListToView(strings);
    }

    private void setCurrenciesListToView(List<String> strings) {
        //Todo hide loading
        iCurrenciesView.setAutoCompleteTextView(strings);
    }

    private void handleErrorLoadCurrenciesList(Throwable throwable) {
        //Todo show error
        Log.e(TAG, "handleErrorLoadCurrenciesList: ", throwable);
    }
}
