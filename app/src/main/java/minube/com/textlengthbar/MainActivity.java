package minube.com.textlengthbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import minube.com.library.TextLengthBar;
import minube.com.library.TextLengthBarState;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_text) EditText editText;
    @BindView(R.id.text_length_bar) TextLengthBar textLengthBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        textLengthBar.setStates(buildStates());
        textLengthBar.attachToEditText(editText);
    }

    private List<TextLengthBarState> buildStates() {

        List<TextLengthBarState> states = new ArrayList<>();

        states.add(new TextLengthBarState.Builder(25, "Poor experience, try to write more content")
            .backgroundColor(android.R.color.holo_red_light)
            .icon(android.R.drawable.ic_input_add)
            .build());

        states.add(new TextLengthBarState.Builder(50, "Medium experience, you can do it better")
            .backgroundColor(android.R.color.holo_blue_bright)
            .icon(android.R.drawable.ic_dialog_email)
            .build());

        states.add(new TextLengthBarState.Builder(75,"Cool experience, add more text to help others travelers")
            .backgroundColor(android.R.color.holo_green_light)
            .icon(android.R.drawable.ic_lock_idle_lock)
            .build());

        return states;
    }
}
