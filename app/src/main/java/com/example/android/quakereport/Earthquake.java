/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.quakereport;

/**
 * {@link Earthquake} is used to hold all the relevant information related to a single earthquake.
 */
public class Earthquake {

    /** Stores the magnitude of the earthquake */
    private double mMagnitude;

    /** Stores the location where earthquake has occurred */
    private String mLocation;

    /** Stores the time of the earthquake */
    private long mTimeInMilliseconds;

    /**
     * Create an object of {@link Earthquake}.
     *
     * @param magnitude          Magnitude of the earthquake
     * @param location           Location where earthquake occurred
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    /**
     * Returns the magnitude of the earthquake
     */
    public double getMagnitude() {
        return mMagnitude;
    }

    /**
     * Returns the location of the earthquake
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Returns the time of the earthquake
     */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    /**
     * Returns a string containing a concise, human-readable description of this object.
     */
    @Override
    public String toString() {
        return "Earthquake:\nmagnitude = " + mMagnitude + ",\nlocation = " + mLocation +
                ",\ntimeInMilliseconds = " + mTimeInMilliseconds + ".";
    }
}
