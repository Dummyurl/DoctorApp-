package com.bfurns.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfurns.R;
import com.bfurns.activity.ClinicListInDrawer;
import com.bfurns.activity.ConsultActivity;
import com.bfurns.activity.DoctorProfile;
import com.bfurns.activity.PatientDetails;
import com.bfurns.activity.RevenueActivity;
import com.bfurns.activity.ScheduleManagement;
import com.bfurns.activity.ViewAccounts;
import com.bfurns.adapter.NavigationDrawerAdapter;
import com.bfurns.application.AppClass;
import com.bfurns.model.NavDrawerItem;
import com.bfurns.utility.MyPreferences;
import com.bfurns.utility.Preferences;
import com.bfurns.utility.RecyclerItemClickListener;
import com.bfurns.utility.SimpleDividerItemDecoration;
import com.bfurns.utility.URLListner;
import com.bfurns.ziffylink.LoginActivity;
import com.bfurns.ziffylink.MergeAccount;
import com.bfurns.ziffylink.SplashScreenActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles=new String[3];
    private static int[] icon=new int[3];
    private FragmentDrawerListener drawerListener;
    SharedPreferences sharedpreferences;
    String email,doctor_id,user_id,user_name,image;
    TextView name,signin;
    CircleImageView imageView;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        for (int i = 0; i < icon.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setImageUrl(icon[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public void onStart() {
        super.onStart();

        email=  AppClass.getemail();
        image=  AppClass.getImage();
        user_name= AppClass.getUsername();

        signin.setText(email);
        name.setText(user_name);

        if(!image.equalsIgnoreCase("null")){

            Picasso.with(getContext())
                    .load(URLListner.BASEURLL+image)
                    .into(imageView);
        }else{

            Picasso.with(getContext()).load(R.drawable.placeholder_user).resize(40,40).into(imageView);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        sharedpreferences=getActivity().getSharedPreferences(Preferences.MyPREFERENCES,Context.MODE_PRIVATE);

        name=(TextView)layout.findViewById(R.id.name);
        signin=(TextView)layout.findViewById(R.id.signin);
        imageView=(CircleImageView) layout.findViewById(R.id.user_image);

        user_id=   AppClass.getuserId();


        recyclerView.setAdapter(new NavigationDrawerAdapter(getActivity(),getCountryList()));
        recyclerView.setHasFixedSize(true);
      //  recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (position){
                    case 0:
                        Intent inttent=new Intent(getContext(),MergeAccount.class);

                        startActivity(inttent);

                        break;


                    case 1:
                        Intent intent6=new Intent(getContext(),ClinicListInDrawer.class);
                        startActivity(intent6);
                        break;


                    case 2:
                        Intent intent=new Intent(getContext(),DoctorProfile.class);


                        startActivity(intent);

                        break;


                    case  3:
                        Intent intent1=new Intent(getContext(),ScheduleManagement.class);

                        startActivity(intent1);

                        break;




                    case 4:
                        Intent intent2=new Intent(getContext(),PatientDetails.class);

                        startActivity(intent2);

                        break;

                    case  5:
                        Intent intent3=new Intent(getContext(),RevenueActivity.class);

                        startActivity(intent3);
                        break;

                    case  6:
                        Intent intent4=new Intent(getContext(),ConsultActivity.class);

                        startActivity(intent4);
                        break;

                    case 7:


                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Are your sure?").setTitle("Sign Out");

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.clear();
                                editor.commit();

                                if (!sharedpreferences.contains(Preferences.userId)) {

                                    Intent i = new Intent(getContext(), SplashScreenActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);

                                }
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                        break;


                }
            }
        }));





        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    private static List<NavDrawerItem> getCountryList() {



        List<NavDrawerItem> countries = new ArrayList<>();
       // countries.add(new NavDrawerItem(false,"Home", 0));
        countries.add(new NavDrawerItem(false,"Merge Account", 0));
        countries.add(new NavDrawerItem(false,"Clinic List", 0));
        countries.add(new NavDrawerItem(false,"My Profile", R.drawable.ic_menu_profile));
        countries.add(new NavDrawerItem(false,"Schedule Management", 0));
        countries.add(new NavDrawerItem(false,"Patient Management ", 0));
        countries.add(new NavDrawerItem(false,"Reports", 0));
        countries.add(new NavDrawerItem(false,"Consult", 0));
        countries.add(new NavDrawerItem(false,"Log Out", 0));







        return countries;
    }

}
