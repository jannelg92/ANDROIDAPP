package tamuedu.geoinnovation.gisday;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FragTwo extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
 
      public static final String IMAGE_RESOURCE_ID = "@drawable/ic_action_eventcal";
      public static final String ITEM_NAME = "@string/title_sectionE";
 
      public FragTwo() {
 
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
 
            View view = inflater.inflate(R.layout.fragment_drawerlayout_two, container,
                        false);
 
            ivIcon = (ImageView) view.findViewById(R.id.frag2_icon);
            tvItemName = (TextView) view.findViewById(R.id.frag2_text);
 
            tvItemName.setText(getArguments().getString(ITEM_NAME));
            ivIcon.setImageDrawable(view.getResources().getDrawable(
                        getArguments().getInt(IMAGE_RESOURCE_ID)));
            return view;
      }
 
}