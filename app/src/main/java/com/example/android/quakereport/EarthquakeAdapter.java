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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Earthquake} objects.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

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
        // Get the magnitude from the current Earthquake object and
        // set this text on the magnitudeTextView
        magnitudeTextView.setText(String.valueOf(currentEarthquake.getMagnitude()));

        // TODO: 2. Format the location into offset and primaryLocation and integrate it into UI
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
     * Return the proximity of the earthquake's epicenter from the primary location.
     */
    private String formatLocationOffset(String place) {
        // Check if the place mentions the proximity to the primary location
        if(place.contains("of")) {
            // Then split the offset from the primary location and return the substring from
            // beginning to endIndex (character at endIndex not included)
            int endIndex = place.indexOf("of") + 2;
            return place.substring(0, endIndex);
        }
        else {
            // Otherwise return 'Near the'
            return "Near the";
        }
    }

    /**
     * Return the primary location which will get affected from the earthquake
     */
    private String formatPrimaryLocation(String place) {
        // Check if the place mentions the proximity to the primary location
        if(place.contains("of")) {
            // Then split the primary location from the passed {@link String} and return the
            // resultant substring from startIndex
            int startIndex = place.indexOf("of") + 3;
            return place.substring(startIndex);
        }
        else {
            // Otherwise return the passed {@link String} as it is, without any formatting
            return place;
        }
    }
}
