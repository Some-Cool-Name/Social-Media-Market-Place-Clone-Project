package com.example.social_media_market_place_clone_project;

import java.util.ArrayList;

public class Quicksort {

    private void swap(User[] arr, int i, int j)
    {
        User temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /* This function takes last element as pivot, places
       the pivot element at its correct position in sorted
       array, and places all smaller (smaller than pivot)
       to left of pivot and all greater elements to right
       of pivot */
    private int partition(User[] arr, int low, int high)
    {

        // pivot
        User pivot = arr[high];

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for(int j = low; j <= high - 1; j++)
        {

            // If current element is smaller
            // than the pivot
            if (arr[j].getDistanceFromUser() < pivot.getDistanceFromUser())
            {

                // Increment index of
                // smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /* The main function that implements QuickSort
              arr[] --> Array to be sorted,
              low --> Starting index,
              high --> Ending index
     */
    private void quickSort(User[] arr, int low, int high)
    {
        if (low < high)
        {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arr, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    public ArrayList<User> convert (ArrayList<User> users){
        User[] usersBuilt = new User[users.size()];

        for(int i=0;i< users.size();i++){
            usersBuilt[i] = users.get(i);

        }
        quickSort(usersBuilt,0,usersBuilt.length-1);

        for (int i=0;i< usersBuilt.length; i ++){
            users.set(i,usersBuilt[i]);
        }
        return  users;

    }



}
