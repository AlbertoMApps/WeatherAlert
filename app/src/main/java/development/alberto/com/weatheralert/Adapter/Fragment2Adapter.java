package development.alberto.com.weatheralert.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import development.alberto.com.weatheralert.Model.WeatherInfo;
import development.alberto.com.weatheralert.R;
import development.alberto.com.weatheralert.DataModel.SavedWeatherData;

/**
 * Created by alber on 11/03/2016.
 */
public class Fragment2Adapter extends RecyclerView.Adapter<Fragment2Adapter.ViewHolder> {
    private Context context;
    private int layout;
    List<SavedWeatherData> savedData;


    public Fragment2Adapter( List<SavedWeatherData> weatherData, int fragment2_row_layout, Context context) {
        this.context = context;
        this.savedData = weatherData;
        layout = fragment2_row_layout;
    }

    @Override
    public Fragment2Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment2_row_layout, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "we are in position: " + i, Toast.LENGTH_SHORT).show();
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Fragment2Adapter.ViewHolder holder, int position) {
        SavedWeatherData dataSaved = savedData.get(position);
        holder.txtCityName.setText(dataSaved.getCityName().toString());
        holder.tvSpeed.setText(dataSaved.getWindSpeed().toString());
        holder.tvDegree.setText(dataSaved.getWindDegree().toString());
    }

    @Override
    public int getItemCount() {
        return savedData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvSavedDataCityName)
        TextView txtCityName;
        @Bind(R.id.tvSavedSpeed)
        TextView tvSpeed;
        @Bind(R.id.tvSavedDegree)
        TextView tvDegree;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
