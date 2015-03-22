package cs3773.com.eventplanner;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "section_number";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mCalendar;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public CalendarFragment newInstance(int sectionNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, sectionNumber);
        this.setArguments(args);
        return this;
    }

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar2, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MenuActivity) activity).onSectionAttached(1);
    }


}
