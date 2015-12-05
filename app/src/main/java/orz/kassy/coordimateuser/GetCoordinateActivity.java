package orz.kassy.coordimateuser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class GetCoordinateActivity extends AppCompatActivity {

    private static final String TAG = "GetCoordinate";
    ImageView mImgCoordinateGet;
    private SectionsPagerAdapter mSectionsPagerAdapter1;
    private ViewPager mViewPager1;
    private static Bitmap bmp1, bmp2, bmp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coordinate);
        mImgCoordinateGet = (ImageView) findViewById(R.id.imgCoordinateGet);

        mSectionsPagerAdapter1 = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager1 = (ViewPager) findViewById(R.id.container1);
        mViewPager1.setAdapter(mSectionsPagerAdapter1);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ClothesFragment.newInstance(position);

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    public static class ClothesFragment extends Fragment {
        static int mPosition;
        ImageView mImgClothes;

        public static ClothesFragment newInstance(int position) {
            ClothesFragment fragment = new ClothesFragment();
            Bundle args = new Bundle();
            args.putInt("a", position);
            fragment.setArguments(args);
            return fragment;
        }

        public ClothesFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_clothes, null);
            mImgClothes = (ImageView) view.findViewById(R.id.imgClothes);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("CoordinateObject");
            query.orderByDescending("createdAt");
//            query.getFirstInBackground(new GetCallback<ParseObject>() {
//                public void done(ParseObject object, ParseException e) {
//                    if (e == null) {
//                        // object will be your game score
//                        Log.i(TAG, "foo=" + object.get("foo"));
//                        ParseFile file = (ParseFile) object.get("image");
//                        file.getDataInBackground(new GetDataCallback() {
//                            public void done(byte[] data, ParseException e) {
//                                if (e == null) {
//                                    Log.d("test", "We've got data in data.");
//                                    // use data for something
//                                    bmp1 = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//                                } else {
//                                    Log.d("test", "There was a problem downloading the data.");
//                                }
//                            }
//                        });
//                    } else {
//                        // something went wrong
//                        Log.i(TAG, "error");
//                    }
//                }
//            });
            try {
                List<ParseObject> list = query.find();
                ParseFile file = (ParseFile) list.get(getArguments().getInt("a")).get("image");
                file.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            Log.d("test", "We've got data in data.");
                            // use data for something
                            bmp1 = BitmapFactory.decodeByteArray(data, 0, data.length);
                            mImgClothes.setImageBitmap(bmp1);
                        } else {
                            Log.d("test", "There was a problem downloading the data.");
                        }
                    }
                });

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.i(TAG,"getA" + getArguments().getInt("a"));
//            switch (getArguments().getInt("a")) {
//                case 0:
//                    mImgClothes.setImageBitmap(bmp1);
//                    break;
//                case 1:
//                    mImgClothes.setImageBitmap(bmp1);
//                    break;
//                case 2:
//                    mImgClothes.setImageBitmap(bmp1);
//                    break;
//            }
//

            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
        }
    }
}
