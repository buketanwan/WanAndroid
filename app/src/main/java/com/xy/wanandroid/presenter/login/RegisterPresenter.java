package com.xy.wanandroid.presenter.login;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.RegisterContract;
import com.xy.wanandroid.data.login.UserInfo;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/22.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RegisterContract.View view;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void register(String name, String password, String rePassword) {
        ApiStore.createApi(ApiService.class)
                .register(name, password, rePassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<UserInfo>>() {
                    @Override
                    public void onNext(BaseResp<UserInfo> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            view.registerOk(baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            view.registerErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.registerErr(e.getMessage());
                    }
                });
    }
}