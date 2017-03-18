package com.example.rafae.rrss20;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileTabActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    static DatabaseReference mConditionRefmotor = mRootRef.child("motor");
    static DatabaseReference mConditionRefdoors = mRootRef.child("doors");
    static DatabaseReference mConditionRefuser = mRootRef.child("nombre");
    static DatabaseReference mConditionRefmodelo = mRootRef.child("modelo");
    static DatabaseReference mConditionRefplaca = mRootRef.child("placa");
    static DatabaseReference mConditionRefestado = mRootRef.child("estado");


    private static TextView tvmotor;
    private static TextView tvdoors;
    private static TextView tvuser;
    private static TextView tvmodelo;
    private static TextView tvplaca;
    private static TextView tvestado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        setContentView(R.layout.activity_profile_tab);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class Fragment1 extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public Fragment1() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment1 newInstance(int sectionNumber) {
            Fragment1 fragment1 = new Fragment1();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment1.setArguments(args);
            return fragment1;
        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_1, container, false);
            ImageButton button2 = (ImageButton) rootView.findViewById(R.id.imageButton2);
            ImageButton buttonBt = (ImageButton) rootView.findViewById(R.id.imageButton3);

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getActivity(), MapsActivity.class));
                }
            });

            buttonBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getActivity(), BluetoothMode.class));
                }
            });

            final Button buttondesbloqueo = (Button) rootView.findViewById(R.id.buttonDesbloqueo);
            final Button buttonOff = (Button) rootView.findViewById(R.id.buttonOff);
            final Button buttonClose = (Button) rootView.findViewById(R.id.buttonClose);
            buttondesbloqueo.setEnabled(false);
            buttonClose.setEnabled(false);
            buttonOff.setEnabled(false);
            tvmotor = (TextView) rootView.findViewById(R.id.textViewMotor);
            tvdoors = (TextView) rootView.findViewById(R.id.textViewPuertas);
            tvuser = (TextView) rootView.findViewById(R.id.textViewNombre);
            tvmodelo = (TextView) rootView.findViewById(R.id.textViewModelo);
            tvplaca = (TextView) rootView.findViewById(R.id.textViewPlaca);
            tvestado = (TextView) rootView.findViewById(R.id.textViewEstado);

            mConditionRefestado.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String festado = dataSnapshot.getValue(String.class);
                    tvestado.setText(festado);

                    if(festado.toString().equals("normal")){
                        buttondesbloqueo.setEnabled(false);
                        buttonClose.setEnabled(false);
                        buttonOff.setEnabled(false);
                    }
                    else{
                        buttondesbloqueo.setEnabled(true);
                        buttonClose.setEnabled(true);
                        buttonOff.setEnabled(true);
                        if(festado.toString().equals("alerta")){

                            Toast.makeText(getActivity(), " iniciando", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getActivity(), BluetoothMode.class));}


                        }
                    }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            mConditionRefuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fuser = dataSnapshot.getValue(String.class);
                    tvuser.setText(fuser);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mConditionRefmodelo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fmodelo = dataSnapshot.getValue(String.class);
                    tvmodelo.setText(fmodelo);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mConditionRefplaca.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fplaca = dataSnapshot.getValue(String.class);
                    tvplaca.setText(fplaca);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mConditionRefmotor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fmotor = dataSnapshot.getValue(String.class);
                    tvmotor.setText(fmotor);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mConditionRefdoors.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String fdoors = dataSnapshot.getValue(String.class);
                    tvdoors.setText(fdoors);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            buttondesbloqueo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Toast.makeText(getActivity(), " vehiculo desbloqueado", Toast.LENGTH_SHORT).show();
                        mConditionRefestado.setValue("normal");
                    }});

            buttonOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(), " motor apagado", Toast.LENGTH_SHORT).show();
                    mConditionRefmotor.setValue("off");
                }});


            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(), " puertas cerradas", Toast.LENGTH_SHORT).show();
                    mConditionRefdoors.setValue("close");
                }});


            return rootView;
        }
    }



    public static class Fragment2 extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public Fragment2() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment2 newInstance(int sectionNumber) {
            Fragment2 fragment = new Fragment2();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_2, container, false);
            return rootView;
        }
    }

    @Override
    public void onClick(View v) {


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return Fragment1.newInstance(position + 1);

                case 1:
                    return Fragment2.newInstance(position + 1);

            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PROFILE";
                case 1:
                    return "NOTIFICATIONS";

            }
            return null;
        }
    }
}
