package minube.com.textlengthbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

        states.add(new TextLengthBarState.Builder(100, "Add %d more characters for a great review.").backgroundColor(
            R.color.first_state_color).icon(R.drawable.ic_first_step).build());

        states.add(new TextLengthBarState.Builder(150, "Help others by adding %d more characters.").backgroundColor(
            R.color.second_state_color).icon(R.drawable.ic_second_state).build());

        states.add(new TextLengthBarState.Builder(200, "You're doing great.").backgroundColor(R.color.third_state_color)
            .icon(R.drawable.ic_third_state)
            .build());

        return states;
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this,ProgressTextLengthBarActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
