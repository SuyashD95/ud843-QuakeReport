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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * An {@link EarthquakeAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Earthquake} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    /**
     * The part of the location string from the USGS service that we use to determine
     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
     */
    private static final String LOCATION_SEPARATOR = "of";

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     *
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context       The current context. Used to inflate the layout file.
     * @param earthquakes   A list of Earthquake objects to display in a list.
     */
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     *
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        // Check for a scrap view. If there are no scrap views to be recycled
        // i.e., listItemView == null, then create a new View.
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in the earthquake_list_item.xml layout with the ID magnitude_text_view
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        // Get the magnitude from the current Earthquake object and
        // set this text on the magnitudeTextView
        magnitudeTextView.setText(formattedMagnitude);

        // Find the TextView with view ID location_offset_text_view
        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset_text_view);
        // Parse the String containing the place and extract the substring which stores information
        // about the proximity of the earthquake's epicenter from the primary location
        String locationOffset = formatLocationOffset(currentEarthquake.getLocation());
        // Display the location offset of the current earthquake in that TextView
        locationOffsetView.setText(locationOffset);

        // Find the TextView with view ID primary_location_text_view
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location_text_view);
        // Parse the String containing the place and extract the substring with the primary
        // location
        String primaryLocation = formatPrimaryLocation(currentEarthquake.getLocation());
        // Display the primary location of the current earthquake in that TextView
        primaryLocationView.setText(primaryLocation);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find the TextView with view ID date_text_view
        TextView dateView = (TextView) listItemView.findViewById(R.id.date_text_view);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time_text_view
        TextView timeView = (TextView) listItemView.findViewById(R.id.time_text_view);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat =  new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the proximity of the earthquake's epicenter (i.e. "10km N of")
     * from the primary location.
     */
    private String formatLocationOffset(String place) {
        // Check if the place mentions the proximity to the primary location
        if(place.contains(LOCATION_SEPARATOR)) {
            // Then split the offset from the primary location and return the substring from
            // beginning to endIndex (character at endIndex not included)
            int endIndex = place.indexOf(LOCATION_SEPARATOR) + 2;
            return place.substring(0, endIndex);
        }
        else {
            // Otherwise return 'Near the'
            return getContext().getString(R.string.near_the);
        }
    }

    /**
     * Return the primary location (i.e. "New York") which will get affected from the earthquake
     */
    private String formatPrimaryLocation(String place) {
        // Check if the place mentions the proximity to the primary location
        if(place.contains(LOCATION_SEPARATOR)) {
            // Then split the primary location from the passed {@link String} and return the
            // resultant substring from startIndex
            int startIndex = place.indexOf(LOCATION_SEPARATOR) + 3;
            return place.substring(startIndex);
        }
        else {
            // Otherwise return the passed {@link String} as it is, without any formatting
            return place;
        }
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}
