package minube.com.textlengthbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import minube.com.library.ProgressTextLengthBar;
import minube.com.library.TextLengthBarState;

public class ProgressTextLengthBarActivity extends AppCompatActivity {

    @BindView(R.id.edit_text) EditText editText;
    @BindView(R.id.progress_text_length_bar) ProgressTextLengthBar progressTextLengthBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_text_length_bar);
        ButterKnife.bind(this);

        progressTextLengthBar.setStates(buildStates());
        progressTextLengthBar.attachToEditText(editText);
    }

    private List<TextLengthBarState> buildStates() {

        List<TextLengthBarState> states = new ArrayList<>();

        states.add(new TextLengthBarState.Builder(100, "Add %d more characters for a great review.")
            .backgroundColor(android.R.color.secondary_text_dark)
            .progressColor(R.color.first_state_color)
            .textColor(R.color.first_state_color)
            .icon(R.drawable.ic_progress_first_state)
            .build());

        states.add(new TextLengthBarState.Builder(150, "Help others by adding %d more characters.")
            .backgroundColor(android.R.color.secondary_text_dark)
            .progressColor(R.color.second_state_color)
            .textColor(R.color.second_state_color)
            .icon(R.drawable.ic_progress_second_state)
            .build());

        states.add(new TextLengthBarState.Builder(200, "You're doing great.")
            .backgroundColor(android.R.color.secondary_text_dark)
            .progressColor(R.color.third_state_color)
            .textColor(R.color.third_state_color)
            .icon(R.drawable.ic_progress_third_state)
            .build());

        return states;
    }
}
