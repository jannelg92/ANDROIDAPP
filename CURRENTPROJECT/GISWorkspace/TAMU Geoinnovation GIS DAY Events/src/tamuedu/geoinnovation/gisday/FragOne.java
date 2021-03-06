package tamuedu.geoinnovation.gisday;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FragOne extends Fragment {
 
      ImageView ivIcon;
      TextView tvItemName;
 
      public static final String IMAGE_RESOURCE_ID = "@drawable/ic_action_signup";
      public static final String ITEM_NAME = "@string/title_section2";
 
      public FragOne() {
 
      }
 
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
 
            View view = inflater.inflate(R.layout.fragment_drawerlayout_one, container,
                        false);
 
            ivIcon = (ImageView) view.findViewById(R.id.frag1_icon);
            tvItemName = (TextView) view.findViewById(R.id.frag1_text);
 
            tvItemName.setText(getArguments().getString(ITEM_NAME));
            ivIcon.setImageDrawable(view.getResources().getDrawable(
                        getArguments().getInt(IMAGE_RESOURCE_ID)));
            return view;
      }
 
}