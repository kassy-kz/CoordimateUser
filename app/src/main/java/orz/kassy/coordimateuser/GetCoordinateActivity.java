package orz.kassy.coordimateuser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class GetCoordinateActivity extends AppCompatActivity {

    private static final String TAG = "GetCoordinate";
    ImageView imgCoordinateGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coordinate);
        imgCoordinateGet = (ImageView) findViewById(R.id.imgCoordinateGet);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("CoordinateObject");
        query.orderByDescending("createdAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    Log.i(TAG, "foo=" + object.get("foo"));
                    ParseFile file = (ParseFile) object.get("image");
                    file.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Log.d("test", "We've got data in data.");
                                // use data for something
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                imgCoordinateGet.setImageBitmap(bmp);
                            } else {
                                Log.d("test", "There was a problem downloading the data.");
                            }
                        }
                    });
                } else {
                    // something went wrong
                    Log.i(TAG, "error");
                }
            }
        });
    }
}
