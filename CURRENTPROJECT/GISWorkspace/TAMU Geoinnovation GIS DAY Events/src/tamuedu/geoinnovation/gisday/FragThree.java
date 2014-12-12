package tamuedu.geoinnovation.gisday;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FragThree extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
 
      public static final String IMAGE_RESOURCE_ID = "@drawable/ic_action_share";
      public static final String ITEM_NAME = "@string/title_section7";
 
      public FragThree() {
 
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
 
            View view = inflater.inflate(R.layout.fragment_drawerlayout_three, container,
                        false);
 
            ivIcon = (ImageView) view.findViewById(R.id.frag3_icon);
            tvItemName = (TextView) view.findViewById(R.id.frag3_text);
 
            tvItemName.setText(getArguments().getString(ITEM_NAME));
            ivIcon.setImageDrawable(view.getResources().getDrawable(
                        getArguments().getInt(IMAGE_RESOURCE_ID)));
            return view;
      }
 
}