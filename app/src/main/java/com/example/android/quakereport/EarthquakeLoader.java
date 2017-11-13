package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class EarthquakeLoader extends AsyncTaskLoader{

    public EarthquakeLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        // TODO: Implement this method
    }
}
