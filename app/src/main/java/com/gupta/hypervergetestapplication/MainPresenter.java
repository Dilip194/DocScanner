package com.gupta.hypervergetestapplication;

import android.content.Context;
import android.content.Intent;

public interface MainPresenter {

    void scanImage(MainActivity context);
    void getImageResponse(Context context, Intent data);
}
